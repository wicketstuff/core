package com.googlecode.wicket.jquery.ui.samples.pages.tabs;

import com.googlecode.wicket.jquery.ui.Options;
import com.googlecode.wicket.jquery.ui.widget.Tabs;

public class WidgetTabsPage extends AbstractTabsPage
{
	private static final long serialVersionUID = 1L;
	
	public WidgetTabsPage()
	{
		Options options = new Options();
		options.set("collapsible", true);
		
		this.add(new Tabs("tabs", options));
	}
}
