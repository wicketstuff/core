package com.googlecode.wicket.jquery.ui.samples;

import com.googlecode.wicket.kendo.ui.widget.tabs.TabbedPanel;

public abstract class KendoSamplePage extends SamplePage
{
	private static final long serialVersionUID = 1L;

	public KendoSamplePage()
	{
		this.add(new TabbedPanel("sources", this.newSourceTabList()));
	}
}
