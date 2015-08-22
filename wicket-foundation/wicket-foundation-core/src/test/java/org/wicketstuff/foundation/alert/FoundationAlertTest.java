package org.wicketstuff.foundation.alert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;
import org.wicketstuff.foundation.button.ButtonColor;
import org.wicketstuff.foundation.button.ButtonRadius;
import org.wicketstuff.foundation.util.StringUtil;

public class FoundationAlertTest {

	@Test
	public void testBasic() {
		WicketTester tester = new WicketTester();
		FoundationAlert alert = new FoundationAlert("alert", Model.of("hello world"), new AlertOptions());
		tester.startComponentInPage(alert);
		TagTester alertTag = tester.getTagByWicketId("alert");
		assertTrue(alertTag.getMarkup().contains("data-alert"));
		assertTrue(alertTag.getAttributeIs("class", "alert-box"));
		assertTrue(alertTag.hasChildTag("a"));
		TagTester bodyTag = tester.getTagByWicketId("body");
		assertEquals("hello world", bodyTag.getValue());
	}
	
	@Test
	public void testAdvanced() {
		WicketTester tester = new WicketTester();
		AlertOptions options = new AlertOptions().setColor(AlertColor.ALERT).setRadius(ButtonRadius.ROUND);
		FoundationAlert alert = new FoundationAlert("alert", Model.of("hello world"), options);
		tester.startComponentInPage(alert);
		TagTester alertTag = tester.getTagByWicketId("alert");
		assertTrue(alertTag.getMarkup().contains("data-alert"));
		assertTrue(alertTag.getAttributeContains("class", "alert-box"));
		assertTrue(alertTag.getAttributeContains("class", StringUtil.EnumNameToCssClassName(ButtonColor.ALERT.name())));
		assertTrue(alertTag.getAttributeContains("class", StringUtil.EnumNameToCssClassName(ButtonRadius.ROUND.name())));
		assertTrue(alertTag.hasChildTag("a"));
		TagTester bodyTag = tester.getTagByWicketId("body");
		assertEquals("hello world", bodyTag.getValue());
	}
}
