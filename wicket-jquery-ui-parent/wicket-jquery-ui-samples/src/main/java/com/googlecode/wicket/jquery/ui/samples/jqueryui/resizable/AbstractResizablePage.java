package com.googlecode.wicket.jquery.ui.samples.jqueryui.resizable;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.JQuerySamplePage;

abstract class AbstractResizablePage extends JQuerySamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(DefaultResizablePage.class, "Resizable Behavior"), // lf
				new DemoLink(ResizablePanelPage.class, "Resizable Panel") // lf
		);
	}
}
