package com.nawar.khoury.testing.assertions.enhanced.template;

import java.util.Arrays;

public class PossibleValuesTemplate extends Template
{
	@Override
	public String assertAndRemove(String value, int startIndex) throws Exception
	{
		String[] possibleValues = expectation.split(",");
		for(String possibleValue : possibleValues)
		{
			int endIndex = startIndex + possibleValue.length();
			if(value.length() < endIndex)
				continue;
			
			String foundValue = value.substring(startIndex, endIndex);
			
			if(foundValue.equals(possibleValue))
			{
				return value.substring(0, startIndex) + value.substring(startIndex + possibleValue.length());
			}
		}
		
		throw new Exception("could not find any of the possible values: " + Arrays.toString(possibleValues) + " at index: " + startIndex);
	}
}
