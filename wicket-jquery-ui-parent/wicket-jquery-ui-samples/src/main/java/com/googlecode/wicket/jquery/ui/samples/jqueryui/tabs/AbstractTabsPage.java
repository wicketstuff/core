package com.googlecode.wicket.jquery.ui.samples.jqueryui.tabs;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.JQuerySamplePage;

abstract class AbstractTabsPage extends JQuerySamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(DefaultTabsPage.class, "Tabs Behavior"), // lf
				new DemoLink(TabbedPanelPage.class, "TabbedPanel"), // lf
				new DemoLink(AdvancedTabsPage.class, "TabbedPanel: demo") // lf
			);
	}
}
