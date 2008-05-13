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

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.apache.wicket.request.target.component.BookmarkablePageRequestTarget;
import org.apache.wicket.security.hive.HiveMind;
import org.apache.wicket.security.hive.config.PolicyFileHiveFactory;
import org.apache.wicket.security.hive.config.SwarmPolicyFileHiveFactory;
import org.apache.wicket.security.pages.ContainerHomePage;
import org.apache.wicket.security.pages.ContainerPage2;
import org.apache.wicket.security.pages.MockLoginPage;
import org.apache.wicket.security.swarm.SwarmWebApplication;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author marrink
 */
public class ContainerTest extends TestCase
{
	private static final Logger log = LoggerFactory.getLogger(ContainerTest.class);

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
	protected void setUp() throws Exception
	{
		mock = new WicketTester(application = new SwarmWebApplication()
		{

			protected Object getHiveKey()
			{
				// if we were using servlet-api 2.5 we could get the contextpath
				// from the servletcontext
				return "test";
			}

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

			public Class getHomePage()
			{
				return ContainerHomePage.class;
			}

			public Class getLoginPage()
			{
				return MockLoginPage.class;
			}
		}, "src/test/java/" + getClass().getPackage().getName().replace('.', '/'));
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception
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
	 * test inheriting permissions from the page.
	 */
	public void testPagePermissionInheritance()
	{
		mock.startPage(ContainerHomePage.class);
		mock.assertRenderedPage(MockLoginPage.class);
		FormTester form = mock.newFormTester("form");
		form.setValue("username", "container1");
		form.submit();
		mock.assertRenderedPage(ContainerHomePage.class);
		mock.assertVisible("txt1");
		mock.assertVisible("lvl1");
		mock.assertVisible("lvl1:txt1");
		mock.assertVisible("lvl1:lvl2");
		mock.assertVisible("lvl1:lvl2:txt2");
		// The following only works from wicket 1.3.1 and above
		TagTester tagTester = mock.getTagByWicketId("txt1");
		if (tagTester == null)
			return;
		assertTrue(tagTester.hasAttribute("disabled"));
		assertTrue(mock.getTagByWicketId("lvl1").getChild("name", "lvl1:txt1").hasAttribute(
				"disabled"));
		assertTrue(mock.getTagByWicketId("lvl1").getChild("name", "lvl1:lvl2:txt2").hasAttribute(
				"disabled"));
	}

	/**
	 * test inheriting permissions from the 1st container.
	 */
	public void testContainerPermissionInheritance1()
	{
		mock.startPage(ContainerHomePage.class);
		mock.assertRenderedPage(MockLoginPage.class);
		FormTester form = mock.newFormTester("form");
		form.setValue("username", "container2");
		form.submit();
		mock.assertRenderedPage(ContainerHomePage.class);
		mock.assertInvisible("txt1");
		mock.assertVisible("lvl1");
		mock.assertVisible("lvl1:txt1");
		mock.assertVisible("lvl1:lvl2");
		mock.assertVisible("lvl1:lvl2:txt2");
		// The following only works from wicket 1.3.1 and above
		TagTester tagTester = mock.getTagByWicketId("lvl1");
		if (tagTester == null)
			return;
		assertTrue(tagTester.getChild("name", "lvl1:txt1").hasAttribute("disabled"));
		assertTrue(tagTester.getChild("name", "lvl1:lvl2:txt2").hasAttribute("disabled"));
	}

	/**
	 * test inheriting permissions from the 2nd container.
	 */
	public void testContainerPermissionInheritance2()
	{
		mock.startPage(ContainerHomePage.class);
		mock.assertRenderedPage(MockLoginPage.class);
		FormTester form = mock.newFormTester("form");
		form.setValue("username", "container3");
		form.submit();
		mock.assertRenderedPage(ContainerHomePage.class);
		mock.assertInvisible("txt1");
		mock.assertVisible("lvl1");
		mock.assertInvisible("lvl1:txt1");
		mock.assertVisible("lvl1:lvl2");
		mock.assertVisible("lvl1:lvl2:txt2");
		// The following only works from wicket 1.3.1 and above
		TagTester tagTester = mock.getTagByWicketId("lvl1");
		if (tagTester == null)
			return;
		assertTrue(tagTester.getChild("name", "lvl1:lvl2:txt2").hasAttribute("disabled"));
	}

	/**
	 * test inheriting permissions with different permissions.
	 */
	public void testContainerPermissionInheritance3()
	{
		// continueto originaldestination does not work if there is no url
		// available, so we need to fake one here(testing only hack), fixed in
		// wicket 1.3.4
		mock.setupRequestAndResponse();
		WebRequestCycle cycle = mock.createRequestCycle();
		String url1 = cycle.urlFor(new BookmarkablePageRequestTarget(ContainerPage2.class, null))
				.toString();
		mock.getServletRequest().setURL("/ContainerTest$1/ContainerTest$1/" + url1);
		mock.processRequestCycle();
		mock.assertRenderedPage(MockLoginPage.class);
		FormTester form = mock.newFormTester("form");
		form.setValue("username", "container4");
		form.submit();
		mock.assertRenderedPage(ContainerPage2.class);
		mock.assertInvisible("secure");
		mock.assertVisible("label");
	}

	/**
	 * test inheriting permissions with different permissions.
	 */
	public void testContainerPermissionInheritance4()
	{
		doContainerPermissionInheritance("container5");
	}

	/**
	 * 
	 */
	private void doContainerPermissionInheritance(String username)
	{
		// continueto originaldestination does not work if there is no url
		// available, so we need to fake one here(testing only hack), fixed in
		// wicket 1.3.4
		mock.setupRequestAndResponse();
		WebRequestCycle cycle = mock.createRequestCycle();
		String url1 = cycle.urlFor(new BookmarkablePageRequestTarget(ContainerPage2.class, null))
				.toString();
		mock.getServletRequest().setURL("/ContainerTest$1/ContainerTest$1/" + url1);
		mock.processRequestCycle();
		mock.assertRenderedPage(MockLoginPage.class);
		FormTester form = mock.newFormTester("form");
		form.setValue("username", username);
		form.submit();
		mock.assertRenderedPage(ContainerPage2.class);
		mock.assertVisible("secure");
		mock.assertVisible("label");
	}

	/**
	 * test inheriting permissions with different permissions.
	 */
	public void testContainerPermissionInheritance5()
	{
		doContainerPermissionInheritance("container6");
	}

	/**
	 * test inheriting permissions with different permissions.
	 */
	public void testContainerPermissionInheritance6()
	{
		doContainerPermissionInheritance("container7");
	}
}
