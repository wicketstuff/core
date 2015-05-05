package com.iluwatar;

import org.junit.Test;

public class SideNavPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(SideNavPage.class);
		tester.assertRenderedPage(SideNavPage.class);
	}
}
