package com.googlecode.wicket.jquery.ui.samples.kendoui.autocomplete;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.KendoSamplePage;

abstract class AbstractAutoCompletePage extends KendoSamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(KendoAutoCompletePage.class, "Auto-complete"), // lf
				new DemoLink(KendoCustomAutoCompletePage.class, "Auto-complete: custom beans"), // lf
				new DemoLink(KendoTemplateAutoCompletePage.class, "Auto-complete: custom template"), // lf
				new DemoLink(KendoRendererAutoCompletePage.class, "Auto-complete: custom renderer"), // lf
				new DemoLink(KendoConverterAutoCompletePage.class, "Auto-complete: form submit") // lf
		);
	}
}
