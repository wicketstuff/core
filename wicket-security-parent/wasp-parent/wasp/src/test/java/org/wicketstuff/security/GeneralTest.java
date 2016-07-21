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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.UnauthorizedActionException;
import org.apache.wicket.authorization.UnauthorizedInstantiationException;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.TagTester;
import org.junit.Test;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.checks.ComponentSecurityCheck;
import org.wicketstuff.security.components.ISecureComponent;
import org.wicketstuff.security.components.ISecureContainer;
import org.wicketstuff.security.components.ISecurePage;
import org.wicketstuff.security.components.SecureComponentHelper;
import org.wicketstuff.security.components.markup.html.form.SecureTextField;
import org.wicketstuff.security.models.ISecureModel;
import org.wicketstuff.security.pages.container.MySecurePanel;
import org.wicketstuff.security.pages.secure.HomePage;
import org.wicketstuff.security.pages.secure.PageA;
import org.wicketstuff.security.pages.secure.PageB;
import org.wicketstuff.security.pages.secure.PageC;
import org.wicketstuff.security.pages.secure.PageC2;
import org.wicketstuff.security.pages.secure.PageD;
import org.wicketstuff.security.strategies.ClassAuthorizationStrategy;
import org.wicketstuff.security.strategies.SecurityException;

/**
 * Some general tests.
 * 
 * @author marrink
 */
public class GeneralTest extends WaspAbstractTestBase
{

	/**
	 * Test reading and writing from and to a secure textfield.
	 */
	@Test
	public void testReadWrite()
	{
		doLogin();
		Map<String, WaspAction> authorized = new HashMap<String, WaspAction>();
		authorized.put(SecureComponentHelper.alias(PageA.class), application.getActionFactory()
			.getAction("access render enable"));
		login(authorized);
		mock.processRequest();
		mock.clickLink("link", false);
		mock.assertRenderedPage(PageA.class);
		mock.assertInvisible("secure");
		authorized.put(SecureComponentHelper.alias(mock.getLastRenderedPage().get("secure")),
			application.getActionFactory().getAction("access render"));
		login(authorized);
		mock.startPage(PageA.class);
		mock.assertVisible("secure");
		TagTester tag = mock.getTagByWicketId("secure");
		assertTrue(tag.hasAttribute("disabled"));
		try
		{
			mock.getComponentFromLastRenderedPage("secure").setDefaultModelObject(
				"writing in textfield");
			fail("should not be able to write in textfield");
		}
		catch (UnauthorizedActionException e)
		{
		}
		authorized.put(
			SecureComponentHelper.alias(mock.getComponentFromLastRenderedPage("secure")),
			application.getActionFactory().getAction("access render enable"));
		login(authorized);
		mock.startPage(PageA.class);
		mock.assertVisible("secure");
		tag = mock.getTagByWicketId("secure");
		assertFalse(tag.hasAttribute("disabled"));
		String writings = "now we are getting somewhere";
		mock.getComponentFromLastRenderedPage("secure").setDefaultModelObject(writings);
		assertEquals(writings, mock.getComponentFromLastRenderedPage("secure")
			.getDefaultModelObject());
		mock.startPage(mock.getLastRenderedPage());
		mock.assertRenderedPage(PageA.class);
		tag = mock.getTagByWicketId("secure");
		assertTrue(tag.getAttributeIs("value", writings));
	}

	/**
	 * Test behaviour of different secure classes in the {@link ClassAuthorizationStrategy}.
	 */
	@Test
	public void testInstantiation()
	{
		doLogin();
		assertEquals(ISecureComponent.class, getSecureClass());
		new PageB();
		// even though we added the same securitycheck to a regular textfield as
		// a
		// secureTextfield has there is no instantiation check because it is not
		// an
		// IsecureComponent
		Map<String, WaspAction> authorized = new HashMap<String, WaspAction>();
		authorized.put(SecureComponentHelper.alias(PageA.class), application.getActionFactory()
			.getAction("access"));
		login(authorized);
		new PageA(); // still allowed here
		authorized.clear();
		authorized.put(SecureComponentHelper.alias(SecureTextField.class),
			application.getActionFactory().getAction("access"));
		logoff(authorized);
		try
		{
			new PageA();
			fail("somehow page was instantiated");
		}
		catch (UnauthorizedInstantiationException e)
		{
			// because we are not allowed to instantiate a SecureTextField
			// anymore
		}
		mock.processRequest();
		try
		{
			tearDown();
			setSecureClass(ISecurePage.class);
			setUp();
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			fail(e.getMessage());
		}
		doLogin();
		// now we only check pages so all components will be created, this does
		// not affect
		// the render check though
		logoff(authorized);
		authorized.clear();
		authorized.put(SecureComponentHelper.alias(PageA.class), application.getActionFactory()
			.getAction("access"));
		// need to enable page a again
		login(authorized);
		new PageA(); // only pages are checked so now securetextfield is
		// allowed.
		mock.processRequest();
	}

	/**
	 * More instantiation testing.
	 */
	@Test
	public void testAdvancedInstantiationChecks()
	{
		doLogin();
		mock.startPage(PageC.class);
		// even if we are logged in and have access rights it will redirect us
		// to the access denied page because it need render rights
		mock.assertRenderedPage(application.getApplicationSettings().getAccessDeniedPage());
		Map<String, WaspAction> authorized = new HashMap<String, WaspAction>();
		authorized.put(SecureComponentHelper.alias(PageC.class), application.getActionFactory()
			.getAction("access render"));
		login(authorized);
		mock.startPage(PageC.class);
		mock.assertRenderedPage(PageC.class);
		// PageC2 will render because it has all the required rights.
		mock.startPage(PageC2.class);
		mock.assertRenderedPage(PageC2.class);
		mock.startPage(HomePage.class);
		logoff(authorized);
		// but if we remove the render rights for PageC, PageC2 misses those
		// rights and won't be able to be instantiated anymore
		mock.startPage(PageC2.class);
		mock.assertRenderedPage(application.getApplicationSettings().getAccessDeniedPage());
	}

