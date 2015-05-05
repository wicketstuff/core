package com.iluwatar;

import org.junit.Test;

public class RevealModalPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(RevealModalPage.class);
		tester.assertRenderedPage(RevealModalPage.class);
	}
}
