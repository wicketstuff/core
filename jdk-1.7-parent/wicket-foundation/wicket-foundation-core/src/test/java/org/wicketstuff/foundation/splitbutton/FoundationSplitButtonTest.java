package org.wicketstuff.foundation.splitbutton;

import java.util.Arrays;

import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

public class FoundationSplitButtonTest {

	@Test
	public void testBasic() {
		WicketTester tester = new WicketTester();
		FoundationSplitButton splitButton = new FoundationSplitButton("id", "button", Arrays.asList("link1", "link2", "link3")) {

			private static final long serialVersionUID = 1L;

			@Override
			public AbstractLink createButton(String id) {
				return new Link<Void>(id) {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
					}
				};
			}

			@Override
			public AbstractLink createDropdownLink(String id, int idx) {
				return new Link<Void>(id) {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
					}
				};
			}
			
		};
		tester.startComponentInPage(splitButton);
		tester.dumpPage();
	}
}
