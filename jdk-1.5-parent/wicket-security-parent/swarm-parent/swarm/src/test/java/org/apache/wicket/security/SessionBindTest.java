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
package org.apache.wicket.security;

import java.net.MalformedURLException;

import junit.framework.TestCase;

import org.apache.wicket.Page;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.apache.wicket.request.target.component.BookmarkablePageRequestTarget;
import org.apache.wicket.security.authentication.LoginException;
import org.apache.wicket.security.hive.HiveMind;
import org.apache.wicket.security.hive.authentication.PrimaryLoginContext;
import org.apache.wicket.security.hive.config.PolicyFileHiveFactory;
import org.apache.wicket.security.hive.config.SwarmPolicyFileHiveFactory;
import org.apache.wicket.security.pages.MockHomePage;
import org.apache.wicket.security.pages.MockLoginPage;
import org.apache.wicket.security.strategies.WaspAuthorizationStrategy;
import org.apache.wicket.security.swarm.SwarmWebApplication;
import org.apache.wicket.util.tester.SwarmFormTester;
import org.apache.wicket.util.tester.SwarmWicketTester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author marrink
 */
public class SessionBindTest extends TestCase
{
	private final class MyWebApplication extends SwarmWebApplication
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
					"org.apache.wicket.security.hive.authorization.SimplePrincipal");
				factory.setAlias("myPackage", "org.apache.wicket.security.pages");
			}
			catch (MalformedURLException e)
			{
				log.error(e.getMessage(), e);
			}
			HiveMind.registerHive(getHiveKey(), factory);
		}

		@Override
		public Class< ? extends Page> getHomePage()
		{
			return MockHomePage.class;
		}

		public Class< ? extends Page> getLoginPage()
		{
			return MockLoginPage.class;
		}
	}

	private static final Logger log = LoggerFactory.getLogger(SessionBindTest.class);

	/**
	 * The swarm application used for the test.
	 */
	protected WebApplication application;

	/**
	 * Handle to the mock environment.
	 */
	protected SwarmWicketTester mock;

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp()
	{
		mock =
			new SwarmWicketTester(application = new MyWebApplication(), "src/test/java/"
				+ getClass().getPackage().getName().replace('.', '/'));

	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown()
	{
		mock.setupRequestAndResponse();
		mock.getWicketSession().invalidate();
		mock.processRequestCycle();
		mock.destroy();
		mock = null;
		application = null;
		HiveMind.unregisterHive("test");
	}

	/**
	 * Test if the session is correctly bound if we login through the session.
	 */
	public void testSessionLogin()
	{
		mock.startPage(MockLoginPage.class);
		mock.setupRequestAndResponse();
		assertTrue(mock.getWicketSession().isTemporary());
		mock.processRequestCycle(MockLoginPage.class);
		// loginpage, else the homepage will be used which will trigger a bind
		// because a throw restartResponseAtInterceptPageexception will trigger
		// a session.bind
		SwarmFormTester form = mock.newFormTester("form");
		form.setValue("username", "test");
		form.submit();
		mock.assertRenderedPage(MockHomePage.class);
		mock.setupRequestAndResponse();
		assertFalse(Session.get().isTemporary());
		mock.processRequestCycle(MockLoginPage.class);
	}

	/**
	 * Test if the session is correctly bound even if we do not use the session to login.
	 */
	public void testStrategyLogin()
	{
		mock.startPage(MockLoginPage.class);
		mock.setupRequestAndResponse();
		assertTrue(mock.getWicketSession().isTemporary());
		mock.processRequestCycle(MockLoginPage.class);
		// loginpage, else the homepage will be used which will trigger a bind
		// because a throw restartResponseAtInterceptPageexception will trigger
		// a session.bind
		mock.setupRequestAndResponse();
		try
		{
			((WaspAuthorizationStrategy) mock.getWicketSession().getAuthorizationStrategy())
				.login(new PrimaryLoginContext());
		}
		catch (LoginException e)
		{
			fail(e.getMessage());
		}
		// hack to prevent mock from throwing away the requestcycle with our
		// subject
		WebRequestCycle cycle = ((WebRequestCycle) RequestCycle.get());
		assertNotNull(cycle);
		try
		{
			cycle.request(new BookmarkablePageRequestTarget(MockLoginPage.class, null));
		}
		finally
		{
			cycle.getResponse().close();
		}
		mock.postProcessRequestCycle(cycle);
		// mock.processRequestCycle(MockLoginPage.class);
		mock.setupRequestAndResponse();
		assertFalse(Session.get().isTemporary());
		mock.processRequestCycle(MockLoginPage.class);
	}
}
