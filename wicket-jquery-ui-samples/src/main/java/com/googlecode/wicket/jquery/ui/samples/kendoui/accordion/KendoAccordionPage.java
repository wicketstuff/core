package com.googlecode.wicket.jquery.ui.samples.kendoui.accordion;

import com.googlecode.wicket.kendo.ui.KendoUIBehavior;

public class KendoAccordionPage extends AbstractAccordionPage
{
	private static final long serialVersionUID = 1L;

	public KendoAccordionPage()
	{
		this.add(new KendoUIBehavior("#accordion", "kendoPanelBar"));
	}
}
