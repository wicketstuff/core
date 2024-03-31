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
package org.wicketstuff.jeeweb;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServlet;

import java.io.File;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.protocol.http.mock.MockServletContext;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class JEEWebResolverTest
{

	private WicketTester wicketTester;

	@AfterEach
	public void tearDown()
	{
		wicketTester.destroy();
	}


	@Test
	public void testServletsAndJSPsAreResolvedRight() throws Exception
	{
		TestApplication testApplication = new TestApplication();
		MockServletContext mockServletContext = new MockServletContext(testApplication, new File(
			"src/test/webapp").getCanonicalPath());

		mockServletContext.addServlet("/TestServlet", new HttpServlet()
		{
		});

		wicketTester = new WicketTester(testApplication, mockServletContext);
		wicketTester.startPage(TestServletAndJSPPage.class);
		String lastResponse = wicketTester.getLastResponseAsString();
		assertTrue(lastResponse.contains("INCLUDE OF RESOURCE: /TestServlet"));
		assertTrue(lastResponse.contains("INCLUDE OF RESOURCE: /TestJSP.jsp"));
	}

	@Test
	public void testJSPRequestIsFailingIfNotExist() throws Exception
	{
		assertThrows(WicketRuntimeException.class, () -> {
			TestApplication testApplication = new TestApplication();
			MockServletContext mockServletContext = new MockServletContext(testApplication, new File(
				"src/main/webapp").getCanonicalPath());
			wicketTester = new WicketTester(testApplication, mockServletContext);
			wicketTester.startPage(TestJSPFailPage.class);
		});
	}

	@Test
	public void testServletRequestIsFailingIfNoServletIsAvailable() throws Exception
	{
		assertThrows(WicketRuntimeException.class, () -> {
			TestApplication testApplication = new TestApplication();
			MockServletContext mockServletContext = new MockServletContext(testApplication, new File(
				"src/main/webapp").getCanonicalPath())
			{

				// The dispatcher is returned as null if the servlet is not found -
				// so we have to do this here.
				@Override
				public RequestDispatcher getRequestDispatcher(String name)
				{
					return null;
				}
			};
			wicketTester = new WicketTester(testApplication, mockServletContext);
			wicketTester.startPage(TestServletFailPage.class);
		});
	}
}
