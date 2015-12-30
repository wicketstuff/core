package com.googlecode.wicket.jquery.ui.samples.pages.spinner;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;

abstract class AbstractSpinnerPage extends SamplePage
{
	private static final long serialVersionUID = 1L;

	public AbstractSpinnerPage()
	{

	}

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(new DemoLink(DefaultSpinnerPage.class, "Spinner"), // lf
//				new DemoLink(CultureSpinnerPage.class, "Spinner, with culture"),
				new DemoLink(TimeSpinnerPage.class, "Time Spinner"),
				new DemoLink(AjaxSpinnerPage.class, "Ajax Spinner"));
	}
}
