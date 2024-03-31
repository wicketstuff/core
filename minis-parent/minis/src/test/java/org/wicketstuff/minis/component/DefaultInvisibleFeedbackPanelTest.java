package org.wicketstuff.minis.component;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link DefaultInvisibleFeedbackPanel}.
 *
 * @author akiraly
 */
public class DefaultInvisibleFeedbackPanelTest
{
	private final WicketTester tester = new WicketTester();

	@AfterEach
	public void after()
	{
		tester.destroy();
	}

	@Test
	public void testVisibility()
	{
		FeedbackPanel feedbackPanel = new DefaultInvisibleFeedbackPanel("foo");

		tester.startComponentInPage(feedbackPanel, null);
		tester.assertInvisible(feedbackPanel.getId());

		tester.getSession().info("info");

		tester.startPage(tester.getLastRenderedPage());
		tester.assertVisible(feedbackPanel.getId());
		tester.clearFeedbackMessages();

		tester.startPage(tester.getLastRenderedPage());
		tester.assertInvisible(feedbackPanel.getId());
	}
}
