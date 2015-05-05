package com.iluwatar;

import org.junit.Test;

public class SubNavPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(SubNavPage.class);
		tester.assertRenderedPage(SubNavPage.class);
	}
}
