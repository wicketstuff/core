package org.wicketstuff.foundation;

import org.junit.jupiter.api.Test;

public class ThumbnailsPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(ThumbnailsPage.class);
		tester.assertRenderedPage(ThumbnailsPage.class);
	}
}
