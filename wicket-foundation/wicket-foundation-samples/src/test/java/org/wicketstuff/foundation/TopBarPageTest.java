package org.wicketstuff.foundation;

import org.junit.Test;
import org.wicketstuff.foundation.TopBarPage;

public class TopBarPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(TopBarPage.class);
		tester.assertRenderedPage(TopBarPage.class);
	}
}
