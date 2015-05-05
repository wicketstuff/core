package com.iluwatar;

import org.junit.Test;

public class BreadcrumbsPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(BreadcrumbsPage.class);
		tester.assertRenderedPage(BreadcrumbsPage.class);
	}

}
