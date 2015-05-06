package org.wicketstuff.foundation;

import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.foundation.button.ButtonColor;
import org.wicketstuff.foundation.button.ButtonExpansion;
import org.wicketstuff.foundation.button.ButtonOptions;
import org.wicketstuff.foundation.button.ButtonRadius;
import org.wicketstuff.foundation.button.ButtonSize;
import org.wicketstuff.foundation.button.ButtonState;
import org.wicketstuff.foundation.button.FoundationButton;

public class ButtonsPage extends BasePage {

	private static final long serialVersionUID = 1L;

	public ButtonsPage(PageParameters params) {
		super(params);
		add(new FoundationButton("btn", Model.of("Click")));
		add(new FoundationButton("tiny", Model.of("Tiny"), new ButtonOptions(ButtonSize.TINY)));
		add(new FoundationButton("alert", Model.of("Alert"), new ButtonOptions(ButtonColor.ALERT)));
		add(new FoundationButton("radius", Model.of("Radius"), new ButtonOptions(ButtonRadius.RADIUS)));
		add(new FoundationButton("disabled", Model.of("Disabled"), new ButtonOptions(ButtonState.DISABLED)));
		add(new FoundationButton("expand", Model.of("Expand"), new ButtonOptions(ButtonExpansion.EXPAND)));
	}

}
