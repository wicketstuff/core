package com.googlecode.wicket.jquery.ui.samples.jqueryui.tabs;

import com.googlecode.wicket.jquery.ui.JQueryUIBehavior;

public class DefaultTabsPage extends AbstractTabsPage
{
	private static final long serialVersionUID = 1L;

	public DefaultTabsPage()
	{
		this.add(new JQueryUIBehavior("#tabs", "tabs"));
	}
}
