package com.googlecode.wicket.jquery.ui.samples.pages.plugins;

import com.googlecode.wicket.jquery.ui.plugins.emoticons.EmoticonsBehavior;
import com.googlecode.wicket.jquery.ui.samples.SamplePage;

public class EmoticonsPage extends SamplePage
{
	private static final long serialVersionUID = 1L;

	public EmoticonsPage()
	{
		this.add(new EmoticonsBehavior("#comments"));
	}
}
