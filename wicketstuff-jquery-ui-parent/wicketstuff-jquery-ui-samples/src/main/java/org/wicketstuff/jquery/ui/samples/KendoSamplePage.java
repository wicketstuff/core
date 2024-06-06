/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples;

import org.wicketstuff.kendo.ui.widget.tabs.TabbedPanel;

public abstract class KendoSamplePage extends SamplePage
{
	private static final long serialVersionUID = 1L;

	public KendoSamplePage()
	{
		this.add(new TabbedPanel("sources", this.newSourceTabList()));
	}
}
