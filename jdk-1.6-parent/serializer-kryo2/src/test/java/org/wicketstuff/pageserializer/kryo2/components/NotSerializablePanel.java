package org.wicketstuff.pageserializer.kryo2.components;

import org.apache.wicket.markup.html.panel.Panel;

public class NotSerializablePanel extends Panel
{
	private final IsNotSerializableObject aField = new IsNotSerializableObject();

	public NotSerializablePanel(String id)
	{
		super(id);
	}

	static class IsNotSerializableObject
	{
		String name;
	}
}
