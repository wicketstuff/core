package org.wicketstuff.foundation;

import org.junit.jupiter.api.Test;

public class IndexPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(IndexPage.class);
		tester.assertRenderedPage(IndexPage.class);
	}

}
