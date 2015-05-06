package org.wicketstuff.foundation;

import org.apache.wicket.markup.html.basic.Label;
import org.wicketstuff.foundation.component.FoundationBasePanel;
import org.wicketstuff.foundation.icon.FoundationIcon;
import org.wicketstuff.foundation.icon.IconType;

public class FoundationIconAndTextPanel extends FoundationBasePanel {

	private static final long serialVersionUID = 1L;

	public FoundationIconAndTextPanel(String id, IconType icon, String text) {
		super(id);
		add(new FoundationIcon("icon", icon));
		add(new Label("text", text));
	}
}
