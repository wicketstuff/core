package com.iluwatar;

import org.junit.Test;

public class TooltipsPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(TooltipsPage.class);
		tester.assertRenderedPage(TooltipsPage.class);
	}

}
