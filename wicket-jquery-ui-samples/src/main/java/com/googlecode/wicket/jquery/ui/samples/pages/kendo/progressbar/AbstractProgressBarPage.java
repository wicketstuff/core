package com.googlecode.wicket.jquery.ui.samples.pages.kendo.progressbar;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.pages.kendo.AbstractKendoPage;



abstract class AbstractProgressBarPage extends AbstractKendoPage
{
	private static final long serialVersionUID = 1L;

	public AbstractProgressBarPage()
	{

	}

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(KendoProgressBarPage.class, "Progress-bar Widget"),
				new DemoLink(ButtonProgressBarPage.class, "Progress-bar: controlled by buttons"),
				new DemoLink(SliderProgressBarPage.class, "Progress-bar: controlled by a slider")
			);
	}
}
