/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.kendoui.treeview;

import java.util.Arrays;
import java.util.List;

import org.wicketstuff.jquery.ui.samples.KendoSamplePage;

abstract class AbstractTreeViewPage extends KendoSamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(DefaultTreeViewPage.class, "Behavior (static html)"), // lf
				new DemoLink(AjaxTreeViewPage.class, "AjaxTreeView (custom beans)") // lf
			);
	}
}
