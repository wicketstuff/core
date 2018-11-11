package org.wicketstuff.foundation;

import org.junit.jupiter.api.Test;
import org.wicketstuff.foundation.AlertsPage;

public class AlertsPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(AlertsPage.class);
		tester.assertRenderedPage(AlertsPage.class);
	}
}
