package org.wicketstuff.foundation;

import org.apache.wicket.Application;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

public abstract class BasePage extends WebPage {

	private static final long serialVersionUID = 1L;

	private ResourceReference styleCssReference;
	
	public BasePage(final PageParameters params) {
		super(params);
		styleCssReference = new CssResourceReference(BasePage.class, "styles.css");
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
		response.render(CssHeaderItem.forReference(Foundation.getNormalizeCssReference()));
		response.render(CssHeaderItem.forReference(Foundation.getFoundationCssReference()));
		response.render(JavaScriptHeaderItem.forReference(Foundation.getFoundationJsReference()));
		response.render(CssHeaderItem.forReference(styleCssReference));
	}
	
}
