package org.wicketstuff.foundation.revealmodal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.Test;

public class FoundationRevealModalTest {

	@Test
	public void test() {
		WicketTester tester = new WicketTester();
		FoundationRevealModal modal = new FoundationRevealModal("modal", Model.of("Click for modal"), Model.of("<h1>heading</h1><p>some text comes here</p>"));
		tester.startComponentInPage(modal);
		tester.debugComponentTrees();
		tester.dumpPage();
		String dataRevealId = tester.getTagByWicketId("openLink").getAttribute("data-reveal-id");
		String modalContentId = tester.getTagByWicketId("modalContent").getAttribute("id");
		assertEquals(dataRevealId, modalContentId);
		assertEquals("reveal-modal", tester.getTagByWicketId("modalContent").getAttribute("class"));
		assertTrue(tester.getTagByWicketId("modalContent").hasAttribute("data-reveal"));
	}
}
