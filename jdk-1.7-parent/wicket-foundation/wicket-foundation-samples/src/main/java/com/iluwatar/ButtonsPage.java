package com.iluwatar;

import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.iluwatar.foundation.button.FoundationButton;
import com.iluwatar.foundation.button.ButtonColor;
import com.iluwatar.foundation.button.ButtonExpansion;
import com.iluwatar.foundation.button.ButtonOptions;
import com.iluwatar.foundation.button.ButtonRadius;
import com.iluwatar.foundation.button.ButtonSize;
import com.iluwatar.foundation.button.ButtonState;

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
