package com.googlecode.wicket.jquery.ui.samples.pages.accordion;

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
				new DemoLink(DefaultAccordionPage.class, "Accordion behavior"),
				new DemoLink(AccordionPanelPage.class, "AccordionPanel (using ITabs)")
			);
	}
}
