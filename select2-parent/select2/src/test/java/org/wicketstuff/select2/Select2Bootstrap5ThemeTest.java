/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License") +  you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wicketstuff.select2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.html.DecoratingHeaderResponse;
import org.apache.wicket.request.handler.resource.ResourceReferenceRequestHandler;
import org.junit.jupiter.api.Test;

class Select2Bootstrap5ThemeTest extends AbstarctSelect2Test
{
	@Test
	void name()
	{
		assertEquals("bootstrap-5", new Select2Bootstrap5Theme().name());
	}

	@Test
	void rendersThemeStylesheetFromWebjar()
	{
		List<HeaderItem> items = render(new Select2Bootstrap5Theme());
		assertEquals(1, items.size());

		String url = tester.getRequestCycle()
			.urlFor(new ResourceReferenceRequestHandler(
				((CssReferenceHeaderItem)items.get(0)).getReference()))
			.toString();
		tester.executeUrl(url);

		// a webjar file that cannot be found is served as an empty response by the tester
		assertEquals(200, tester.getLastResponse().getStatus(), url);
		assertTrue(tester.getLastResponse().getDocument().contains(".select2-container--bootstrap-5"), url);
	}

	@Test
	void rendersBootstrapWhenRequested()
	{
		assertEquals(2, render(new Select2Bootstrap5Theme(true)).size());
	}

	private List<HeaderItem> render(ISelect2Theme theme)
	{
		List<HeaderItem> items = new ArrayList<>();
		theme.renderHead(null, new DecoratingHeaderResponse(null)
		{
			@Override
			public void render(HeaderItem item)
			{
				items.add(item);
			}
		});
		return items;
	}
}
