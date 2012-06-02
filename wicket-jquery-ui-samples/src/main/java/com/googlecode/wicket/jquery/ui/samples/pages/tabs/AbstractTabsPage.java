package com.googlecode.wicket.jquery.ui.samples.pages.tabs;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;



abstract class AbstractTabsPage extends SamplePage
{
	private static final long serialVersionUID = 1L;
	
	public AbstractTabsPage()
	{
		
	}
	
	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(DefaultTabsPage.class, "Tabs Behavior"),
				new DemoLink(WidgetTabsPage.class, "Tabs Widget: TabbedPanel")
			);
	}
}
