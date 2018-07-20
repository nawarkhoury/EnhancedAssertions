package com.nawar.khoury.testing.assertions.enhanced.template;

/**
 * @author nawar.khoury
 *
 */
public abstract class Template
{
	protected String expectation;
	protected String foundValue;
	
	public abstract String assertAndRemove(String value, int startIndex) throws Exception;
	
	public void setExpectation(String expectation)
	{
		this.expectation = expectation;
	}
	public void setFoundValue(String foundValue)
	{
		this.foundValue = foundValue;
	}
	
	
}
