package org.wicketstuff.browserid.example;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.browserid.BrowserIdPanel;
import org.wicketstuff.browserid.GuestPanel.Style;

/**
 * Demo page that just adds the default BrowserIdPanel and a feedback panel to show any errors
 */
public class BrowserIdPage extends WebPage
{

	private static final long serialVersionUID = 1L;

	public BrowserIdPage(final PageParameters parameters)
	{
		super(parameters);

		add(new BrowserIdPanel("browserId", Style.GREEN));

		add(new FeedbackPanel("feedback").setOutputMarkupId(true));
	}
}