package org.wicketstuff.foundation.label;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.Test;
import org.wicketstuff.foundation.button.ButtonColor;
import org.wicketstuff.foundation.button.ButtonRadius;

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
