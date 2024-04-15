package com.googlecode.wicket.jquery.ui.samples.jqueryui.autocomplete;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.JQuerySamplePage;

abstract class AbstractAutoCompletePage extends JQuerySamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(DefaultAutoCompletePage.class, "Auto-complete"), // lf
				new DemoLink(CustomAutoCompletePage.class, "Auto-complete: custom beans"), // lf
				new DemoLink(TemplateAutoCompletePage.class, "Auto-complete: custom template"), // lf
				new DemoLink(RendererAutoCompletePage.class, "Auto-complete: custom renderer"), // lf
				new DemoLink(ConverterAutoCompletePage.class, "Auto-complete: form submit") // lf
		);
	}
}
