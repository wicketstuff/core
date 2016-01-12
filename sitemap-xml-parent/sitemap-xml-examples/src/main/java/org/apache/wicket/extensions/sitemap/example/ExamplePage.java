package org.apache.wicket.extensions.sitemap.example;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class ExamplePage extends WebPage {

	protected ExamplePage(PageParameters parameters) {
		super(parameters);
		add(new WebMarkupContainer("number", new Model<String>(parameters.get("number").toString())));
	}
}