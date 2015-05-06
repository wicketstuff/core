package org.wicketstuff.foundation.button;

import org.junit.Test;
import org.wicketstuff.foundation.button.ButtonColor;
import org.wicketstuff.foundation.button.ButtonExpansion;
import org.wicketstuff.foundation.button.ButtonOptions;
import org.wicketstuff.foundation.button.ButtonRadius;
import org.wicketstuff.foundation.button.ButtonSize;
import org.wicketstuff.foundation.button.ButtonState;

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
