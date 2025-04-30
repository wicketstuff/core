/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.kendoui.tabs;

import org.wicketstuff.kendo.ui.KendoUIBehavior;

public class KendoTabsPage extends AbstractTabsPage
{
	private static final long serialVersionUID = 1L;

	public KendoTabsPage()
	{
		this.add(new KendoUIBehavior("#tabs", "kendoTabStrip"));
	}
}
