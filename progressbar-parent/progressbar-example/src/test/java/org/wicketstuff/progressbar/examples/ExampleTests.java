package org.wicketstuff.progressbar.examples;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.wicket.Page;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wicketstuff.progressbar.ProgressBar;

/**
 * Just trivial test cases to test that example pages render.
 *
 * @author Christopher Hlubek
 *
 */
public class ExampleTests
{

	private WicketTester tester;

	@BeforeEach
	public void setUp()
	{
		tester = new WicketTester(new ExampleApplication());
	}

	@Test
	public void testSimpleProgressExamplePage()
	{
		Page page = tester.startPage(SimpleProgressExamplePage.class);
		assertNotNull(page.get("form:bar"));
		assertTrue(page.get("form:bar") instanceof ProgressBar);
	}

	@Test
	public void testTaskServiceProgressExamplePage()
	{
		Page page = tester.startPage(TaskServiceProgressExamplePage.class);
		assertNotNull(page.get("form:bar"));
		assertTrue(page.get("form:bar") instanceof ProgressBar);
	}
}
