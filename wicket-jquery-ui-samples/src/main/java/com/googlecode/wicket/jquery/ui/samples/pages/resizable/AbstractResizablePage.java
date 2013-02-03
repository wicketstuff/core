package com.googlecode.wicket.jquery.ui.samples.pages.resizable;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;



abstract class AbstractResizablePage extends SamplePage
{
	private static final long serialVersionUID = 1L;

	public AbstractResizablePage()
	{

	}

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(DefaultResizablePage.class, "Resizable Behavior"),
				new DemoLink(ResizablePanelPage.class, "Resizable Panel")
			);
	}
}
