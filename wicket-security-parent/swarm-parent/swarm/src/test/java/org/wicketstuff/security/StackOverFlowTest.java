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
package org.wicketstuff.security;

import java.net.MalformedURLException;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.security.authentication.LoginException;
import org.wicketstuff.security.hive.HiveMind;
import org.wicketstuff.security.hive.authentication.PrimaryLoginContext;
import org.wicketstuff.security.hive.config.PolicyFileHiveFactory;
import org.wicketstuff.security.hive.config.SwarmPolicyFileHiveFactory;
import org.wicketstuff.security.pages.MockHomePage;
import org.wicketstuff.security.pages.MockLoginPage;
import org.wicketstuff.security.swarm.SwarmWebApplication;

/**
 * @author marrink
 */
public class StackOverFlowTest extends Assert
{
	private static final class MyWebApplication extends SwarmWebApplication
	{
		@Override
		protected Object getHiveKey()
		{
			// if we were using servlet-api 2.5 we could get the contextpath
			// from the servletcontext
			return "test";
		}

		@Override
		protected void setUpHive()
		{
			PolicyFileHiveFactory factory = new SwarmPolicyFileHiveFactory(getActionFactory());
			try
			{
				factory.addPolicyFile(getServletContext().getResource("WEB-INF/policy.hive"));
				factory.setAlias("SimplePrincipal",
					"org.wicketstuff.security.hive.authorization.SimplePrincipal");
				factory.setAlias("myPackage", "org.wicketstuff.security.pages");
			}
			catch (MalformedURLException e)
			{
				log.error(e.getMessage(), e);
			}
			HiveMind.registerHive(getHiveKey(), factory);
		}

		@Override
		public Class<? extends Page> getHomePage()
		{
			return MockHomePage.class;
		}

		public Class<? extends Page> getLoginPage()
		{
			return MockLoginPage.class;
		}

		@Override
		public Session newSession(Request request, Response response)
		{
			WaspSession session = (WaspSession)super.newSession(request, response);
			try
			{
				session.login(new PrimaryLoginContext());
			}
			catch (LoginException e)
			{
				throw new RuntimeException(e);
			}
			return session;
		}
	}

	private static final Logger log = LoggerFactory.getLogger(StackOverFlowTest.class);

	/**
	 * The swarm application used for the test.
	 */
	protected WebApplication application;

	/**
	 * Handle to the mock environment.
	 */
	protected WicketTester mock;

	@Before
	public void setUp()
	{
		mock = new WicketTester(application = new MyWebApplication(), "src/test/java/" +
			getClass().getPackage().getName().replace('.', '/'));
		mock.setExposeExceptions(false);
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	@After
	public void tearDown()
	{
		mock.getSession().invalidate();
		mock.processRequest();
		mock.destroy();
		mock = null;
		application = null;
		HiveMind.unregisterHive("test");
	}

	/**
	 * Test if automatically logging in in does not trigger a stackoverflow in {@link Session#get()}
	 * .
	 */
	@Test
	public void testStackoverflow()
	{
		try
		{
			mock.startPage(MockHomePage.class);
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
		mock.assertRenderedPage(MockHomePage.class);
	}
}
