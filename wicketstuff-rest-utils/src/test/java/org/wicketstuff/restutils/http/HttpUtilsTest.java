/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wicketstuff.restutils.http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.protocol.http.mock.MockHttpSession;
import org.apache.wicket.protocol.http.mock.MockServletContext;
import org.apache.wicket.request.http.WebRequest;
import org.junit.jupiter.api.Test;

import org.wicketstuff.restutils.test.BufferedMockRequest;

public class HttpUtilsTest
{
	@Test
	public void readRequestString() throws Exception
	{
		String xmlText = "<?xml version='1.0' encoding='UTF-8'?>\n"
			+ "<html>\n"
			+ "	<body>\n"
			+ "		<p >\n"
			+ "		</p>\n"
			+ "	</body>\n"
			+ "</html>";

		MockApplication mockApplication = new MockApplication();
		MockServletContext servletContext = new MockServletContext(mockApplication, "/");
		BufferedMockRequest mockedRequest = new BufferedMockRequest(mockApplication,
			new MockHttpSession(servletContext), servletContext, "POST");

		mockedRequest.setTextAsRequestBody(xmlText);

		WebRequest webRequest = mock(WebRequest.class);
		when(webRequest.getContainerRequest()).thenReturn(mockedRequest);

		String stringFromRequest = HttpUtils.readStringFromRequest(webRequest);
		assertEquals(xmlText, stringFromRequest);
	}
}
