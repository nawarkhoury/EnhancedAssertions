package com.nawar.khoury.testing.assertions.enhanced.template;

public class DayOfMonthTemplate extends Template
{
	public final static String DAY_OF_MONTH = "1st,2nd,3rd,4th,5th,6th,7th,8th,9th,10th,11th,12th,13th,14th,15th,16th,17th,18th,19th,20th,21st,22nd,23rd,24th,25th,26th,27th,28th,29th,30th,31s";
	
	@Override
	public String assertAndRemove(String value, int startIndex) throws Exception
	{
		PossibleValuesTemplate template = new PossibleValuesTemplate();
		template.setExpectation(DAY_OF_MONTH);
		return template.assertAndRemove(value, startIndex);
	}

}
