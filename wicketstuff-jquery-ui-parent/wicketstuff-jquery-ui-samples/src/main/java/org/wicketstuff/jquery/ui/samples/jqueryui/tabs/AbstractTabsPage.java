/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.jqueryui.tabs;

import java.util.Arrays;
import java.util.List;

import org.wicketstuff.jquery.ui.samples.JQuerySamplePage;

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
