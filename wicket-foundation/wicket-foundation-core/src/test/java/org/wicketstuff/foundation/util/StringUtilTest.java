package org.wicketstuff.foundation.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class StringUtilTest {

	@Test
	public void testEnumNameToCssClassNameNull() {
		assertThrows(IllegalArgumentException.class, () -> {
			StringUtil.EnumNameToCssClassName(null);
		});
	}

	@Test
	public void testEnumNameToCssClassName() {
		assertEquals("show-for-xxlarge-up", StringUtil.EnumNameToCssClassName("SHOW_FOR_XXLARGE_UP"));

		assertEquals("abc123", StringUtil.EnumNameToCssClassName("ABC123"));
	}

}
