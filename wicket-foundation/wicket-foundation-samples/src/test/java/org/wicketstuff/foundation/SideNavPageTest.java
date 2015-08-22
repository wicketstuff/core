package org.wicketstuff.foundation;

import org.junit.Test;
import org.wicketstuff.foundation.SideNavPage;

public class SideNavPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(SideNavPage.class);
		tester.assertRenderedPage(SideNavPage.class);
	}
}
