package com.googlecode.wicket.jquery.ui.samples.pages.kendo.radio;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;



abstract class AbstractRadioPage extends SamplePage
{
	private static final long serialVersionUID = 1L;

	public AbstractRadioPage()
	{
	}

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(DefaultRadioPage.class, "Radio"),
				new DemoLink(KendoCheckPage.class, "Check"),
				new DemoLink(KendoCheckBoxPage.class, "CheckBox")
			);
	}
}
