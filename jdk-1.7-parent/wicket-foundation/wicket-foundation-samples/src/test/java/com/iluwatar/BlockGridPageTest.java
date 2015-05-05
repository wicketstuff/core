package com.iluwatar;

import org.junit.Test;

public class BlockGridPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(BlockGridPage.class);
		tester.assertRenderedPage(BlockGridPage.class);
	}

}
