package org.wicketstuff.servlet3;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage
{
	private WicketTester tester;

	@BeforeEach
	public void setUp()
	{
		tester = new WicketTester(new WicketApplication());
	}

	@AfterEach
	public void tearDown() {
		tester.destroy();
	}

	@Test
	public void testRenderMyPage()
	{
		// start and render the test page
		tester.startPage(HomePage.class);

		// assert rendered page class
		tester.assertRenderedPage(HomePage.class);

		// assert rendered label component
		tester.assertLabel("message",
			"If you see this message wicket is properly configured and running");
	}
}
