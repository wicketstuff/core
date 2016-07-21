package org.wicketstuff.foundation.tooltip;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;
import org.wicketstuff.foundation.button.ButtonRadius;

public class FoundationTooltipBehaviorTest {

	@Test
	public void testBasic() {
		WicketTester tester = new WicketTester();
		Label label = new Label("id", Model.of("Some content"));
		FoundationTooltipBehavior tooltip = new FoundationTooltipBehavior("My tooltip");
		label.add(tooltip);
		tester.startComponentInPage(label);
		TagTester idTag = tester.getTagByWicketId("id");
		assertTrue(idTag.hasAttribute("data-tooltip"));
		assertTrue(idTag.getAttributeIs("aria-haspopup", "true"));
		assertTrue(idTag.getAttributeContains("class", "has-tip"));
		assertTrue(idTag.getAttributeIs("title", "My tooltip"));
		assertEquals("Some content", idTag.getValue());
	}
	
	@Test
	public void testAdvanced() {
		WicketTester tester = new WicketTester();
		Label label = new Label("id", Model.of("Some content"));
		TooltipOptions options = new TooltipOptions(true).setPosition(TooltipPosition.TIP_RIGHT)
				.setRadius(ButtonRadius.ROUND).setVisibility(TooltipVisibility.MEDIUM);
		FoundationTooltipBehavior tooltip = new FoundationTooltipBehavior("My tooltip", options);
		label.add(tooltip);
		tester.startComponentInPage(label);
		tester.dumpPage();
		TagTester idTag = tester.getTagByWicketId("id");
		assertTrue(idTag.hasAttribute("data-tooltip"));
		assertTrue(idTag.getAttributeIs("aria-haspopup", "true"));
		assertTrue(idTag.getAttributeContains("class", "has-tip"));
		assertTrue(idTag.getAttributeIs("title", "My tooltip"));
		assertEquals("Some content", idTag.getValue());
		assertTrue(idTag.getAttributeContains("class", "tip-right"));
		assertTrue(idTag.getAttributeContains("class", "round"));
		assertTrue(idTag.getAttributeContains("data-options", "disable_for_touch:true"));
		assertTrue(idTag.getAttributeContains("data-options", "show_on:medium"));
	}
}
