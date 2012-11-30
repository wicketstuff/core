package org.wicketstuff.yui.helper;

import java.util.ArrayList;
import java.util.List;


/**
 * a Javascript Array "[value1, value2, value3]" TODO : Use JSON ?
 * 
 * @author josh
 * 
 */
public class JSArray extends TokenSeparatedValues implements JavascriptObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * the list of values
	 */
	private List<String> values = new ArrayList<String>();

	/**
	 * construct.
	 */
	public JSArray()
	{
		super();
	}

	/**
	 * constructor for a list of array
	 * 
	 * @param array
	 */
	public JSArray(List<String> array)
	{
		values.addAll(array);
	}

	/**
	 * @param int_values
	 */
	public JSArray(int... int_values)
	{
		for (int a_int : int_values)
		{
			values.add(Integer.toString(a_int));
		}
	}

	public JSArray(String[] javaArray)
	{
		for (String s : javaArray)
		{
			values.add("\"" + s + "\"");
		}
	}

	public JSArray add(String value)
	{
		values.add(value);
		return this;
	}

	public JSArray add(JavascriptObject object)
	{
		values.add(object.toString());
		return this;
	}

	@Override
	public String getPrefix()
	{
		return "[";
	}

	@Override
	public String getSuffix()
	{
		return "]";
	}

	@Override
	public String getValueSeparator()
	{
		return ",";
	}

	@Override
	public List<String> getValues()
	{
		return values;
	}

	public void setValues(List<String> values)
	{
		this.values = values;
	}
}
