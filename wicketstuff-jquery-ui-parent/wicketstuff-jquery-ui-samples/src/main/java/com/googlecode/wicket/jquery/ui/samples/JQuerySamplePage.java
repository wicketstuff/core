package com.googlecode.wicket.jquery.ui.samples;

import com.googlecode.wicket.jquery.ui.widget.tabs.TabbedPanel;

public abstract class JQuerySamplePage extends SamplePage
{
	private static final long serialVersionUID = 1L;

	public JQuerySamplePage()
	{
		this.add(new TabbedPanel("sources", this.newSourceTabList()));
	}
}
