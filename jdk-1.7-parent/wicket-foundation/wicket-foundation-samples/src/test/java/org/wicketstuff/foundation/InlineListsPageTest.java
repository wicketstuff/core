package org.wicketstuff.foundation;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;
import org.wicketstuff.foundation.InlineListsPage;

public class InlineListsPageTest {

	@Test
	public void render(){
		WicketTester tester = new WicketTester();
		tester.startPage(InlineListsPage.class);
		tester.assertRenderedPage(InlineListsPage.class);
	}
	
}
