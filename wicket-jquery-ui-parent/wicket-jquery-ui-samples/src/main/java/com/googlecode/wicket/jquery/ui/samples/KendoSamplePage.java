/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
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
