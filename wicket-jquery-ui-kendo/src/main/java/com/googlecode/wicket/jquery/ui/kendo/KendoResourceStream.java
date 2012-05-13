package com.googlecode.wicket.jquery.ui.kendo;

import com.googlecode.wicket.jquery.ui.template.JQueryResourceStream;

public class KendoResourceStream extends JQueryResourceStream
{
	private static final long serialVersionUID = 1L;

	public KendoResourceStream(final String content, final String token)
	{
		super(content, token);
	}

	public String getContentType()
	{
		return "text/x-kendo-template";
	}
}
