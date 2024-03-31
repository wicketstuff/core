package org.wicketstuff.foundation.button;

import org.junit.jupiter.api.Test;

public class ButtonOptionsTest {

	@Test
	public void test() {
		ButtonOptions options = new ButtonOptions();
		ButtonOptions options2 = new ButtonOptions(options);
		options2.setFoundationButtonColor(ButtonColor.ALERT);
		options2.setFoundationButtonExpansion(ButtonExpansion.EXPAND);
		options2.setFoundationButtonRadius(ButtonRadius.ROUND);
		options2.setFoundationButtonSize(ButtonSize.TINY);
		options2.setFoundationButtonState(ButtonState.DISABLED);
		options2.reset();
	}

}
