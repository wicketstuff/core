package org.wicketstuff.foundation.dropdown;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;
import org.wicketstuff.foundation.button.ButtonColor;
import org.wicketstuff.foundation.button.ButtonExpansion;
import org.wicketstuff.foundation.button.ButtonRadius;
import org.wicketstuff.foundation.button.ButtonSize;

public class FoundationDropdownTest {

	@Test
	public void renderDropdownTest() { 
		WicketTester tester = new WicketTester();
		FoundationDropdown dropdown = new FoundationDropdown("id", "dropdown", new DropdownOptions(), Arrays.asList("foo", "bar", "baz")) {

			@Override
			protected AbstractLink createDropdownLink(int idx, String id) {
				return new Link<String>(id) {
					@Override
					public void onClick() {}
				};
			}
			
		};
		tester.startComponentInPage(dropdown);
		tester.dumpPage();
		TagTester btnTag = tester.getTagByWicketId("btn");
		TagTester containerTag = tester.getTagByWicketId("container");
		assertEquals(btnTag.getAttribute("data-dropdown"), containerTag.getAttribute("id"));
		assertEquals("a", btnTag.getName());
		assertTrue(containerTag.getMarkup().contains("data-dropdown-content"));
		assertTrue(containerTag.getAttributeContains("class", "f-dropdown"));
		assertEquals(3, tester.getTagsByWicketId("item").size());
		assertEquals(3, tester.getTagsByWicketId("link").size());
		assertEquals(3, tester.getTagsByWicketId("body").size());
		assertEquals(tester.getTagsByWicketId("body").get(0).getValue(), "foo");
		assertEquals(tester.getTagsByWicketId("body").get(1).getValue(), "bar");
		assertEquals(tester.getTagsByWicketId("body").get(2).getValue(), "baz");
	}
	
	@Test
	public void renderDropdownLinkTest() { 
		WicketTester tester = new WicketTester();
		FoundationDropdown dropdown = new FoundationDropdown("id", "dropdown", new DropdownOptions(DropdownType.DROPDOWNLINK), Arrays.asList("foo", "bar", "baz")) {

			@Override
			protected AbstractLink createDropdownLink(int idx, String id) {
				return new Link<String>(id) {
					@Override
					public void onClick() {}
				};
			}
			
		};
		tester.startComponentInPage(dropdown);
		tester.dumpPage();
		TagTester btnTag = tester.getTagByWicketId("btn");
		TagTester containerTag = tester.getTagByWicketId("container");
		assertEquals(btnTag.getAttribute("data-dropdown"), containerTag.getAttribute("id"));
		assertEquals("a", btnTag.getName());
		assertEquals("button", btnTag.getAttribute("class"));
		assertTrue(containerTag.getMarkup().contains("data-dropdown-content"));
		assertTrue(containerTag.getAttributeContains("class", "f-dropdown"));
		assertEquals(3, tester.getTagsByWicketId("item").size());
		assertEquals(3, tester.getTagsByWicketId("link").size());
		assertEquals(3, tester.getTagsByWicketId("body").size());
		assertEquals(tester.getTagsByWicketId("body").get(0).getValue(), "foo");
		assertEquals(tester.getTagsByWicketId("body").get(1).getValue(), "bar");
		assertEquals(tester.getTagsByWicketId("body").get(2).getValue(), "baz");
	}
		
	@Test
	public void renderDropdownButtonTest() { 
		WicketTester tester = new WicketTester();
		FoundationDropdown dropdown = new FoundationDropdown("id", "dropdown", new DropdownOptions(DropdownType.DROPDOWNBUTTON), Arrays.asList("foo", "bar", "baz")) {

			@Override
			protected AbstractLink createDropdownLink(int idx, String id) {
				return new Link<String>(id) {
					@Override
					public void onClick() {}
				};
			}
			
		};
		tester.startComponentInPage(dropdown);
		tester.dumpPage();
		TagTester btnTag = tester.getTagByWicketId("btn");
		TagTester containerTag = tester.getTagByWicketId("container");
		assertEquals(btnTag.getAttribute("data-dropdown"), containerTag.getAttribute("id"));
		assertEquals("button", btnTag.getName());
		assertTrue(btnTag.getAttributeContains("class", "button"));
		assertTrue(btnTag.getAttributeContains("class", "dropdown"));
		assertTrue(containerTag.getMarkup().contains("data-dropdown-content"));
		assertTrue(containerTag.getAttributeContains("class", "f-dropdown"));
		assertEquals(3, tester.getTagsByWicketId("item").size());
		assertEquals(3, tester.getTagsByWicketId("link").size());
		assertEquals(3, tester.getTagsByWicketId("body").size());
		assertEquals(tester.getTagsByWicketId("body").get(0).getValue(), "foo");
		assertEquals(tester.getTagsByWicketId("body").get(1).getValue(), "bar");
		assertEquals(tester.getTagsByWicketId("body").get(2).getValue(), "baz");
	}			
	
