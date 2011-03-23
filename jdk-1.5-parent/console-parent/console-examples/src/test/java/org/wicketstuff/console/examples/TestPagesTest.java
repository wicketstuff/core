package org.wicketstuff.console.examples;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import org.wicketstuff.console.examples.ClojureEngineTestPage;
import org.wicketstuff.console.examples.WicketApplication;

public class TestPagesTest {

	private WicketTester tester;

	@Before
	public void setUp() {
		tester = new WicketTester(new WicketApplication());
	}

	@Test
	public void test_rendersSuccessfully_ClojureEngineTestPage() {
		tester.startPage(ClojureEngineTestPage.class);

		tester.assertRenderedPage(ClojureEngineTestPage.class);
	}

	@Test
	public void test_rendersSuccessfully_GroovyEngineTestPage() {
		tester.startPage(GroovyEngineTestPage.class);

		tester.assertRenderedPage(GroovyEngineTestPage.class);
	}

}
