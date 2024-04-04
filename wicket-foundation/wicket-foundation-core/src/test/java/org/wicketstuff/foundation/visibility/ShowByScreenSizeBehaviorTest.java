package org.wicketstuff.foundation.visibility;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wicketstuff.foundation.visibility.FoundationShowByScreenSizeBehavior.ShowByScreenSizeType;

public class ShowByScreenSizeBehaviorTest {

	private WebMarkupContainer container;

	private WicketTester tester;

	@BeforeEach
	public void setup() {
		tester = new WicketTester();
		container = new WebMarkupContainer("id");
		container.add(new FoundationShowByScreenSizeBehavior(Model.of(ShowByScreenSizeType.SHOW_FOR_MEDIUM_UP)));
	}

	@Test
	public void test() {
		tester.startComponentInPage(container);
		TagTester tagTester = tester.getTagByWicketId("id");
		assertEquals("show-for-medium-up", tagTester.getAttribute("class"));
	}

}
