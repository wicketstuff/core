package com.iluwatar;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

public class InlineListsPageTest {

	@Test
	public void render(){
		WicketTester tester = new WicketTester();
		tester.startPage(InlineListsPage.class);
		tester.assertRenderedPage(InlineListsPage.class);
	}
	
}
