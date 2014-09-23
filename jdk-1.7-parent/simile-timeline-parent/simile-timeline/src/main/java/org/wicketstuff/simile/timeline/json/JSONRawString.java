package org.wicketstuff.simile.timeline.json;


import net.sf.json.JSONFunction;

/**
 * should not be u
 * 
 * @author arnouten
 */
public class JSONRawString extends JSONFunction
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JSONRawString(String text)
	{
		super(text);
	}

	@Override
	public String toString()
	{
		return getText();
	}
}
