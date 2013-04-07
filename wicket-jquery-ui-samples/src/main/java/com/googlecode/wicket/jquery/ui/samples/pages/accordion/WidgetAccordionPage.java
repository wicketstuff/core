package com.googlecode.wicket.jquery.ui.samples.pages.accordion;

import com.googlecode.wicket.jquery.ui.widget.accordion.Accordion;

@SuppressWarnings("deprecation")
public class WidgetAccordionPage extends AbstractAccordionPage
{
	private static final long serialVersionUID = 1L;

	public WidgetAccordionPage()
	{
		this.add(new Accordion("accordion"));
	}
}
