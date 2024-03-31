package org.wicketstuff.foundation;

import org.junit.jupiter.api.Test;

public class TopBarPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(TopBarPage.class);
		tester.assertRenderedPage(TopBarPage.class);
	}
}
