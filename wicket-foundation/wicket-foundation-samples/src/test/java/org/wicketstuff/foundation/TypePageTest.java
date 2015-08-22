package org.wicketstuff.foundation;

import org.junit.Test;
import org.wicketstuff.foundation.TypePage;

public class TypePageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(TypePage.class);
		tester.assertRenderedPage(TypePage.class);
	}

}
