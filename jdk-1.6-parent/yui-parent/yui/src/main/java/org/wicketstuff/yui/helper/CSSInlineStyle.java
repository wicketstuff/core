package org.wicketstuff.yui.helper;

/**
 * This is a CSS Inline Style "Style=..." where it's toString method will return the
 * string suitable  to be used for the Attribute Modifier
 * 
 * @author josh
 *
 */
public class CSSInlineStyle extends NameValuePair<CSSInlineStyle>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * construct
	 */
	public CSSInlineStyle()
	{
		super();
	}

	@Override
	public String getPrefix()
	{
		return "";
	}

	@Override
	public String getSuffix()
	{
		return "";
	}

	@Override
	public String getValueSeparator()
	{
		return ";";
	}

	@Override
	public String getNameValueSeparator()
	{
		return ":";
	}

	/**
	 * adds the extra ";"
	 */
	@Override
	public String toString()
	{
		String ret = super.toString();
		
		if ((ret != null) && (ret.length() > 0))
		{
			ret = ret + getValueSeparator();
		}
		return ret;
	}
	
	protected String wrapValue(String value)
	{
		// no wrapping for CSS values
		// just return the parameter value
		return value;
	}

	/**
	 * @return
	 */
	public String getStyle()
	{
		return toString();
	}
}
