package org.wicketstuff.foundation;

import org.junit.Test;
import org.wicketstuff.foundation.ButtonGroupPage;

public class ButtonGroupPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(ButtonGroupPage.class);
		tester.assertRenderedPage(ButtonGroupPage.class);
	}

}
