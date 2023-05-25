package org.wicketstuff.foundation;

import org.junit.jupiter.api.Test;

public class ButtonGroupPageTest extends AbstractPageTest {

	@Test
	public void test() {
		tester.startPage(ButtonGroupPage.class);
		tester.assertRenderedPage(ButtonGroupPage.class);
	}

}
