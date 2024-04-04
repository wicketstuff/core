package org.wicketstuff.foundation;

import org.junit.jupiter.api.Test;

public class VisibilityPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(VisibilityPage.class);
		tester.assertRenderedPage(VisibilityPage.class);
	}

}
