package org.wicketstuff.foundation.buttongroup;

import java.util.ArrayList;

import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.foundation.button.ButtonOptions;

public class ButtonBarTestPage extends WebPage {

	private static final long serialVersionUID = 1L;
	
	public ButtonBarTestPage() {
		FoundationButtonBarBorder border = new FoundationButtonBarBorder("border");
		add(border);
		border.add(createBasicButtonGroup("first"));
		border.add(createBasicButtonGroup("second"));
	}

	private TestButtonGroupPanel createBasicButtonGroup(String id) {
		ArrayList<ButtonOptions> btnOptions = new ArrayList<>();
		btnOptions.add(new ButtonOptions());
		ButtonGroupOptions groupOptions = new ButtonGroupOptions();
		TestButtonGroupPanel group = new TestButtonGroupPanel(id, groupOptions, btnOptions);
		return group;
	}
}
