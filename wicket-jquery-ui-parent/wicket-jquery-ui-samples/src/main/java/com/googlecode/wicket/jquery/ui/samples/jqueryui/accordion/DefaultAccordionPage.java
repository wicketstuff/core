package com.googlecode.wicket.jquery.ui.samples.jqueryui.accordion;

import com.googlecode.wicket.jquery.ui.JQueryUIBehavior;

public class DefaultAccordionPage extends AbstractAccordionPage
{
	private static final long serialVersionUID = 1L;

	public DefaultAccordionPage()
	{
		this.add(new JQueryUIBehavior("#accordion", "accordion"));
	}
}
