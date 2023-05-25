package org.wicketstuff.foundation;

import org.junit.jupiter.api.Test;

public class AlertsPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(AlertsPage.class);
		tester.assertRenderedPage(AlertsPage.class);
	}
}
