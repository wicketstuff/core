package org.wicketstuff.foundation.blockgrid;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class TestParagraphPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public TestParagraphPanel(String id, IModel<String> model) {
		super(id, model);
		WebMarkupContainer parent = new WebMarkupContainer("parent");
		add(parent);
		parent.add(new Label("paragraph", model));
	}
}
