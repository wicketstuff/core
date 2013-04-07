package com.googlecode.wicket.jquery.ui.samples.pages.plugins;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.plugins.SnippetBehavior;

public class DefaultSnippetPage extends AbstractSnippetPage
{
	private static final long serialVersionUID = 1L;

	public DefaultSnippetPage()
	{
		Options options = new Options();
		options.set("style", Options.asString("ide-eclipse")); //Options.asString() converts a string or an object to its javascript representation. ie: "myvalue" (including the double quote)

		this.add(new SnippetBehavior("#code", "java", options));
	}
}
