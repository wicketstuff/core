package org.wicketstuff.datastores.redis.demo;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class Page2 extends WebPage {
	private static final long serialVersionUID = 1L;

	public Page2(final PageParameters parameters) {
		super(parameters);

		add(new AjaxLink<Void>("link") {
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				// create a new page
				setResponsePage(HomePage.class);
			}
		});
    }
}
