package org.wicketstuff.jquery.ui.samples;

import org.wicketstuff.jquery.ui.widget.tabs.TabbedPanel;

public abstract class JQuerySamplePage extends SamplePage
{
	private static final long serialVersionUID = 1L;

	public JQuerySamplePage()
	{
		this.add(new TabbedPanel("sources", this.newSourceTabList()));
	}
}
