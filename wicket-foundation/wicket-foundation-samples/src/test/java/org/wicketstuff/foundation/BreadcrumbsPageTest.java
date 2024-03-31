package org.wicketstuff.foundation;

import org.junit.jupiter.api.Test;

public class BreadcrumbsPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(BreadcrumbsPage.class);
		tester.assertRenderedPage(BreadcrumbsPage.class);
	}

}
