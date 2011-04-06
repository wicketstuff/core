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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;

import junit.framework.TestCase;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.security.hive.HiveMind;
import org.apache.wicket.security.hive.authentication.SecondaryLoginContext;
import org.apache.wicket.security.hive.authorization.permissions.AllPermissions;
import org.apache.wicket.security.hive.config.PolicyFileHiveFactory;
import org.apache.wicket.security.hive.config.SwarmPolicyFileHiveFactory;
import org.apache.wicket.security.pages.MockHomePage;
import org.apache.wicket.security.pages.MockLoginPage;
import org.apache.wicket.security.pages.PageA;
import org.apache.wicket.security.pages.SecondaryLoginPage;
import org.apache.wicket.security.pages.VerySecurePage;
import org.apache.wicket.security.swarm.SwarmWebApplication;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author marrink
 */
public class GeneralTest extends TestCase
{
	private static final Logger log = LoggerFactory.getLogger(GeneralTest.class);

	/**
	 * The swarm application used for the test.
	 */
	protected WebApplication application;

	/**
	 * Handle to the mock environment.
	 */
	protected WicketTester mock;

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp()
	{
		mock = new WicketTester(application = new SwarmWebApplication()
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
		}, "src/test/java/" + getClass().getPackage().getName().replace('.', '/'));
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
	 * Test multiple logins with auto redirect to the correct login page.
	 */
	public void testMultiLogin()
	{
		mock.startPage(MockHomePage.class);
		mock.assertRenderedPage(MockLoginPage.class);
		FormTester form = mock.newFormTester("form");
		form.setValue("username", "test");
		form.submit();
		mock.assertRenderedPage(MockHomePage.class);
		mock.clickLink("secret", false);
		mock.assertRenderedPage(SecondaryLoginPage.class);
		form = mock.newFormTester("form");
		form.setValue("username", "test");
		form.submit();
		mock.assertRenderedPage(VerySecurePage.class);
		assertTrue(((WaspSession) mock.getWicketSession()).logoff(new SecondaryLoginContext()));
		mock.startPage(mock.getLastRenderedPage());
		mock.assertRenderedPage(application.getApplicationSettings().getAccessDeniedPage());
		// access denied because the page is already constructed
	}

	/**
	 * test permission inheritance.
	 */
	public void testInheritance()
	{
		mock.startPage(MockHomePage.class);
		mock.assertRenderedPage(MockLoginPage.class);
		FormTester form = mock.newFormTester("form");
		form.setValue("username", "test");
		form.submit();
		mock.assertRenderedPage(MockHomePage.class);
		mock.clickLink("link");
		mock.assertRenderedPage(PageA.class);
		mock.assertInvisible("invisible");
		mock.assertVisible("readonly");
		assertTrue(mock.getTagByWicketId("readonly").hasAttribute("disabled"));
		mock.assertVisible("readonly");
	}

	/**
	 * Tests the {@link AllPermissions} permission with the "all" action.
	 */
	public void testAllPermission()
	{
		mock.startPage(MockHomePage.class);
		mock.assertRenderedPage(MockLoginPage.class);
		FormTester form = mock.newFormTester("form");
		form.setValue("username", "all");
		form.submit();
		mock.assertRenderedPage(MockHomePage.class);
		mock.clickLink("link");
		mock.assertRenderedPage(PageA.class);
		mock.assertVisible("invisible");
		mock.assertVisible("readonly");
		assertFalse(mock.getTagByWicketId("readonly").hasAttribute("disabled"));
		mock.assertVisible("readonly");
		mock.startPage(MockHomePage.class);
		mock.assertRenderedPage(MockHomePage.class);
		// normally having all permissions is not enough to get passed the
		// authentication we have setup for the secondary login, but in this
		// case we cheated by using a logincontext that does authenticate us
		mock.clickLink("secret", false);
		mock.assertRenderedPage(VerySecurePage.class);
	}

	/**
	 * Tests the serialization of the wicket session.
	 * 
	 */
	public void testSerialization()
	{
		// setup session
		mock.startPage(MockHomePage.class);
		mock.assertRenderedPage(MockLoginPage.class);
		FormTester form = mock.newFormTester("form");
		form.setValue("username", "test");
		form.submit();
		mock.assertRenderedPage(MockHomePage.class);
		mock.clickLink("secret", false);
		mock.assertRenderedPage(SecondaryLoginPage.class);
		form = mock.newFormTester("form");
		form.setValue("username", "test");
		form.submit();
		mock.assertRenderedPage(VerySecurePage.class);
		Page lastRendered = mock.getLastRenderedPage();

		// prepare serialization
		WaspSession session = (WaspSession) mock.getWicketSession();
		assertNotNull(session);
		assertFalse(session.isTemporary());
		assertFalse(session.isSessionInvalidated());
		try
		{
			ByteArrayOutputStream bytes = new ByteArrayOutputStream(512 * 1024);
			ObjectOutputStream stream = new ObjectOutputStream(bytes);
			stream.writeObject(session);
			WaspSession session2 =
				(WaspSession) new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()))
					.readObject();
			assertNotNull(session2);
			assertNotSame(session, session2);
			// fake restore session from disk
			mock.setupRequestAndResponse();
			Session.set(session2);
			application.getSessionStore().bind(mock.getWicketRequest(), session2);
			mock.processRequestCycle();
		}
		catch (IOException e)
		{
			log.error(e.getMessage(), e);
			fail(e.getMessage());
		}
		catch (ClassNotFoundException e)
		{
			log.error(e.getMessage(), e);
			fail(e.getMessage());
		}
		// attempt logoff
		WaspSession waspSession = ((WaspSession) mock.getWicketSession());
		assertNotSame(session, waspSession);
		// instead of simulating a different jvm we can make sure the hashcode
		// always stays the same
		SecondaryLoginContext logoff = new SecondaryLoginContext();
		assertEquals(22889663, logoff.hashCode());
		assertTrue(waspSession.logoff(logoff));
		mock.startPage(lastRendered);
		mock.assertRenderedPage(application.getApplicationSettings().getAccessDeniedPage());
	}
}
