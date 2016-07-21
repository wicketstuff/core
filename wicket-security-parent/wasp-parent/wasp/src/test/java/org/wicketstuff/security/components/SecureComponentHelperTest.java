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
package org.wicketstuff.security.components;

import static org.junit.Assert.assertEquals;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wicketstuff.security.components.markup.html.form.SecureTextField;

/**
 * Tests for {@link SecureComponentHelper}.
 * 
 * @author marrink
 */
public class SecureComponentHelperTest
{
	private WicketTester mock;

	@Before
	public void setUp()
	{
		// a wicket application is required to use the page
		mock = new WicketTester();
	}

	@After
	public void tearDown()
	{
		mock.destroy();
	}

	/**
	 * Test {@link SecureComponentHelper#containerAlias(MarkupContainer)}.
	 */
	@Test
	public void testContainerAlias()
	{
		TestPage page = new TestPage();
		String path = SecureComponentHelper.containerAlias(page);
		assertEquals(TestPage.class.getName(), path);
		// Fomcomponents are markupcontainers too
		path = SecureComponentHelper.containerAlias((MarkupContainer)page.get("txt1"));
		assertEquals(TestPage.class.getName() + SecureComponentHelper.PATH_SEPARATOR +
			SecureTextField.class.getName(), path);
		path = SecureComponentHelper.containerAlias((MarkupContainer)page.get("lvl2"));
		assertEquals(TestPage.class.getName() + SecureComponentHelper.PATH_SEPARATOR +
			TestPanel.class.getName(), path);
		path = SecureComponentHelper.containerAlias((MarkupContainer)page.get("lvl2:txt2"));
		assertEquals(TestPage.class.getName() + SecureComponentHelper.PATH_SEPARATOR +
			TestPanel.class.getName() + SecureComponentHelper.PATH_SEPARATOR +
			SecureTextField.class.getName(), path);
		path = SecureComponentHelper.containerAlias((MarkupContainer)page.get("lvl2:lvl3"));
		assertEquals(TestPage.class.getName() + SecureComponentHelper.PATH_SEPARATOR +
			TestPanel.class.getName() + SecureComponentHelper.PATH_SEPARATOR +
			WebMarkupContainer.class.getName(), path);
		path = SecureComponentHelper.containerAlias((MarkupContainer)page.get("lvl2:lvl3:txt3"));
		assertEquals(TestPage.class.getName() + SecureComponentHelper.PATH_SEPARATOR +
			TestPanel.class.getName() + SecureComponentHelper.PATH_SEPARATOR +
			WebMarkupContainer.class.getName() + SecureComponentHelper.PATH_SEPARATOR +
			SecureTextField.class.getName(), path);
	}

