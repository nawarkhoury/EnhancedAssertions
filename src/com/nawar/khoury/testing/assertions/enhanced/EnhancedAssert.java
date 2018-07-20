package com.nawar.khoury.testing.assertions.enhanced;

import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.nawar.khoury.testing.assertions.enhanced.template.DateTemplate;
import com.nawar.khoury.testing.assertions.enhanced.template.DayOfMonthTemplate;
import com.nawar.khoury.testing.assertions.enhanced.template.MonthTemplate;
import com.nawar.khoury.testing.assertions.enhanced.template.PossibleValuesTemplate;
import com.nawar.khoury.testing.assertions.enhanced.template.Template;


/**
 * @author nawar.khoury
 *
 */
public class EnhancedAssert
{
	private final static HashMap<String, Class<? extends Template>> templateTypes = new HashMap<String, Class<? extends Template>>();
	
	static
	{
		templateTypes.put("date", DateTemplate.class);
		templateTypes.put("possibleValues", PossibleValuesTemplate.class);
		templateTypes.put("month", MonthTemplate.class);
		templateTypes.put("dayOfMonth", DayOfMonthTemplate.class);
	}
	
	public static void assertDirectoryListing(String[] dir1, String[] dir2) throws Exception
	{
		ArrayList<String> dir1Listing = new ArrayList<String>(Arrays.asList(dir1));
		ArrayList<String> dir2Listing = new ArrayList<String>(Arrays.asList(dir2));
		
		dir1Listing.forEach((fileName) -> {
			if(dir2Listing.contains(fileName))
			{
				dir1Listing.remove(fileName);
				dir2Listing.remove(fileName);
			}
			else
			{
				fail("File with name " + fileName + " was expected but not found to be under the resulting directory");
			}
		});
		
		if(dir2Listing.size() > 0)
			fail("The files " + dir2Listing + " were unexpectedly found under the resulting directory.");
	}
	
	public static void assertEquals(File expectedFile, File foundFile) throws Exception
	{
		String expectedString = readFileAsString(expectedFile.getAbsolutePath());
		String foundString = readFileAsString(foundFile.getAbsolutePath());
		
		assertEquals(expectedString, foundString);
	}
	
	private static String escapeRegEx(String string)
	{
		return string.replaceAll("([\\\\\\.\\[\\{\\)\\(\\*\\+\\?\\^\\$\\|])", "\\\\$1");
	}
	
	public static void main(String[] args) throws Exception
	{
		/**
		 * This is just for testing
		 */
//		String expected = "nawar@date(yyyyMMdd)something@possibleValues(123,234)nawaragainnawar@date(yyyyMMdd)";
//		String found = "nawar20180504something123nawaragainnawar20180704";
		String expected = "nawar@date(yyyyMMdd)something@possibleValues(123,234)nawaragainnawar@month()123@dayOfMonth()";
		String found = "nawar20180504something123nawaragainnawarJuly1233rd";
		assertEquals(expected, found);
		System.out.println("They are equals!");
	}
	
	public static void assertEquals(String expectedString, String foundString) throws Exception
	{
		//TODO fix bug here where the assertion would fail if the special values were alternating, like
		//something@date(yyyyMMdd)somethingelse@possibleValues(123,234)nawar@date(yyyyMMdd)
		for(String key : templateTypes.keySet())
		{
			while(expectedString.contains("@" + key + "("))
			{
				int startIndex = expectedString.indexOf("@" + key + "(");
				int endIndex = expectedString.indexOf(")",startIndex);
				String expectation = expectedString.substring(startIndex + key.length() + 2, endIndex);
				Template template = templateTypes.get(key).getConstructor().newInstance();
				template.setExpectation(expectation);
				foundString = template.assertAndRemove(foundString, startIndex);
				String specialValue = "@" + key + "(" + expectation + ")";
				expectedString = expectedString.replaceFirst(escapeRegEx(specialValue), "");
			}
		}
		
		//assert remaining string is correct
		org.junit.Assert.assertEquals(expectedString, foundString);
		
//		//TODO support @TimeWithinRange.
//		//TODO support @NotNull.
//		//TODO when done update the ScheduleSteps class and delete DateFormatter class.
		
	}
	public static String readFileAsString(String filePath) throws Exception
	{
		byte[] buffer = new byte[(int) new File(filePath).length()];
		FileInputStream fileInputStream = new FileInputStream(filePath);
		BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
		bufferedInputStream.read(buffer);
		fileInputStream.close();
		bufferedInputStream.close();

		return new String(buffer);
	}
	
}
