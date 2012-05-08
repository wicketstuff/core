package com.googlecode.wicket.jquery.ui.samples.pages.resizable;

import com.googlecode.wicket.jquery.ui.JQueryBehavior;
import com.googlecode.wicket.jquery.ui.Options;
import com.googlecode.wicket.jquery.ui.samples.SamplePage;

public class ResizablePage extends SamplePage
{
	private static final long serialVersionUID = 1L;
	
	public ResizablePage()
	{
		Options options = new Options();
		options.set("minWidth", 350);
		options.set("minHeight", 85);
		options.set("maxWidth", 700);
		options.set("maxHeight", 240);

		this.add(new JQueryBehavior("#resizable", "resizable", options));
	}
}
