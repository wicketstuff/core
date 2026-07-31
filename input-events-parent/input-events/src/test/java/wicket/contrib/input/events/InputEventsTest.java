/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wicket.contrib.input.events;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Renders a page using all three flavours of {@link InputBehavior}: bound to an explicit event, and
 * auto-hooked onto a button and onto a link. The auto-hook flavours write their script inline from
 * {@link InputBehavior#afterRender(org.apache.wicket.Component)} rather than into the header, so
 * they are not covered by the other tests in this package.
 */
public class InputEventsTest
{
	private WicketTester tester;

	@BeforeEach
	public void setUp()
	{
		tester = new WicketTester();
	}

	@AfterEach
	public void tearDown()
	{
		tester.destroy();
	}

	@Test
	public void testCorrectComponentBinding()
	{
		tester.startPage(Page.class);
		tester.assertRenderedPage(Page.class);

		String response = tester.getLastResponseAsString();

		// The explicitly bound button invokes the event method on the component.
		assertRegistered(response, "b", targetOf("button") + ".click()");
		// The auto-hooked button targets the component it is attached to. Note that the event
		// method is left as an unsubstituted ${event} here, because onComponentTag() only detects
		// an event type from an inline handler attribute and AjaxEventBehavior renders none.
		assertRegistered(response, "a", targetOf("button2"));
		// The auto-hooked link has no event handler to hook onto, so it follows the href instead.
		assertRegistered(response, "d", targetOf("link") + ".href");
	}

	/**
	 * The <code>getElementById</code> lookup the generated script uses for the given component -
	 * built from the component's actual markup id rather than a hardcoded one.
	 */
	private String targetOf(String componentPath)
	{
		String markupId = tester.getComponentFromLastRenderedPage(componentPath).getMarkupId();
		return "getElementById('" + markupId + "')";
	}

	/**
	 * Asserts that a shortcut for the given key was registered, that any listener from a previous
	 * render is dropped first (see <a href="https://github.com/wicketstuff/core/issues/711">issue
	 * #711</a>), and that its callback contains the given snippet - i.e. that the whole template was
	 * rendered, not just its opening lines.
	 */
	private static void assertRegistered(String response, String key, String callbackSnippet)
	{
		int add = response.indexOf("shortcut.add(\"" + key + "\"");
		assertTrue(add != -1,
			"No shortcut registered for key '" + key + "' in:\n" + response);

		int remove = response.indexOf("shortcut.remove(\"" + key + "\")");
		assertTrue(remove != -1 && remove < add,
			"Registration for key '" + key + "' must drop the previous listener before adding a "
				+ "new one, in:\n" + response);

		int end = response.indexOf("});", add);
		assertTrue(end != -1,
			"Registration for key '" + key + "' is not terminated in:\n" + response);
		assertTrue(response.substring(add, end).contains(callbackSnippet),
			"Registration for key '" + key + "' must contain '" + callbackSnippet + "' in:\n"
				+ response);
	}
}
