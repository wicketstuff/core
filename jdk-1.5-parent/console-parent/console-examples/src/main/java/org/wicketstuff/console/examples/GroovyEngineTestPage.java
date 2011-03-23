package org.wicketstuff.console.examples;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.console.GroovyScriptEnginePanel;

public class GroovyEngineTestPage extends WebPage {
	private static final long serialVersionUID = 1L;

	public GroovyEngineTestPage(PageParameters params) {
		super(params);

		add(new GroovyScriptEnginePanel("scriptPanel"));
		add(new TestPageLinksPanel("links"));
	}

}
