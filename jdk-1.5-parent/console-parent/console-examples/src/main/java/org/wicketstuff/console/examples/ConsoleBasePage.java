package org.wicketstuff.console.examples;

import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public abstract class ConsoleBasePage extends WebPage
{

	private static final long serialVersionUID = 1L;

	public ConsoleBasePage()
	{
		super();
		init();
	}

	public ConsoleBasePage(final PageParameters parameters)
	{
		super(parameters);
		init();
	}

	public ConsoleBasePage(final IModel<?> model)
	{
		super(model);
		init();
	}

	private void init()
	{
		add(new TestPageLinksPanel("links"));
		add(new FeedbackPanel("feedback"));
	}

	@Override
	public void renderHead(final IHeaderResponse response)
	{
		super.renderHead(response);
	}

}
