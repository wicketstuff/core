package com.googlecode.wicket.jquery.ui.samples.kendoui.accordion;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.KendoSamplePage;

abstract class AbstractAccordionPage extends KendoSamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(KendoAccordionPage.class, "Accordion behavior"), // lf
				new DemoLink(KendoAccordionPanelPage.class, "AccordionPanel (using ITabs)") // lf
		);
	}
}
