package com.iluwatar;

import org.junit.Test;

public class VisibilityPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(VisibilityPage.class);
		tester.assertRenderedPage(VisibilityPage.class);
	}

}
