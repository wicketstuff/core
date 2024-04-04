package org.wicketstuff.select2;

public class JQuery
{
	private JQuery()
	{

	}


	public static String execute(String script, Object... params)
	{
		return "(function($) { " + String.format(script, params) + " })(jQuery);";
	}
}
