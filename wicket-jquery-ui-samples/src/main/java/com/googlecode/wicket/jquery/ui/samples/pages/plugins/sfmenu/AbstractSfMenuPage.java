package com.googlecode.wicket.jquery.ui.samples.pages.plugins.sfmenu;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;



abstract class AbstractSfMenuPage extends SamplePage
{
	private static final long serialVersionUID = 1L;

	public AbstractSfMenuPage()
	{
	}

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(DefaultSfMenuPage.class, "Horizontal Menu"),
				new DemoLink(VerticalSfMenuPage.class, "Vertical Menu")
			);
	}
}
