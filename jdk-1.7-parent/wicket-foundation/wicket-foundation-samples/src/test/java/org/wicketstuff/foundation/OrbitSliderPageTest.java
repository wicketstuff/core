package org.wicketstuff.foundation;

import org.junit.Test;
import org.wicketstuff.foundation.OrbitSliderPage;

public class OrbitSliderPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(OrbitSliderPage.class);
		tester.assertRenderedPage(OrbitSliderPage.class);
	}
}
