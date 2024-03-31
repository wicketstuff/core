package org.wicketstuff.navigator;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class TesterBase {
	private static WicketTester tester;

	@BeforeAll
	static void init() {
		tester = new WicketTester();
	}

	@AfterAll
	static void cleanup() {
		if (tester != null) {
			tester.destroy();
		}
	}
}
