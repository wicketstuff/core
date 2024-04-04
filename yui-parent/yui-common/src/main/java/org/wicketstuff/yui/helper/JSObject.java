package org.wicketstuff.yui.helper;

/**
 * a Javascript Object "{name: value, name1 : value1}"
 * TODO : Use JSON ??
 * 
 * @author josh
 *
 */
public class JSObject<T> extends NameValuePair<T> implements JavascriptObject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * construct.
	 */
	public JSObject()
	{
		super();
	}
	
	@Override
	public String getPrefix()
	{
		return "{";
	}

	@Override
	public String getSuffix()
	{
		return "}";
	}

	@Override
	public String getValueSeparator()
	{
		return ",";
	}
	
	@Override
	public String getNameValueSeparator()
	{
		return ":";
	}
	
	public JSObject add(String name, JavascriptObject object)
	{
		add(name, object.toString());
		return this;
	}

	/**
	 * 
	 */
	public JavascriptObject add(String string)
	{
		return null;
	}
}
