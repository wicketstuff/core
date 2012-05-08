package com.googlecode.wicket.jquery.ui.samples.pages.plugins;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;



abstract class AbstractSnippetPage extends SamplePage
{
	private static final long serialVersionUID = 1L;
	
	public AbstractSnippetPage()
	{
		
	}
	
	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(DefaultSnippetPage.class, "Snippet: the behavior"),
				new DemoLink(WidgetSnippetPage.class, "Snippet: the widget"),
				new DemoLink(OptionSnippetPage.class, "Snippet: the widget, with options")
			);
	}
}