	/**
	 * Test methods of {@link SecureComponentHelper}.
	 */
	@Test
	public void testSecureComponentHelper()
	{
		TextField<String> field = new TextField<String>("id");
		assertNull(SecureComponentHelper.getSecurityCheck(field));
		assertTrue(SecureComponentHelper.isActionAuthorized(field, "whatever"));
		ComponentSecurityCheck check = new ComponentSecurityCheck(field);
		// this did not register the check with the component
		assertNull(SecureComponentHelper.getSecurityCheck(field));
		SecureComponentHelper.setSecurityCheck(field, check);
		assertEquals(check, SecureComponentHelper.getSecurityCheck(field));
		try
		{
			// can not check this because component is not attached to a page
			// see SecureComponentHelper.alias(Component)
			SecureComponentHelper.isActionAuthorized(field, "whatever");
			fail();
		}
		catch (RestartResponseAtInterceptPageException e)
		{
		}
		catch (SecurityException e)
		{
		}
		assertFalse(SecureComponentHelper.hasSecureModel(field));
		field.setModel(new ISecureModel<String>()
		{
			private static final long serialVersionUID = 1L;

			public void detach()
			{
				// noop
			}

			public void setObject(String object)
			{
				// noop
			}

			public String getObject()
			{
				return "test";
			}

			public boolean isAuthorized(Component component, WaspAction action)
			{
				return false;
			}

			public boolean isAuthenticated(Component component)
			{
				return false;
			}

		});
		assertTrue(SecureComponentHelper.hasSecureModel(field));
	}

	/**
	 * Test mixing security check with secure model.
	 */
	@Test
	public void testComponentSecurityCheckAndISecureModel()
	{
		doLogin();
		mock.startPage(PageD.class);
		mock.assertRenderedPage(PageD.class);
		mock.assertInvisible("componentcheck"); // no render rights
		mock.assertVisible("modelcheck");
		mock.assertInvisible("both"); // component says no
		mock.assertInvisible("bothcheck");// component says no, model says yes
		try
		{
			mock.getComponentFromLastRenderedPage("modelcheck").setDefaultModelObject("foobar");
			fail("should not be able to write to model");
		}
		catch (UnauthorizedActionException e)
		{
		}
		Map<String, WaspAction> authorized = new HashMap<String, WaspAction>();
		authorized.put(SecureComponentHelper.alias(SecureTextField.class),
			application.getActionFactory().getAction("access render"));
		authorized.put("model:modelcheck",
			application.getActionFactory().getAction("access render enable"));
		login(authorized);
		mock.startPage(PageD.class);
		mock.assertRenderedPage(PageD.class);
		mock.assertVisible("componentcheck");
		mock.assertVisible("modelcheck");
		mock.assertVisible("both");
		mock.assertVisible("bothcheck");
		mock.getComponentFromLastRenderedPage("modelcheck").setDefaultModelObject("foobar");
		assertEquals("foobar", mock.getComponentFromLastRenderedPage("modelcheck")
			.getDefaultModelObject());
		((ISecureComponent)mock.getComponentFromLastRenderedPage("both")).setSecurityCheck(null);
		authorized.clear();
		authorized.put("model:modelcheck", application.getActionFactory()
			.getAction("access render"));
		authorized.put("model:bothcheck", application.getActionFactory().getAction("access"));
		login(authorized);
		mock.startPage(mock.getLastRenderedPage());
		mock.assertRenderedPage(PageD.class);
		TagTester tag = mock.getTagByWicketId("modelcheck");
		assertTrue(tag.getAttributeIs("value", "foobar"));
		mock.assertVisible("componentcheck");
		mock.assertVisible("modelcheck");
		mock.assertInvisible("both"); // model says no
		mock.assertInvisible("bothcheck"); // model says no, component says yes
		try
		{
			mock.getComponentFromLastRenderedPage("modelcheck").setDefaultModelObject("blaat");
			fail("should not be able to write to model");
		}
		catch (UnauthorizedActionException e)
		{
		}
	}

	/**
	 * Test workings if we are not using a page strategy but a panel replace strategy.
	 */
	@Test
	public void testPanelReplacement()
	{
		try
		{
			tearDown();
			setHomePage(org.wicketstuff.security.pages.container.HomePage.class);
			setSecureClass(ISecureContainer.class);
			setUp();
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			fail(e.getMessage());
		}
		mock.startPage(getHomePage());
		mock.assertRenderedPage(getHomePage());
		mock.clickLink("link");
		mock.assertRenderedPage(getLoginPage());
		FormTester form = mock.newFormTester("signInPanel:signInForm");
		form.setValue("username", "test");
		form.setValue("password", "test");
		form.submit();
		mock.assertRenderedPage(getHomePage());
		assertFalse(Session.get().isTemporary());
		mock.assertInvisible("panel");
		// note by adding a second panel visible if the main panel is invisible
		// we could tell the user he is not authorized or something like that
		Map<String, WaspAction> authorized = new HashMap<String, WaspAction>();
		authorized.put(SecureComponentHelper.alias(MySecurePanel.class),
			application.getActionFactory().getAction("access render"));
		login(authorized);
		mock.startPage(mock.getLastRenderedPage());
		mock.assertVisible("panel");
		mock.clickLink("link");
		mock.assertVisible("panel");
	}
}
