package com.googlecode.wicket.jquery.ui.samples.pages.kendo.window;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.pages.kendo.AbstractKendoPage;

abstract class AbstractWindowPage extends AbstractKendoPage
{
	private static final long serialVersionUID = 1L;

	public AbstractWindowPage()
	{

	}

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(DefaultWindowPage.class, "Window"),
				new DemoLink(ActionWindowPage.class, "Window: actions"),
				new DemoLink(InputWindowPage.class, "Input Window")
			);
	}
}
