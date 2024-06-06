/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package com.googlecode.wicket.jquery.ui.samples.jqueryui.plugins;

import com.googlecode.wicket.jquery.ui.plugins.emoticons.EmoticonsBehavior;
import com.googlecode.wicket.jquery.ui.samples.JQuerySamplePage;

public class EmoticonsPage extends JQuerySamplePage
{
	private static final long serialVersionUID = 1L;

	public EmoticonsPage()
	{
		this.add(new EmoticonsBehavior("#comments"));
	}
}
