package com.iluwatar;

import org.junit.Test;

public class DropdownButtonPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(DropdownButtonsPage.class);
		tester.assertRenderedPage(DropdownButtonsPage.class);
	}
}
