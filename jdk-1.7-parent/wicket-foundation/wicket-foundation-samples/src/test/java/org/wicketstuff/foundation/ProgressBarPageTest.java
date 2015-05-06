package org.wicketstuff.foundation;

import org.junit.Test;
import org.wicketstuff.foundation.ProgressBarPage;

public class ProgressBarPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(ProgressBarPage.class);
		tester.assertRenderedPage(ProgressBarPage.class);
	}
}
