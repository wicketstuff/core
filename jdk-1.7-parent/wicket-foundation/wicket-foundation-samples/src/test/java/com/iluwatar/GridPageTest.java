package com.iluwatar;

import org.junit.Test;

public class GridPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(GridPage.class);
		tester.assertRenderedPage(GridPage.class);
	}

}
