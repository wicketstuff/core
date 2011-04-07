package org.wicketstuff.minis.component;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Test;

/**
 * Tests for {@link DefaultInvisibleFeedbackPanel}.
 *
 * @author akiraly
 */
public class DefaultInvisibleFeedbackPanelTest {
	private WicketTester tester = new WicketTester();

	@After
	public void after() {
		tester.destroy();
	}

	@Test
	public void testVisibility() {
		FeedbackPanel feedbackPanel = new DefaultInvisibleFeedbackPanel(
				"foo");

		tester.startComponent(feedbackPanel, null);
		tester.assertInvisible("");

		tester.getSession().info("info");

		tester.startPage(tester.getLastRenderedPage());
		tester.assertVisible(feedbackPanel.getId());

		tester.startPage(tester.getLastRenderedPage());
		tester.assertInvisible(feedbackPanel.getId());
	}
}
