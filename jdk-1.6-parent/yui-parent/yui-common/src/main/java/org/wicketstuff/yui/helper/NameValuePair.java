package org.wicketstuff.yui.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the abstract class that helps to generate the notations for name
 * pairs
 * 
 * 1. Inline Styles. eg. padding:1px ; color:#FF0000; 2. Javscript Objects. eg.
 * {name:"josh" , age : "44"}
 * 
 * as both are the same thing except it is used at different points (1) in
 * "style=" and (2) in javascript this abstract class handles the basic
 * concatenation of these name/value pairs and generate the "dhtml"
 * 
 * @author josh
 * 
 */
public abstract class NameValuePair<T> extends TokenSeparatedValues
{
	private static final long serialVersionUID = 1L;

	/**
	 * the map used internally
	 */
	Map<String, Object> propertyMap = new HashMap<String, Object>();

	/**
	 * construct
	 */
	public NameValuePair()
	{
	}

	/**
	 * 
	 * @param element
	 */
	@SuppressWarnings("unchecked")
	public T add(String element, Object object)
	{

		if (isValid(element, object))
		{

			if (object instanceof Boolean)
			{
				propertyMap.put(element, object);
			}
			else
			{
				String value = object.toString();
				if ((!value.startsWith("{")) && (!value.startsWith("["))
						&& (!value.startsWith("\"")))
				{
					try
					{
						Float.parseFloat(value);
					}
					catch (Exception e)
					{
						value = wrapValue(value);
					}
				}
				propertyMap.put(element, value);
			}
		}
		return (T)this;
	}

	@SuppressWarnings("unchecked")
	public T add(NameValuePair another)
	{
		Map<String, Object> am = another.propertyMap;

		for (Map.Entry<String, Object> entry : am.entrySet())
		{
			add(entry.getKey(), entry.getValue());
		}
		return (T)this;
	}

	/**
	 * 
	 * @param element
	 * @param int_value
	 * @return
	 */
	public T add(String element, int int_value)
	{
		return this.add(element, Integer.toString(int_value));
	}


	/*
	 * the List to be used by TSV
	 */
	@Override
	public List<String> getValues()
	{
		List<String> list = new ArrayList<String>();

		for (Map.Entry<String, Object> entry : propertyMap.entrySet())
		{
			list.add(entry.getKey() + getNameValueSeparator() + entry.getValue());
		}
		return list;
	}

	public boolean isValid(String key, Object value)
	{
		return true;
	}

	/**
	 * 
	 * @return
	 */
	public abstract String getNameValueSeparator();

	protected String wrapValue(String value)
	{
		return "\"" + value + "\"";
	}


}
