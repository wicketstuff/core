package com.iluwatar;

import org.apache.wicket.markup.html.basic.Label;

import com.iluwatar.foundation.component.FoundationBasePanel;
import com.iluwatar.foundation.icon.FoundationIcon;
import com.iluwatar.foundation.icon.IconType;

public class FoundationIconAndTextPanel extends FoundationBasePanel {

	private static final long serialVersionUID = 1L;

	public FoundationIconAndTextPanel(String id, IconType icon, String text) {
		super(id);
		add(new FoundationIcon("icon", icon));
		add(new Label("text", text));
	}
}
