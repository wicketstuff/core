package org.wicketstuff.htmlcompressor;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

/**
 * Used for testing. Simple page with one label component.
 * 
 * @author akiraly
 */
public class TestPage extends WebPage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void onInitialize()
	{
		super.onInitialize();
		add(new Label("label", "Hello World!"));
	}
}
