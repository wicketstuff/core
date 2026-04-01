package org.wicketstuff.select2;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.DummyHomePage;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import de.agilecoders.wicket.webjars.WicketWebjars;

public class AbstarctSelect2Test {
	protected WicketTester tester;

	@BeforeEach
	void beforeClass() {
		tester = new WicketTester(new WebApplication() {
			@Override
			public Class<? extends Page> getHomePage() {
				return DummyHomePage.class;
			}

			@Override
			protected void init() {
				super.init();
				WicketWebjars.install(this);
			}
		});
	}

	@AfterEach
	void afterClass() {
		tester.destroy();
	}
}
