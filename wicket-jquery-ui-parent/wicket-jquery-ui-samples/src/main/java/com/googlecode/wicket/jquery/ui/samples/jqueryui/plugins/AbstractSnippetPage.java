package com.googlecode.wicket.jquery.ui.samples.jqueryui.plugins;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.JQuerySamplePage;

abstract class AbstractSnippetPage extends JQuerySamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(DefaultSnippetPage.class, "Snippet: the behavior"), // lf
				new DemoLink(WidgetSnippetPage.class, "Snippet: the widget"), // lf
				new DemoLink(OptionSnippetPage.class, "Snippet: the widget, with options"));
	}
}
