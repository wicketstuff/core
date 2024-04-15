package com.googlecode.wicket.jquery.ui.samples.kendoui.tabs;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.KendoSamplePage;

abstract class AbstractTabsPage extends KendoSamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(new DemoLink(KendoTabsPage.class, "Behavior (kendoTabStrip)"), new DemoLink(TabbedPanelPage.class, "TabbedPanel"), new DemoLink(AdvancedTabsPage.class, "TabbedPanel: demo"));
	}
}
