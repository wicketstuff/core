package org.wicketstuff.foundation.topbar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.EnumSet;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

public class FoundationTopBarPanelTest {

	@Test
	public void testBasic() {
		WicketTester tester = new WicketTester();

		ArrayList<TopBarItem> leftItems = new ArrayList<TopBarItem>();
		leftItems.add(new SimpleTopBarItem("foo", "Foo"));
		leftItems.add(new SimpleTopBarItem("bar", "Bar"));
		
		FoundationTopBar topBarPanel = new FoundationTopBar("id", new ArrayList<TopBarItem>(), leftItems) {
			@Override
			public WebMarkupContainer createTitleContainer(String id) {
				return new EmptyPanel(id);
			}
			@Override
			public AbstractLink createLink(String id, String itemId) {
				return new Link<Void>(id) {
					@Override
					public void onClick() {
					}
				};
			}
		};
		
		tester.startComponentInPage(topBarPanel);
//		tester.dumpPage();
		TagTester topBarContainer = tester.getTagByWicketId("topBarContainer");
		TagTester topBar = tester.getTagByWicketId("topBar");
		assertEquals("top-bar", topBar.getAttribute("class"));
		assertTrue(topBar.hasAttribute("data-topbar"));
		assertTrue(topBar.getAttributeIs("role", "navigation"));
		tester.assertComponent("id:topBarContainer:topBar:leftContainer:item:1:link", Link.class);
		tester.assertComponent("id:topBarContainer:topBar:leftContainer:item:2:link", Link.class);
//		tester.debugComponentTrees();
	}

	@Test
	public void testFixed() {
		WicketTester tester = new WicketTester();
		TopBarOptions options = new TopBarOptions().setFixed(true);
		
		FoundationTopBar topBarPanel = new FoundationTopBar("id", options, new ArrayList<TopBarItem>(), new ArrayList<TopBarItem>()) {
			@Override
			public WebMarkupContainer createTitleContainer(String id) {
				return new EmptyPanel(id);
			}
			@Override
			public AbstractLink createLink(String id, String itemId) {
				return new Link<Void>(id) {
					@Override
					public void onClick() {
					}
				};
			}
		};
		
		tester.startComponentInPage(topBarPanel);
		TagTester topBarContainer = tester.getTagByWicketId("topBarContainer");
		assertTrue(topBarContainer.getAttributeContains("class", "fixed"));
		TagTester topBar = tester.getTagByWicketId("topBar");
	}
	
	@Test
	public void testPosition() {
		WicketTester tester = new WicketTester();
		TopBarOptions options = new TopBarOptions().setClickable(true).setContainToGrid(true)
				.setSticky(true).setStickySizes(EnumSet.of(TopBarStickySize.LARGE));
		
		FoundationTopBar topBarPanel = new FoundationTopBar("id", options, new ArrayList<TopBarItem>(), new ArrayList<TopBarItem>()) {
			@Override
			public WebMarkupContainer createTitleContainer(String id) {
				return new EmptyPanel(id);
			}
			@Override
			public AbstractLink createLink(String id, String itemId) {
				return new Link<Void>(id) {
					@Override
					public void onClick() {
					}
				};
			}
		};
		
		tester.startComponentInPage(topBarPanel);
		//tester.dumpPage();
		TagTester topBarContainer = tester.getTagByWicketId("topBarContainer");
		assertTrue(topBarContainer.getAttributeContains("class", "contain-to-grid"));
		assertTrue(topBarContainer.getAttributeContains("class", "sticky"));
		TagTester topBar = tester.getTagByWicketId("topBar");
		assertTrue(topBar.getAttributeContains("data-options", "is_hover:false"));
		assertTrue(topBar.getAttributeContains("data-options", "sticky_on:large"));
	}
	
	@Test
	public void testTitlePanel() {
		WicketTester tester = new WicketTester();

		FoundationTopBar topBarPanel = new FoundationTopBar("id", new ArrayList<TopBarItem>(), new ArrayList<TopBarItem>()) {
			@Override
			public WebMarkupContainer createTitleContainer(String id) {
				return new FoundationTopBarTitle(id, Model.of("Home"), Model.of(TopBarMenuLayout.TITLE_AND_ICON), Model.of("Menu")) {
					@Override
					public AbstractLink createTitleLink(String id) {
						return new Link<Void>(id) {
							@Override
							public void onClick() {
							}
						};
					}
				};
			}
			@Override
			public AbstractLink createLink(String id, String itemId) {
				return new Link<Void>(id) {
					@Override
					public void onClick() {
					}
				};
			}
		};
		
		tester.startComponentInPage(topBarPanel);
		//tester.dumpPage();
		TagTester titleContainer = tester.getTagByWicketId("titleContainer");
		assertEquals("title-area", titleContainer.getAttribute("class"));
		TagTester titleLink = tester.getTagByWicketId("titleLink");
		tester.assertComponent("id:topBarContainer:topBar:titleContainer:titleLink", Link.class);
		TagTester titleLabel = tester.getTagByWicketId("titleLabel");
		assertEquals("Home", titleLabel.getValue());
		TagTester menuContainer = tester.getTagByWicketId("menuContainer");
		assertEquals("toggle-topbar menu-icon", menuContainer.getAttribute("class"));
		TagTester menuTitle = tester.getTagByWicketId("menuTitle");
		assertEquals("Menu", menuTitle.getValue());
	}
	
	@Test
	public void testDropdown() {
		WicketTester tester = new WicketTester();

		ArrayList<TopBarItem> leftItems = new ArrayList<TopBarItem>();
		SimpleTopBarItem foo = new SimpleTopBarItem("foo", "Foo");
		SimpleTopBarItem bar = new SimpleTopBarItem("bar", "Bar");
		foo.addChild(bar);
		bar.addChild(new SimpleTopBarItem("bar1", "Bar1"));
		bar.addChild(new SimpleTopBarItem("bar2", "Bar2"));
		leftItems.add(foo);
		
		FoundationTopBar topBarPanel = new FoundationTopBar("id", new ArrayList<TopBarItem>(), leftItems) {
			@Override
			public WebMarkupContainer createTitleContainer(String id) {
				return new EmptyPanel(id);
			}
			@Override
			public AbstractLink createLink(String id, String itemId) {
				return new Link<Void>(id) {
					@Override
					public void onClick() {
					}
				};
			}
		};
		
		tester.startComponentInPage(topBarPanel);
		tester.dumpPage();
		TagTester topBarContainer = tester.getTagByWicketId("topBarContainer");
		TagTester topBar = tester.getTagByWicketId("topBar");
		assertEquals("top-bar", topBar.getAttribute("class"));
		assertTrue(topBar.hasAttribute("data-topbar"));
		assertTrue(topBar.getAttributeIs("role", "navigation"));
		tester.debugComponentTrees();
		tester.assertComponent("id:topBarContainer:topBar:leftContainer:item:1", TopBarRecursiveLinkPanel.class);
		tester.assertComponent("id:topBarContainer:topBar:leftContainer:item:1:dropdown:item:1", TopBarRecursiveLinkPanel.class);
		tester.assertComponent("id:topBarContainer:topBar:leftContainer:item:1:dropdown:item:1:dropdown:item:1", TopBarRecursiveLinkPanel.class);
		tester.assertComponent("id:topBarContainer:topBar:leftContainer:item:1:dropdown:item:1:dropdown:item:2", TopBarRecursiveLinkPanel.class);
	}	
}
