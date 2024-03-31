/**
 *
 Copyright 2012 Vineet Semwal

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package org.wicketstuff.navigator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.IAjaxCallListener;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.wicketstuff.RepeaterUtil;
import org.wicketstuff.WicketTest;

/**
 * @author Vineet Semwal
 *
 */
public class AjaxPageScrollEventBehaviorTest extends TesterBase {
	@WicketTest
	public void getPrecondition() {
		WebMarkupContainer parent = new WebMarkupContainer("parent");
		AjaxPageScrollEventBehavior.PageScrollListener listener = new AjaxPageScrollEventBehavior.PageScrollListener();
		String actual = listener.getPrecondition(parent).toString();
		String expected = "return " + RepeaterUtil.get().isPageScrollBarAtBottom();
		assertEquals(actual, expected);
	}

	@WicketTest
	public void updateAjaxAttributes() {
		AjaxPageScrollEventBehavior behavior = new AjaxPageScrollEventBehavior() {
			@Override
			protected void onScroll(AjaxRequestTarget target) {
			}
		};
		AjaxRequestAttributes attributes = new AjaxRequestAttributes();
		behavior.updateAjaxAttributes(attributes);
		boolean isAdded = false;
		for (IAjaxCallListener listener : attributes.getAjaxCallListeners()) {
			if (listener instanceof AjaxPageScrollEventBehavior.PageScrollListener) {
				isAdded = true;
			}
		}
		assertTrue(isAdded);
	}
}
