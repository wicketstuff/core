package org.wicketstuff.foundation;

import org.junit.jupiter.api.Test;

public class DropdownButtonPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(DropdownButtonsPage.class);
		tester.assertRenderedPage(DropdownButtonsPage.class);
	}
}
