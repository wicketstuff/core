package org.wicketstuff.foundation;

import org.junit.jupiter.api.Test;

public class TypePageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(TypePage.class);
		tester.assertRenderedPage(TypePage.class);
	}

}
