package com.googlecode.wicket.jquery.ui.samples.pages.autocomplete;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;



abstract class AbstractAutoCompletePage extends SamplePage
{
	private static final long serialVersionUID = 1L;
	
	public AbstractAutoCompletePage()
	{
		
	}
	
	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(DefaultAutoCompletePage.class, "Auto-complete"),
				new DemoLink(CustomAutoCompletePage.class, "Auto-complete: custom beans"),
				new DemoLink(TemplateAutoCompletePage.class, "Auto-complete: custom template"),
				new DemoLink(RendererAutoCompletePage.class, "Auto-complete: custom renderer")
			);
	}
}
