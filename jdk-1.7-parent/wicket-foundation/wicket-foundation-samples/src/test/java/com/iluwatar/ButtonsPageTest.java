package com.iluwatar;

import org.junit.Test;

public class ButtonsPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(ButtonsPage.class);
		tester.assertRenderedPage(ButtonsPage.class);
	}

}
