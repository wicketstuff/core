package org.wicketstuff.openlayers.js;


/**
 * @author Michael O'Cleirigh
 * 
 *         Java Script Generating Utility methods that don't fit into a specific case like @see
 *         {@link ObjectLiteral}.
 * 
 */
public final class JSUtils
{

	/**
	 * 
	 */
	private JSUtils()
	{
	}

	/**
	 * 
	 * @param unquotedString
	 * @return the quoted string. e.g. 'str'
	 */
	public static String getQuotedString(String unquotedString)
	{
		return "'" + unquotedString + "'";
	}

}
