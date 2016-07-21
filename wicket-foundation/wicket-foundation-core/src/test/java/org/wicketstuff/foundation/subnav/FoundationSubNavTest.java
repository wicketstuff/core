package org.wicketstuff.foundation.subnav;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

public class FoundationSubNavTest {

	@Test
	public void test() {
		WicketTester tester = new WicketTester();
		List<SubNavItem> items = new ArrayList<>();
		items.add(new SubNavItem("All", true));
		items.add(new SubNavItem("Active"));
		items.add(new SubNavItem("Pending"));
		items.add(new SubNavItem("Suspended"));
		FoundationSubNav subNav = new FoundationSubNav("id", "Nav title", items) {

			private static final long serialVersionUID = 1L;

			@Override
			public AbstractLink createLink(String id, int idx) {
				return new Link<Void>(id) {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
					}
				};
			}
		};
		tester.startComponentInPage(subNav);
		tester.dumpPage();
	}
}
