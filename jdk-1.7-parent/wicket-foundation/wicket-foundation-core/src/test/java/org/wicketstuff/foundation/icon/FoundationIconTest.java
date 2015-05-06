package org.wicketstuff.foundation.icon;

import static org.junit.Assert.assertTrue;

import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

public class FoundationIconTest {

	@Test
	public void testBasic() {
		WicketTester tester = new WicketTester();
		FoundationIcon foundationIcon = new FoundationIcon("id", IconType.ARROW_RIGHT);
		tester.startComponentInPage(foundationIcon);
		TagTester idTag = tester.getTagByWicketId("id");
		assertTrue(idTag.getAttributeIs("class", "fi-arrow-right"));
	}
	
	@Test
	public void testAdvanced() {
		WicketTester tester = new WicketTester();
		FoundationIcon foundationIcon = new FoundationIcon("id", IconType.ARROW_RIGHT, IconSize.LARGE);
		tester.startComponentInPage(foundationIcon);
		TagTester idTag = tester.getTagByWicketId("id");
		assertTrue(idTag.getAttributeContains("class", "fi-arrow-right"));
		assertTrue(idTag.getAttributeContains("class", "large"));
	}

}
