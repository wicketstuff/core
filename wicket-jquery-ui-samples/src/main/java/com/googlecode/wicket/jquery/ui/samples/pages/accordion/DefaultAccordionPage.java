package com.googlecode.wicket.jquery.ui.samples.pages.accordion;

import com.googlecode.wicket.jquery.core.JQueryBehavior;

public class DefaultAccordionPage extends AbstractAccordionPage
{
	private static final long serialVersionUID = 1L;

	public DefaultAccordionPage()
	{
		this.add(new JQueryBehavior("#accordion", "accordion"));
	}
}
