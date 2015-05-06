package org.wicketstuff.foundation;

import org.junit.Test;
import org.wicketstuff.foundation.FoundationIconsPage;

public class FoundationIconsPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(FoundationIconsPage.class);
		tester.assertRenderedPage(FoundationIconsPage.class);
	}
}
