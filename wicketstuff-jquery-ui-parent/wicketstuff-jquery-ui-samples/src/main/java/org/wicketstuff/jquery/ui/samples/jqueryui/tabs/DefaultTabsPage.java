package org.wicketstuff.jquery.ui.samples.jqueryui.tabs;

import org.wicketstuff.jquery.ui.JQueryUIBehavior;

public class DefaultTabsPage extends AbstractTabsPage
{
	private static final long serialVersionUID = 1L;

	public DefaultTabsPage()
	{
		this.add(new JQueryUIBehavior("#tabs", "tabs"));
	}
}
