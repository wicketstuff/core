package com.iluwatar;

import org.junit.Test;

public class ProgressBarPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(ProgressBarPage.class);
		tester.assertRenderedPage(ProgressBarPage.class);
	}
}
