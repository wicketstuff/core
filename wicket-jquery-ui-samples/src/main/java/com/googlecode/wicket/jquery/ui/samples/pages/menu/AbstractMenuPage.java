package com.googlecode.wicket.jquery.ui.samples.pages.menu;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;



abstract class AbstractMenuPage extends SamplePage
{
	private static final long serialVersionUID = 1L;

	public AbstractMenuPage()
	{
	}

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(DefaultMenuPage.class, "Menu")
			);
	}
}
