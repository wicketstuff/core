package com.googlecode.wicket.jquery.ui.samples.pages.tabs;

import com.googlecode.wicket.jquery.core.JQueryBehavior;

public class DefaultTabsPage extends AbstractTabsPage
{
	private static final long serialVersionUID = 1L;

	public DefaultTabsPage()
	{
		this.add(new JQueryBehavior("#tabs", "tabs"));
	}
}
