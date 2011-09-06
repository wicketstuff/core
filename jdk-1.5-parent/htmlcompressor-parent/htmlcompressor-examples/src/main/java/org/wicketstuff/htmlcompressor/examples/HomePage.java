package org.wicketstuff.htmlcompressor.examples;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class HomePage extends WebPage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void onInitialize()
	{
		super.onInitialize();
		add(new Label("label", "Hello World!"));
	}
}
