package org.wicketstuff.urlfragment.example.asyncpanel;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage
{

	public HomePage(final PageParameters parameters)
	{
		super(parameters);

		add(new AsyncContentPanel("content"));
	}
}
