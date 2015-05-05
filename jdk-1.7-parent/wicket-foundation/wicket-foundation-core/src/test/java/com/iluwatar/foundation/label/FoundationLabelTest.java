package com.iluwatar.foundation.label;

import static org.junit.Assert.assertTrue;

import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

import com.iluwatar.foundation.button.ButtonColor;
import com.iluwatar.foundation.button.ButtonRadius;

public class FoundationLabelTest {

	@Test
	public void test() {
		WicketTester tester = new WicketTester();
		FoundationLabel label = new FoundationLabel("id", "hello", new LabelOptions(ButtonColor.SUCCESS, ButtonRadius.ROUND));
		tester.startComponentInPage(label);
		tester.dumpPage();
		TagTester tag = tester.getTagByWicketId("id");
		assertTrue(tag.getAttributeContains("class", "label"));
		assertTrue(tag.getAttributeContains("class", "success"));
		assertTrue(tag.getAttributeContains("class", "round"));
	}
}
