package com.iluwatar;

import org.junit.Test;

public class DropdownsPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(DropdownsPage.class);
		tester.assertRenderedPage(DropdownsPage.class);
	}
}
