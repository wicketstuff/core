package org.wicketstuff.foundation;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class TextualPanel extends Panel
{

	public TextualPanel(String id, IModel<String> text)
	{
		super(id);
		add(new Label("label", text));
	}

}
