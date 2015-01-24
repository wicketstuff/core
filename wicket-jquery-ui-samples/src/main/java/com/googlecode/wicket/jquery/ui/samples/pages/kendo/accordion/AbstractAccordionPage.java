package com.googlecode.wicket.jquery.ui.samples.pages.kendo.accordion;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;



abstract class AbstractAccordionPage extends SamplePage
{
	private static final long serialVersionUID = 1L;

	public AbstractAccordionPage()
	{
	}

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(KendoAccordionPage.class, "Accordion behavior"),
				new DemoLink(KendoAccordionPanelPage.class, "AccordionPanel (using ITabs)")
			);
	}
}
