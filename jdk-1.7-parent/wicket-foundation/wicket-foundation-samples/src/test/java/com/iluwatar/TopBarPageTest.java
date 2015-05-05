package com.iluwatar;

import org.junit.Test;

public class TopBarPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(TopBarPage.class);
		tester.assertRenderedPage(TopBarPage.class);
	}
}
