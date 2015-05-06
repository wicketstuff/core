package org.wicketstuff.foundation.foundationpanel;

import static org.junit.Assert.assertEquals;

import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

public class FoundationBorderTest {
	
	@Test
	public void renderNormalTest() {
		WicketTester tester = new WicketTester();
		FoundationPanelBorder border = new FoundationPanelBorder("foo", Model.of(PanelType.NORMAL));
		tester.startComponentInPage(border);
		TagTester tagTester = tester.getTagByWicketId("wrapper");
		assertEquals("panel", tagTester.getAttribute("class"));
	}
	
	@Test
	public void renderCalloutTest() {
		WicketTester tester = new WicketTester();
		FoundationPanelBorder border = new FoundationPanelBorder("foo", Model.of(PanelType.CALLOUT));
		tester.startComponentInPage(border);
		TagTester tagTester = tester.getTagByWicketId("wrapper");
		assertEquals("panel callout radius", tagTester.getAttribute("class"));
	}
}
