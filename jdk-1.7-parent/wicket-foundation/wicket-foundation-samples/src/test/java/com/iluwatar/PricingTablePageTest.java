package com.iluwatar;

import org.junit.Test;

public class PricingTablePageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(PricingTablePage.class);
		tester.assertRenderedPage(PricingTablePage.class);
	}
}
