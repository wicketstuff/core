package org.wicketstuff.push.examples;

import java.io.Serializable;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.push.examples.pages.IndexPage;

public class ExampleApplication extends WebApplication implements Serializable
{

	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return IndexPage.class;
	}
}