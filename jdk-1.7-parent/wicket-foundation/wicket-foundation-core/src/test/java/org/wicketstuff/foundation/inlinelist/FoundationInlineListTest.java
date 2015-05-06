package org.wicketstuff.foundation.inlinelist;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

public class FoundationInlineListTest {

	@Test
	public void testBasic() {
		WicketTester tester = new WicketTester();
		List<String> titles = Arrays.asList("Foo", "Bar", "Baz");
		FoundationInlineList inlineList = new FoundationInlineList("id", titles) {

			private static final long serialVersionUID = 1L;

			@Override
			public AbstractLink createLink(String id, int idx) {
				return new AjaxLink<Void>(id) {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
					}
					
				};
			}
			
		};
		tester.startComponentInPage(inlineList);
		tester.dumpPage();
		List<TagTester> textTags = tester.getTagsByWicketId("text");
		assertEquals(3, textTags.size());
		assertEquals("Foo", textTags.get(0).getValue());
		assertEquals("Bar", textTags.get(1).getValue());
		assertEquals("Baz", textTags.get(2).getValue());
	}
}
