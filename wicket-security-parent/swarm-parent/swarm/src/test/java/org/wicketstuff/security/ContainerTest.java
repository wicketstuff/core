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
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.security.hive.HiveMind;
import org.wicketstuff.security.hive.config.PolicyFileHiveFactory;
import org.wicketstuff.security.hive.config.SwarmPolicyFileHiveFactory;
import org.wicketstuff.security.pages.ContainerHomePage;
import org.wicketstuff.security.pages.ContainerPage2;
import org.wicketstuff.security.pages.MockLoginPage;
import org.wicketstuff.security.swarm.SwarmWebApplication;

/**
 * @author marrink
 */
public class ContainerTest extends Assert
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

	@Before
	public void setUp()
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
				return ContainerHomePage.class;
			}

			public Class<? extends Page> getLoginPage()
			{
				return MockLoginPage.class;
			}
		}, "src/test/java/" + getClass().getPackage().getName().replace('.', '/'));
		mock.setExposeExceptions(false);
	}

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
	 * test inheriting permissions from the page.
	 */
	@Test
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
		assertTrue(mock.getTagByWicketId("lvl1")
			.getChild("name", "lvl1:txt1")
			.hasAttribute("disabled"));
		assertTrue(mock.getTagByWicketId("lvl1")
			.getChild("name", "lvl1:lvl2:txt2")
			.hasAttribute("disabled"));
	}

	/**
	 * test inheriting permissions from the 1st container.
	 */
	@Test
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
	@Test
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
	@Test
	public void testContainerPermissionInheritance3()
	{
		mock.startPage(ContainerPage2.class);
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
	@Test
	public void testContainerPermissionInheritance4()
	{
		doContainerPermissionInheritance("container5");
	}

	/**
	 * 
	 */
	private void doContainerPermissionInheritance(String username)
	{
		mock.startPage(ContainerPage2.class);
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
	@Test
	public void testContainerPermissionInheritance5()
	{
		doContainerPermissionInheritance("container6");
	}

	/**
	 * test inheriting permissions with different permissions.
	 */
	@Test
	public void testContainerPermissionInheritance6()
	{
		doContainerPermissionInheritance("container7");
	}
}
