package org.wicketstuff.foundation.visibility;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wicketstuff.foundation.visibility.FoundationShowByOrientationBehavior.ShowByOrientationType;

public class ShowByOrientationBehaviorTest {

	private WebMarkupContainer container;

	private WicketTester tester;

	@BeforeEach
	public void setup() {
		tester = new WicketTester();
		container = new WebMarkupContainer("id");
		container.add(new FoundationShowByOrientationBehavior(Model.of(ShowByOrientationType.SHOW_FOR_LANDSCAPE)));
	}

	@Test
	public void test() {
		tester.startComponentInPage(container);
		TagTester tagTester = tester.getTagByWicketId("id");
		assertEquals("show-for-landscape", tagTester.getAttribute("class"));
	}

}
