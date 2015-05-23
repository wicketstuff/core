package org.wicketstuff.foundation.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringUtilTest {

	@Test(expected=IllegalArgumentException.class)
	public void testEnumNameToCssClassNameNull() {
		
		StringUtil.EnumNameToCssClassName(null);
		
	}

	@Test
	public void testEnumNameToCssClassName() {
		
		assertEquals("show-for-xxlarge-up", StringUtil.EnumNameToCssClassName("SHOW_FOR_XXLARGE_UP"));

		assertEquals("abc123", StringUtil.EnumNameToCssClassName("ABC123"));
		
	}
	
}
