package org.wicketstuff.jquery.demo;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class TextPanel extends Panel
{
	private static final long serialVersionUID = 1L;

	public TextPanel(String id, IModel<?> model)
	{
		super(id, model);
		add(new Label("label", model));
	}

}
