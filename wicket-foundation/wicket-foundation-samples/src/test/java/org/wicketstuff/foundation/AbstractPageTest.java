package org.wicketstuff.foundation;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.BeforeEach;

public class AbstractPageTest {

	protected WicketTester tester;

	@BeforeEach
	public void setUp()
	{
		tester = new WicketTester(new WicketApplication());
	}

}
