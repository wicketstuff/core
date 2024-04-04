package org.wicketstuff.foundation;

import org.junit.jupiter.api.Test;

public class BlockGridPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(BlockGridPage.class);
		tester.assertRenderedPage(BlockGridPage.class);
	}

}
