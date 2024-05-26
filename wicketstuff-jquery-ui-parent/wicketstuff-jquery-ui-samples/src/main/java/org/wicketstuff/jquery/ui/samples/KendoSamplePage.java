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
