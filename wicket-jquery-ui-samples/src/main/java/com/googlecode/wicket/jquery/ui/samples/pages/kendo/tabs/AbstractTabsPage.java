package com.googlecode.wicket.jquery.ui.samples.pages.kendo.tabs;

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
				new DemoLink(KendoTabsPage.class, "Behavior (kendoTabStrip)"),
				new DemoLink(TabbedPanelPage.class, "TabbedPanel"),
				new DemoLink(AdvancedTabsPage.class, "TabbedPanel: demo")
			);
	}
}
