package com.googlecode.wicket.jquery.ui.samples.pages.tabs;

import com.googlecode.wicket.jquery.ui.widget.tabs.TabsBehavior;

public class DefaultTabsPage extends AbstractTabsPage
{
	private static final long serialVersionUID = 1L;
	
	public DefaultTabsPage()
	{
		this.add(new TabsBehavior("#tabs"));
	}
}
