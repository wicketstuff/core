package org.wicketstuff.foundation;

import org.junit.jupiter.api.Test;
import org.wicketstuff.foundation.PanelsPage;

public class PanelsPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(PanelsPage.class);
		tester.assertRenderedPage(PanelsPage.class);
	}

}
