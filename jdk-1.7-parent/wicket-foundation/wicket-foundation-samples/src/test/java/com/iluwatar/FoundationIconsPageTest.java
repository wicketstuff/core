package com.iluwatar;

import org.junit.Test;

public class FoundationIconsPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(FoundationIconsPage.class);
		tester.assertRenderedPage(FoundationIconsPage.class);
	}
}
