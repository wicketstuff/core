package org.wicketstuff.yui.markup.html.menu2;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

class YuiMenuGroupMenu extends YuiMenu {

	private static final String LABEL_ID = "label";

	private static final long serialVersionUID = 1L;

	YuiMenuGroupMenu() {
		this(false, false);
	}

	YuiMenuGroupMenu(IModel label) {
		this(false, false);
	}

	YuiMenuGroupMenu(final boolean firstMenu, boolean addInit) {
		super(null, firstMenu, addInit);
		WebMarkupContainer label = new WebMarkupContainer(LABEL_ID);
		label.setRenderBodyOnly(true);
		label.setVisible(false);
		add(label);
	}

	YuiMenuGroupMenu(IModel label, final boolean firstMenu, boolean addInit) {
		super(null, false, addInit);

		Label lbl = new Label(LABEL_ID, label);
		if (firstMenu) {
			lbl.add(new AttributeAppender("class", true, new Model(
					"first-of-type"), " "));
		}
		
		add(lbl);

	}

	@Override
	protected WebMarkupContainer getMenuContainer() {
		return null;
	}

}
