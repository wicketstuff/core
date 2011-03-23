package org.wicketstuff.console.examples;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.console.ClojureScriptEnginePanel;

public class ClojureEngineTestPage extends WebPage {
	private static final long serialVersionUID = 1L;

	public ClojureEngineTestPage(PageParameters params) {
		super(params);
		
		add(new Label("version", getApplication().getFrameworkSettings()
				.getVersion()));
		// TODO Add your page's components here
		add(new ClojureScriptEnginePanel("scriptPanel"));

		add(new TestPageLinksPanel("links"));
	}
}
