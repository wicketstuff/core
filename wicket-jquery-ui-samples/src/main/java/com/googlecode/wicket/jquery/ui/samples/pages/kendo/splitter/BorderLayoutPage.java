package com.googlecode.wicket.jquery.ui.samples.pages.kendo.splitter;

import com.googlecode.wicket.jquery.ui.kendo.splitter.BorderLayout;

public class BorderLayoutPage extends AbstractSplitterPage
{
	private static final long serialVersionUID = 1L;
	
	public BorderLayoutPage()
	{
		this.add(new BorderLayout("layout"));
	}
}
