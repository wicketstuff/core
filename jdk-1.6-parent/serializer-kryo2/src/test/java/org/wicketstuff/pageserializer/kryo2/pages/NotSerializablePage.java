package org.wicketstuff.pageserializer.kryo2.pages;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.pageserializer.kryo2.components.NotSerializablePanel;

public class NotSerializablePage extends SamplePage
{
	public NotSerializablePage(PageParameters pageParameters)
	{
		super(pageParameters);
		
		add(new NotSerializablePanel("not"));
	}

}
