package com.iluwatar;

import org.junit.Test;

public class ButtonGroupPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(ButtonGroupPage.class);
		tester.assertRenderedPage(ButtonGroupPage.class);
	}

}
