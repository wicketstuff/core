package org.wicketstuff.pageserializer.kryo2;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.pageserializer.kryo2.components.SamplePanel;

public class SamplePage extends WebPage
{
	private final PageParameters pageParameters;

	public SamplePage(PageParameters pageParameters)
	{
		this.pageParameters = pageParameters;
		add(new SamplePanel("sample"));
	}
}