	@Test
	public void renderDropdownButtonAdvancedTest() {
		WicketTester tester = new WicketTester();
		
		DropdownOptions options = new DropdownOptions(DropdownType.DROPDOWNBUTTON);
		options.setColor(ButtonColor.ALERT);
		options.setExpansion(ButtonExpansion.EXPAND);
		options.setHover(DropdownHover.HOVERABLE);
		options.setListAlignment(DropdownListAlignment.LEFT);
		options.setListStyle(DropdownListStyle.LARGE);
		options.setRadius(ButtonRadius.ROUND);
		options.setSize(ButtonSize.LARGE);
		
		FoundationDropdown dropdown = new FoundationDropdown("id", "dropdown", options, Arrays.asList("foo", "bar", "baz")) {

			@Override
			protected AbstractLink createDropdownLink(int idx, String id) {
				return new Link<String>(id) {
					@Override
					public void onClick() {
					}
				};
			}

		};
		tester.startComponentInPage(dropdown);
		tester.dumpPage();
		TagTester btnTag = tester.getTagByWicketId("btn");
		TagTester containerTag = tester.getTagByWicketId("container");
		assertEquals(btnTag.getAttribute("data-dropdown"),
				containerTag.getAttribute("id"));
		assertEquals("button", btnTag.getName());
		assertTrue(btnTag.getAttributeContains("class", "button"));
		assertTrue(btnTag.getAttributeContains("class", "dropdown"));
		assertTrue(containerTag.getMarkup().contains("data-dropdown-content"));
		assertTrue(containerTag.getAttributeContains("class", "f-dropdown"));
		assertEquals(3, tester.getTagsByWicketId("item").size());
		assertEquals(3, tester.getTagsByWicketId("link").size());
		assertEquals(3, tester.getTagsByWicketId("body").size());
		assertEquals(tester.getTagsByWicketId("body").get(0).getValue(), "foo");
		assertEquals(tester.getTagsByWicketId("body").get(1).getValue(), "bar");
		assertEquals(tester.getTagsByWicketId("body").get(2).getValue(), "baz");
		
		assertTrue(btnTag.getAttributeContains("class", "alert"));
		assertTrue(btnTag.getAttributeContains("class", "round"));
		assertTrue(btnTag.getAttributeContains("class", "large"));
		assertTrue(btnTag.getAttributeContains("class", "expand"));
		assertTrue(btnTag.getAttributeContains("data-options", "align:left"));
		assertTrue(btnTag.getAttributeContains("data-options", "is_hover:true"));
		assertTrue(containerTag.getAttributeContains("class", "large"));
	}
	
	@Test
	public void renderDropdownContentTest() { 
		WicketTester tester = new WicketTester();
		final String testContent = "<b>frog</b>";
		FoundationContentDropdown dropdown = new FoundationContentDropdown("id", "dropdown", new DropdownOptions(DropdownType.DROPDOWNCONTENT), testContent);
		tester.startComponentInPage(dropdown);
		tester.dumpPage();
		TagTester btnTag = tester.getTagByWicketId("btn");
		TagTester containerTag = tester.getTagByWicketId("content");
		assertEquals(btnTag.getAttribute("data-dropdown"), containerTag.getAttribute("id"));
		assertEquals("a", btnTag.getName());
		assertTrue(containerTag.getMarkup().contains("data-dropdown-content"));
		assertTrue(containerTag.getAttributeContains("class", "f-dropdown"));
		assertEquals(0, tester.getTagsByWicketId("item").size());
		assertEquals(0, tester.getTagsByWicketId("link").size());
		assertEquals(0, tester.getTagsByWicketId("body").size());
		assertEquals(containerTag.getValue(), testContent);
	}	
}
