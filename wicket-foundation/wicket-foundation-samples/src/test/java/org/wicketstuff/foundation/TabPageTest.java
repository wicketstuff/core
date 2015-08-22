package org.wicketstuff.foundation;

import org.junit.Test;

public class TabPageTest extends AbstractPageTest {
	
	@Test
	public void test() {
		tester.startPage(TabPage.class);
		tester.assertRenderedPage(TabPage.class);
	}
}
