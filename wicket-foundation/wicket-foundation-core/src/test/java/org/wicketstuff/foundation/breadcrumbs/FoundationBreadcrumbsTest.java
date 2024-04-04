package org.wicketstuff.foundation.breadcrumbs;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.Test;

public class FoundationBreadcrumbsTest {

	@Test
	public void test() {
		WicketTester tester = new WicketTester();
		List<BreadcrumbsItem> items = new ArrayList<>();
		items.add(new BreadcrumbsItem("First"));
		items.add(new BreadcrumbsItem("Second", true, true));
		items.add(new BreadcrumbsItem("Third"));
		items.add(new BreadcrumbsItem("Fourth"));
		FoundationBreadcrumbs breadcrumbs = new FoundationBreadcrumbs("id", items) {

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
		tester.startComponentInPage(breadcrumbs);
		tester.dumpPage();
		TagTester breadcrumbsContainer = tester.getTagByWicketId("breadcrumbsContainer");
		assertTrue(breadcrumbsContainer.getAttributeIs("class", "breadcrumbs"));
		List<TagTester> breadcrumbsItems = tester.getTagsByWicketId("breadcrumbsItem");
		assertEquals(4, breadcrumbsItems.size());
		assertTrue(breadcrumbsItems.get(1).getAttributeContains("class", "unavailable"));
		assertTrue(breadcrumbsItems.get(1).getAttributeContains("class", "current"));
	}
}
