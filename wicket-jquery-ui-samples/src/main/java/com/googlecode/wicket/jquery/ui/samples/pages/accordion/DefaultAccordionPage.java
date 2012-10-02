package com.googlecode.wicket.jquery.ui.samples.pages.accordion;

import com.googlecode.wicket.jquery.ui.widget.accordion.AccordionBehavior;

public class DefaultAccordionPage extends AbstractAccordionPage
{
	private static final long serialVersionUID = 1L;

	public DefaultAccordionPage()
	{
		this.add(new AccordionBehavior("#accordion"));
	}
}
