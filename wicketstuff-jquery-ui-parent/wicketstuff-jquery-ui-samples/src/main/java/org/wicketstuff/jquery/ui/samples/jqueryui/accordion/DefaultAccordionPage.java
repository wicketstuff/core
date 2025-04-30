/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
*/
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
