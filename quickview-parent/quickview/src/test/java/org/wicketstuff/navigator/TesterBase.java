package org.wicketstuff.navigator;

import org.apache.wicket.util.tester.WicketTester;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public abstract class TesterBase {
	private static WicketTester tester;

	@BeforeClass
	private static void init() {
		tester = new WicketTester();
	}

	@AfterClass
	private static void cleanup() {
		if (tester != null) {
			tester.destroy();
		}
	}
}
