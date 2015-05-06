package org.wicketstuff.foundation.dropdown;

import org.junit.Test;
import org.wicketstuff.foundation.button.ButtonColor;
import org.wicketstuff.foundation.button.ButtonExpansion;
import org.wicketstuff.foundation.button.ButtonRadius;
import org.wicketstuff.foundation.button.ButtonSize;

public class DropdownOptionsTest {

	@Test
	public void test() {
		DropdownOptions options = new DropdownOptions();
		DropdownOptions options2 = new DropdownOptions(options);
		options2 = options2.setColor(ButtonColor.ALERT).setRadius(ButtonRadius.ROUND)
				.setSize(ButtonSize.LARGE).setExpansion(ButtonExpansion.EXPAND);
		DropdownOptions options3 = new DropdownOptions(options2.getColor());
		DropdownOptions options4 = new DropdownOptions(options2.getRadius());
		DropdownOptions options5 = new DropdownOptions(options2.getSize());
		DropdownOptions options6 = new DropdownOptions(options2.getExpansion());
		DropdownOptions options7 = new DropdownOptions(DropdownListStyle.LARGE);
		DropdownOptions options8 = new DropdownOptions(DropdownListAlignment.RIGHT);
		DropdownOptions options9 = new DropdownOptions(DropdownHover.HOVERABLE);
	}
}
