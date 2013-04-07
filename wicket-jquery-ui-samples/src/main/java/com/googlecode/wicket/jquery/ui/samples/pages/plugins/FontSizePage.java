package com.googlecode.wicket.jquery.ui.samples.pages.plugins;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.plugins.FontSizeBehavior;
import com.googlecode.wicket.jquery.ui.samples.SamplePage;

public class FontSizePage extends SamplePage
{
	private static final long serialVersionUID = 1L;

	public FontSizePage()
	{
		Options options = new Options();
		options.set("sizeChange", 3);

		this.add(new FontSizeBehavior("#paragraph", options));
	}
}
