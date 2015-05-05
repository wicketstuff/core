package com.iluwatar;

import org.junit.Test;

public class OrbitSliderPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(OrbitSliderPage.class);
		tester.assertRenderedPage(OrbitSliderPage.class);
	}
}
