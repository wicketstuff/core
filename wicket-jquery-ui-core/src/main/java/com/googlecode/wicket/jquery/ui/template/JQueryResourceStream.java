package com.googlecode.wicket.jquery.ui.template;

import org.apache.wicket.util.resource.AbstractStringResourceStream;

public class JQueryResourceStream extends AbstractStringResourceStream
{
	private static final long serialVersionUID = 1L;
	
	private final String token;
	private final String content;

	public JQueryResourceStream(final String content, final String token)
	{
		this.token = token;
		this.content = content;
	}

	@Override
	public String getString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("<script id=\"").append(this.token).append("\" type=\"").append(this.getContentType()).append("\">");
		builder.append(this.content);
		builder.append("</script>");

		return builder.toString();
	}

	@Override
	public String getContentType()
	{
		return "text/x-jquery-tmpl";
	}
}
