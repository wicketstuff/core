package com.iluwatar;

import org.junit.Test;

public class ThumbnailsPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(ThumbnailsPage.class);
		tester.assertRenderedPage(ThumbnailsPage.class);
	}
}
