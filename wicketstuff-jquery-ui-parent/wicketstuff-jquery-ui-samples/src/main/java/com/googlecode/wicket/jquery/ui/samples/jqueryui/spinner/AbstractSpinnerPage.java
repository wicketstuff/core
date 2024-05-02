package com.googlecode.wicket.jquery.ui.samples.jqueryui.spinner;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.JQuerySamplePage;

abstract class AbstractSpinnerPage extends JQuerySamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(new DemoLink(DefaultSpinnerPage.class, "Spinner"), // lf
				// new DemoLink(CultureSpinnerPage.class, "Spinner, with culture"),
				new DemoLink(TimeSpinnerPage.class, "Time Spinner"), // lf
				new DemoLink(AjaxSpinnerPage.class, "Ajax Spinner"));
	}
}
