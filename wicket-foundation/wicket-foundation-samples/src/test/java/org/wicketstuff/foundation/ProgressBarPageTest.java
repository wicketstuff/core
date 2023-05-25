package org.wicketstuff.foundation;

import org.junit.jupiter.api.Test;

public class ProgressBarPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(ProgressBarPage.class);
		tester.assertRenderedPage(ProgressBarPage.class);
	}
}
