package com.googlecode.wicket.jquery.ui.samples.pages.accordion;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;
import com.googlecode.wicket.jquery.ui.widget.Accordion;

public class AccordionPage extends SamplePage
{
	private static final long serialVersionUID = 1L;
	
	public AccordionPage()
	{
		this.add(new Accordion("accordion"));
	}
}
