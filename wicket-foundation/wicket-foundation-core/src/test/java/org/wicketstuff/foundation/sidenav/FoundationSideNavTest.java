package org.wicketstuff.foundation.sidenav;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

public class FoundationSideNavTest {

	@Test
	public void test() {
		WicketTester tester = new WicketTester();
		List<SideNavItem> items= new ArrayList<>();
		items.add(new SideNavHeaderItem("Header"));
		items.add(new SideNavLinkItem("Link 1", true));
		items.add(new SideNavLinkItem("Link 2"));
		items.add(new SideNavLinkItem("Link 3"));
		items.add(new SideNavDividerItem());
		items.add(new SideNavLinkItem("Link 4"));
		FoundationSideNav sideNav = new FoundationSideNav("id", items) {

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
		tester.startComponentInPage(sideNav);
		tester.dumpPage();
		TagTester sideNavContainer = tester.getTagByWicketId("sideNavContainer");
		assertTrue(sideNavContainer.getAttributeIs("class", "side-nav"));
		List<TagTester> sideNavItems = tester.getTagsByWicketId("sideNavItem");
		assertEquals(sideNavItems.size(), items.size());
		assertTrue(sideNavItems.get(0).getAttributeIs("class", "heading"));
		assertTrue(sideNavItems.get(0).getValue().contains("Header"));
		assertTrue(sideNavItems.get(1).getAttributeIs("class", "active"));
		assertTrue(sideNavItems.get(1).getValue().contains("Link 1"));
		assertTrue(sideNavItems.get(2).getValue().contains("Link 2"));
		assertTrue(sideNavItems.get(3).getValue().contains("Link 3"));
		assertTrue(sideNavItems.get(4).getAttributeIs("class", "divider"));
		assertTrue(sideNavItems.get(5).getValue().contains("Link 4"));
	}
}
