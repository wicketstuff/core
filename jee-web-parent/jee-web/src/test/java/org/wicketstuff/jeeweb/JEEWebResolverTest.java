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

import java.io.File;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;

import org.apache.wicket.protocol.http.mock.MockServletContext;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class JEEWebResolverTest
{

	private WicketTester wicketTester;

	@After
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
		Assert.assertTrue(lastResponse.contains("INCLUDE OF RESOURCE: /TestServlet"));
		Assert.assertTrue(lastResponse.contains("INCLUDE OF RESOURCE: /TestJSP.jsp"));
	}

	@Test(expected = org.apache.wicket.WicketRuntimeException.class)
	public void testJSPRequestIsFailingIfNotExist() throws Exception
	{
		TestApplication testApplication = new TestApplication();
		MockServletContext mockServletContext = new MockServletContext(testApplication, new File(
			"src/main/webapp").getCanonicalPath());
		wicketTester = new WicketTester(testApplication, mockServletContext);
		wicketTester.startPage(TestJSPFailPage.class);
	}

	@Test(expected = org.apache.wicket.WicketRuntimeException.class)
	public void testServletRequestIsFailingIfNoServletIsAvailable() throws Exception
	{
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
	}
}
