package org.wicketstuff.foundation;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.wicketstuff.foundation.WicketApplication;

public class AbstractPageTest {

	protected WicketTester tester;

	@Before
	public void setUp()
	{
		tester = new WicketTester(new WicketApplication());
	}
	
}
