package org.wicketstuff.yui.helper;


import java.io.Serializable;
import java.util.List;

/**
 * Basic class for generating DHTML values for Javascript and CSS
 * 
 * 1. Javscript Arrays : ['value1', 'value2','value3'] 2. Javscript Objects :
 * {name:"josh" , age : "44"} 3. Inline Styles : padding:1px ; color:#FF0000;
 * 
 * @author josh
 * 
 */
public abstract class TokenSeparatedValues implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * construct
	 */
	public TokenSeparatedValues()
	{
		super();
	}

	/**
	 * the actual String to be returned for the Javscript
	 */
	@Override
	public String toString()
	{
		final StringBuffer buffer = new StringBuffer();

		for (Object aValue : getValues())
		{
			buffer.append(getValueSeparator()).append(aValue);
		}

		String ret = (buffer.length() > 0) ? buffer.substring(1) : "";
		return getPrefix() + ret + getSuffix();
	}

	/**
	 * allow child to quote
	 * 
	 * @return
	 */
	public abstract List<String> getValues();

	/**
	 * the suffix
	 * 
	 * @return
	 */
	public abstract String getSuffix();

	/**
	 * the prefix
	 * 
	 * @return
	 */
	public abstract String getPrefix();

	/**
	 * the separator value
	 * 
	 * @return
	 */
	public abstract String getValueSeparator();
}