package com.googlecode.wicket.jquery.ui.samples.pages.kendo.autocomplete;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.pages.kendo.AbstractKendoPage;


abstract class AbstractAutoCompletePage extends AbstractKendoPage
{
	private static final long serialVersionUID = 1L;

	public AbstractAutoCompletePage()
	{
	}

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(KendoAutoCompletePage.class, "AutoCompleteTextField")
			);
	}
}
