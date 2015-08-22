package com.inmethod.grid;

/**
 * Represents a HTML size unit.
 * 
 * @author Matej Knopp
 */
public enum SizeUnit
{
	/**
	 * CSS EM unit
	 */
	EM("em"),

	/**
	 * CSS PX unit
	 */
	PX("px"),

    /**
     * CSS percent unit
     */
    PERCENT("%"),

	/**
	 * CSS EX unit
	 */
	EX("ex");

	private final String value;

	SizeUnit(String value)
	{
		this.value = value;
	}

	/**
	 * Returns the string representation of the unit.
	 * 
	 * @return string representation of the unit
	 */
	public String getValue()
	{
		return value;
	}
};
