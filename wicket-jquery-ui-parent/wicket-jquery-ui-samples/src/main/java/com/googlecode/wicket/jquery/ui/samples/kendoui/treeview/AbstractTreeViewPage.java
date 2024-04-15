package com.googlecode.wicket.jquery.ui.samples.kendoui.treeview;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.KendoSamplePage;

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