	/**
	 * Test {@link SecureComponentHelper#containerAliasses(org.apache.wicket.Component)} .
	 */
	@Test
	public void testContainerAliasses()
	{
		TestPage page = new TestPage();
		String[] alias = SecureComponentHelper.containerAliasses(page);
		assertEquals(1, alias.length);
		assertEquals(TestPage.class.getName(), alias[0]);
		alias = SecureComponentHelper.containerAliasses(page.get("txt1"));
		assertEquals(3, alias.length);
		assertEquals(TestPage.class.getName() + SecureComponentHelper.PATH_SEPARATOR +
			SecureTextField.class.getName(), alias[0]);
		assertEquals(TestPage.class.getName(), alias[1]);
		assertEquals(SecureTextField.class.getName(), alias[2]);
		alias = SecureComponentHelper.containerAliasses(page.get("lvl2"));
		assertEquals(3, alias.length);
		assertEquals(TestPage.class.getName() + SecureComponentHelper.PATH_SEPARATOR +
			TestPanel.class.getName(), alias[0]);
		assertEquals(TestPage.class.getName(), alias[1]);
		assertEquals(TestPanel.class.getName(), alias[2]);
		alias = SecureComponentHelper.containerAliasses(page.get("lvl2:txt2"));
		assertEquals(5, alias.length);
		assertEquals(TestPage.class.getName() + SecureComponentHelper.PATH_SEPARATOR +
			TestPanel.class.getName() + SecureComponentHelper.PATH_SEPARATOR +
			SecureTextField.class.getName(), alias[0]);
		assertEquals(TestPage.class.getName(), alias[1]);
		assertEquals(TestPanel.class.getName() + SecureComponentHelper.PATH_SEPARATOR +
			SecureTextField.class.getName(), alias[2]);
		assertEquals(TestPanel.class.getName(), alias[3]);
		assertEquals(SecureTextField.class.getName(), alias[4]);
		alias = SecureComponentHelper.containerAliasses(page.get("lvl2:lvl3"));
		assertEquals(5, alias.length);
		assertEquals(TestPage.class.getName() + SecureComponentHelper.PATH_SEPARATOR +
			TestPanel.class.getName() + SecureComponentHelper.PATH_SEPARATOR +
			WebMarkupContainer.class.getName(), alias[0]);
		assertEquals(TestPage.class.getName(), alias[1]);
		assertEquals(TestPanel.class.getName() + SecureComponentHelper.PATH_SEPARATOR +
			WebMarkupContainer.class.getName(), alias[2]);
		assertEquals(TestPanel.class.getName(), alias[3]);
		assertEquals(WebMarkupContainer.class.getName(), alias[4]);
		alias = SecureComponentHelper.containerAliasses(page.get("lvl2:lvl3:txt3"));
		assertEquals(7, alias.length);
		assertEquals(TestPage.class.getName() + SecureComponentHelper.PATH_SEPARATOR +
			TestPanel.class.getName() + SecureComponentHelper.PATH_SEPARATOR +
			WebMarkupContainer.class.getName() + SecureComponentHelper.PATH_SEPARATOR +
			SecureTextField.class.getName(), alias[0]);
		assertEquals(TestPage.class.getName(), alias[1]);
		assertEquals(TestPanel.class.getName() + SecureComponentHelper.PATH_SEPARATOR +
			WebMarkupContainer.class.getName() + SecureComponentHelper.PATH_SEPARATOR +
			SecureTextField.class.getName(), alias[2]);
		assertEquals(TestPanel.class.getName(), alias[3]);
		assertEquals(WebMarkupContainer.class.getName() + SecureComponentHelper.PATH_SEPARATOR +
			SecureTextField.class.getName(), alias[4]);
		assertEquals(WebMarkupContainer.class.getName(), alias[5]);
		assertEquals(SecureTextField.class.getName(), alias[6]);
		// TestPanel:WebMarkupContainer is also a valid alias

	}

	/**
	 * Test {@link SecureComponentHelper#alias(org.apache.wicket.Component)}.
	 */
	@Test
	public void testAlias()
	{
		TestPage page = new TestPage();
		String alias = SecureComponentHelper.alias(page);
		assertEquals(TestPage.class.getName(), alias);
		alias = SecureComponentHelper.alias(page.get("txt1"));
		assertEquals(TestPage.class.getName() + ":txt1", alias);
		alias = SecureComponentHelper.alias(page.get("lvl2"));
		assertEquals(TestPage.class.getName() + ":lvl2", alias);
		alias = SecureComponentHelper.alias(page.get("lvl2:txt2"));
		assertEquals(TestPage.class.getName() + ":lvl2:txt2", alias);
		alias = SecureComponentHelper.alias(page.get("lvl2:lvl3"));
		assertEquals(TestPage.class.getName() + ":lvl2:lvl3", alias);
		alias = SecureComponentHelper.alias(page.get("lvl2:lvl3:txt3"));
		assertEquals(TestPage.class.getName() + ":lvl2:lvl3:txt3", alias);
	}

	private static final class TestPage extends WebPage
	{
		private static final long serialVersionUID = 1L;

		/**
		 * Construct.
		 */
		public TestPage()
		{
			super();
			add(new SecureTextField<String>("txt1"));
			add(new TestPanel("lvl2"));
		}

	}

	private static final class TestPanel extends Panel
	{
		private static final long serialVersionUID = 1L;

		/**
		 * Construct.
		 * 
		 * @param id
		 */
		public TestPanel(String id)
		{
			super(id);
			add(new SecureTextField<String>("txt2"));
			add(new WebMarkupContainer("lvl3").add(new SecureTextField<String>("txt3")));

		}

	}
}
