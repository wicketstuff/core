package com.iluwatar;

import org.junit.Test;

public class TypePageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(TypePage.class);
		tester.assertRenderedPage(TypePage.class);
	}

}
