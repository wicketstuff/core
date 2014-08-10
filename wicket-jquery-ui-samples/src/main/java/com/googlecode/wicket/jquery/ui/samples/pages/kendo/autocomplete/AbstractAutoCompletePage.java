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
				new DemoLink(KendoAutoCompletePage.class, "Auto-complete"),
				new DemoLink(KendoCustomAutoCompletePage.class, "Auto-complete: custom beans"),
				new DemoLink(KendoTemplateAutoCompletePage.class, "Auto-complete: custom template"),
				new DemoLink(KendoRendererAutoCompletePage.class, "Auto-complete: custom renderer"),
				new DemoLink(KendoConverterAutoCompletePage.class, "Auto-complete: form submit")
			);
	}
}
