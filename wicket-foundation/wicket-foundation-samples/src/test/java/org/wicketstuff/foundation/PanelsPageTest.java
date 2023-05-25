package org.wicketstuff.foundation;

import org.junit.jupiter.api.Test;

public class PanelsPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(PanelsPage.class);
		tester.assertRenderedPage(PanelsPage.class);
	}

}
