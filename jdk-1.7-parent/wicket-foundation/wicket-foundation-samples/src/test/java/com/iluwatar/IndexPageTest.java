package com.iluwatar;

import org.junit.Test;

public class IndexPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(IndexPage.class);
		tester.assertRenderedPage(IndexPage.class);
	}

}
