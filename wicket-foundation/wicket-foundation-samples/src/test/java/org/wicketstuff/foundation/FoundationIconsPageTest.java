package org.wicketstuff.foundation;

import org.junit.jupiter.api.Test;

public class FoundationIconsPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(FoundationIconsPage.class);
		tester.assertRenderedPage(FoundationIconsPage.class);
	}
}
