package org.wicketstuff.foundation.foundationpanel;

import static org.junit.Assert.assertEquals;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

public class FoundationPanelTest {

	@Test
	public void renderNormalTest() {
		WicketTester tester = new WicketTester();
		TestPanel panel = new TestPanel("panel", Model.of(PanelType.NORMAL));
		tester.startComponentInPage(panel);
		TagTester tagTester = tester.getTagByWicketId("panel");
		assertEquals("panel", tagTester.getAttribute("class"));
	}

	@Test
	public void renderCalloutTest() {
		WicketTester tester = new WicketTester();
		TestPanel panel = new TestPanel("panel", Model.of(PanelType.CALLOUT));
		tester.startComponentInPage(panel);
		TagTester tagTester = tester.getTagByWicketId("panel");
		assertEquals("panel callout radius", tagTester.getAttribute("class"));
	}
	
	private static class TestPanel extends FoundationPanel {

		private static final long serialVersionUID = 1L;

		public TestPanel(String id, IModel<PanelType> type) {
			super(id, type);
		}
	}
}
