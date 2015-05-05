package com.iluwatar;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;

public class AbstractPageTest {

	protected WicketTester tester;

	@Before
	public void setUp()
	{
		tester = new WicketTester(new WicketApplication());
	}
	
}
