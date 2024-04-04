package com.googlecode.wicket.jquery.ui.samples.jqueryui.accordion;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.JQuerySamplePage;

abstract class AbstractAccordionPage extends JQuerySamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(DefaultAccordionPage.class, "Accordion behavior"), // lf
				new DemoLink(AccordionPanelPage.class, "AccordionPanel (using ITabs)") // lf
		);
	}
}
