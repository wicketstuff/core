package org.wicketstuff.jquery.ui.samples.jqueryui.accordion;

import org.wicketstuff.jquery.ui.JQueryUIBehavior;

public class DefaultAccordionPage extends AbstractAccordionPage
{
	private static final long serialVersionUID = 1L;

	public DefaultAccordionPage()
	{
		this.add(new JQueryUIBehavior("#accordion", "accordion"));
	}
}
