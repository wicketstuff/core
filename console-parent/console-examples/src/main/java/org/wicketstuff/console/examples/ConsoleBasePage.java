package org.wicketstuff.console.examples;

import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

public abstract class ConsoleBasePage extends WebPage
{

	private static final long serialVersionUID = 1L;
	private static final ResourceReference CSS = new CssResourceReference(ConsoleBasePage.class,
		ConsoleBasePage.class.getSimpleName() + ".css");

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
		add(new BookmarkablePageLink<Void>("homeLink", WebApplication.get().getHomePage()));
		add(new Label("title", Model.of(getClass().getSimpleName())));
		add(new TestPageLinksPanel("links"));
		add(new FeedbackPanel("feedback"));
	}

	@Override
	public void renderHead(final IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(CssReferenceHeaderItem.forReference(CSS));
	}

}
