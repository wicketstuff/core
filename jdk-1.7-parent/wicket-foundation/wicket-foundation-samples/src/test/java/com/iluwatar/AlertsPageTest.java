package com.iluwatar;

import org.junit.Test;

public class AlertsPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(AlertsPage.class);
		tester.assertRenderedPage(AlertsPage.class);
	}
}
