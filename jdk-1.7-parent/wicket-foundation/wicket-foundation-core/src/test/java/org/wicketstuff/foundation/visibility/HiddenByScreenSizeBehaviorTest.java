package org.wicketstuff.foundation.visibility;

import static org.junit.Assert.assertEquals;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import org.wicketstuff.foundation.visibility.FoundationHiddenByScreenSizeBehavior.HiddenByScreenSizeType;

public class HiddenByScreenSizeBehaviorTest {

	private WebMarkupContainer container;
	
	private WicketTester tester;
	
	@Before
	public void setup() {
		tester = new WicketTester();
		container = new WebMarkupContainer("id");
		container.add(new FoundationHiddenByScreenSizeBehavior(Model.of(HiddenByScreenSizeType.HIDDEN_FOR_MEDIUM_UP)));
	}
	
	@Test
	public void test() {
		tester.startComponentInPage(container);
		TagTester tagTester = tester.getTagByWicketId("id");
		assertEquals("hidden-for-medium-up", tagTester.getAttribute("class"));
	}

}
