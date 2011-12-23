/**
 * Copyright (C) 2009 Uwe Schaefer <uwe@codesmell.de>
 *
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
package org.wicketstuff.jslibraries;

import junit.framework.TestCase;

import org.apache.wicket.Application;
import org.apache.wicket.markup.head.internal.HeaderResponse;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.request.Response;
import org.apache.wicket.util.tester.WicketTester;

public class JSLibTest extends TestCase
{

	public void testSettings() throws Exception
	{
		new WicketTester();

		final StringBuffer sb = new StringBuffer(128);

		IHeaderContributor hc = JSLib.getHeaderContribution(
			VersionDescriptor.exactVersion(Library.JQUERY, 1, 3, 1), CDN.GOOGLE);
		HeaderResponse mockResponse = new HeaderResponse()
		{

			@Override
			protected Response getRealResponse()
			{

				return new Response()
				{

					@Override
					public void write(CharSequence arg0)
					{
						sb.append(arg0);
					}

					@Override
					public Object getContainerResponse()
					{
						throw new UnsupportedOperationException();
					}

					@Override
					public String encodeURL(CharSequence url)
					{
						throw new UnsupportedOperationException();
					}

					@Override
					public void write(byte[] array)
					{
						throw new UnsupportedOperationException();
					}

					@Override
					public void write(byte[] array, int offset, int length)
					{
						throw new UnsupportedOperationException();
					}
				};
			}
		};
		hc.renderHead(mockResponse);
		String scriptTag = sb.toString();
		assertTrue(scriptTag.contains("google")); // must be in as selected
		assertFalse(scriptTag.contains("resources/org.wicketstuff.jsl"));
		sb.setLength(0);

		// now set applicationwide setting to local only:
		JSLib.setOverrideProviders(Application.get(), LocalProvider.DEFAULT);

		// and retest
		hc = JSLib.getHeaderContribution(VersionDescriptor.exactVersion(Library.JQUERY, 1, 3, 1),
			CDN.GOOGLE);

		hc.renderHead(mockResponse);
		scriptTag = sb.toString();
		assertTrue(scriptTag.contains("wicket/resource/org.wicketstuff.jslibraries.JSReference/js/jquery-1.3.1.js"));
	}

}
