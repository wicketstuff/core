/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package com.googlecode.wicket.jquery.ui.samples.jqueryui.tabs;

import com.googlecode.wicket.jquery.ui.JQueryUIBehavior;

public class DefaultTabsPage extends AbstractTabsPage
{
	private static final long serialVersionUID = 1L;

	public DefaultTabsPage()
	{
		this.add(new JQueryUIBehavior("#tabs", "tabs"));
	}
}
