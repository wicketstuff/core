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

public class InputBehaviorRenderTest
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

	/**
	 * Re-rendering the behavior must emit a script referring to the component's current markup id -
	 * not to the id captured during the first render. This guards against the template being
	 * interpolated in place, which would freeze the values of the first render.
	 */
	@Test
	public void thatRerenderUsesCurrentMarkupId()
	{
		tester.startPage(Page.class);
		tester.getComponentFromLastRenderedPage("button").setMarkupId("changedId");

		// Re-render the page - the behavior renders its script again.
		tester.startPage(tester.getLastRenderedPage());

		String script = tester.getLastResponseAsString();

		assertTrue(script.contains("getElementById('changedId')"),
			"Re-rendered script must target the current markup id, but was:\n" + script);
	}
}
