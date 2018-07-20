package com.nawar.khoury.testing.assertions.enhanced.template;

public class MonthTemplate extends Template
{
	public final static String MONTH_OF_YEAR = "January,February,March,April,May,June,July,August,September,October,November,December";
	
	@Override
	public String assertAndRemove(String value, int startIndex) throws Exception
	{
		PossibleValuesTemplate template = new PossibleValuesTemplate();
		template.setExpectation(MONTH_OF_YEAR);
		return template.assertAndRemove(value, startIndex);
	}

}
