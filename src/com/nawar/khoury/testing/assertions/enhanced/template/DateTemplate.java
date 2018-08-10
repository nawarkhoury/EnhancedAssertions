package com.nawar.khoury.testing.assertions.enhanced.template;

import java.text.SimpleDateFormat;

/**
 * @author nawar.khoury
 *
 */
public class DateTemplate extends Template
{

	public int expectedLength()
	{
		int length = expectation.length();
		if(expectation.contains("'"))
			length = length - 2;
		
		if(expectation.contains("Z"))
			length = length + 4;
		
		return length;
	}

	@Override
	public String assertAndRemove(String value, int startIndex) throws Exception
	{
		int expectedLength = expectedLength();
		//TODO bug, handle having timezones in the strings, this handling is not enough
		if(expectation.endsWith("z") || expectation.endsWith("Z"))
			expectation = expectation.substring(0,expectation.length() - 1);
		SimpleDateFormat sdf = new SimpleDateFormat(expectation);
		sdf.setLenient(false);
		sdf.parse(value.substring(startIndex, startIndex + expectedLength));
		return value.substring(0, startIndex) + value.substring(startIndex + expectedLength);
	}
	
	
}
