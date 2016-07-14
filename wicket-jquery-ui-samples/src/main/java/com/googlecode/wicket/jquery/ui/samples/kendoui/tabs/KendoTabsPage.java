package com.googlecode.wicket.jquery.ui.samples.kendoui.tabs;

import com.googlecode.wicket.kendo.ui.KendoUIBehavior;

public class KendoTabsPage extends AbstractTabsPage
{
	private static final long serialVersionUID = 1L;

	public KendoTabsPage()
	{
		this.add(new KendoUIBehavior("#tabs", "kendoTabStrip"));
	}
}
