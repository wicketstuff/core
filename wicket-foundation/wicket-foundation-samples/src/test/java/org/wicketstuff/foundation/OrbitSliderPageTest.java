package org.wicketstuff.foundation;

import org.junit.jupiter.api.Test;

public class OrbitSliderPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(OrbitSliderPage.class);
		tester.assertRenderedPage(OrbitSliderPage.class);
	}
}
