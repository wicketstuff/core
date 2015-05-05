package com.iluwatar;

import org.junit.Test;

public class PanelsPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(PanelsPage.class);
		tester.assertRenderedPage(PanelsPage.class);
	}

}
