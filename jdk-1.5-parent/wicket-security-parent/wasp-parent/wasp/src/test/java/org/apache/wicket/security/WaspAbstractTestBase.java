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

import java.util.Collections;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.security.actions.RegistrationException;
import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.actions.WaspActionFactory;
import org.apache.wicket.security.authentication.LoginException;
import org.apache.wicket.security.components.ISecureComponent;
import org.apache.wicket.security.pages.login.LoginPage;
import org.apache.wicket.security.pages.secure.HomePage;
import org.apache.wicket.security.strategies.StrategyFactory;
import org.apache.wicket.security.strategies.WaspAuthorizationStrategy;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base test helper for seperate test classes.
 * 
 * @author marrink
 * @author Olger Warnier
 */
public abstract class WaspAbstractTestBase extends TestCase
{

	protected static final Logger log = LoggerFactory.getLogger(GeneralTest.class);

	protected WicketTester mock = null;

	protected WaspWebApplication application;

	private Class< ? extends Page> homePage = HomePage.class;

	private Class< ? extends Page> loginPage = LoginPage.class;

	private Class< ? extends ISecureComponent> secureClass = ISecureComponent.class;

	/**
	 * @see TestCase#setUp()
	 */
	@Override
	protected void setUp()
	{
		mock = new WicketTester(application = new WaspWebApplication()
		{
			private WaspActionFactory actionFactory;

			private StrategyFactory strategyFactory;

			@Override
			protected void setupActionFactory()
			{
				actionFactory = new WaspActionFactory()
				{
					/**
					 * 
					 * @see WaspActionFactory#getAction(org.apache.wicket.authorization.Action)
					 */
					public WaspAction getAction(Action actions)
					{
						return new StringAction(convertActions(actions.getName()));
					}

					/**
					 * 
					 * @see org.apache.wicket.security.actions.ActionFactory#getAction(String)
					 */
					public WaspAction getAction(String actions)
					{
						return new StringAction(convertActions(actions));
					}

					private String convertActions(String name)
					{
						if (name == null)
							return "";
						return name.toLowerCase();
					}

					/**
					 * 
					 * @see org.apache.wicket.security.actions.ActionFactory#getAction(Class)
					 */
					public WaspAction getAction(Class< ? extends WaspAction> waspActionClass)
					{
						return new StringAction(waspActionClass.getName().substring(
							waspActionClass.getName().lastIndexOf('.') + 1).toLowerCase());
					}

					/**
					 * 
					 * @see org.apache.wicket.security.actions.ActionFactory#register(Class,
					 *      String)
					 */
					public WaspAction register(Class< ? extends WaspAction> waspActionClass,
							String name) throws RegistrationException
					{
						throw new RegistrationException(
							"this test factory does not allow registration");
					}

					/**
					 * 
					 * @see org.apache.wicket.security.actions.ActionFactory#getRegisteredActions()
					 */
					public List<WaspAction> getRegisteredActions()
					{
						return Collections.emptyList();
					}

					/**
					 * 
					 * @see org.apache.wicket.security.actions.ActionFactory#destroy()
					 */
					public void destroy()
					{
						// noop
					}
				};
			}

			@Override
			protected void setupStrategyFactory()
			{
				strategyFactory = new StrategyFactory()
				{
					public void destroy()
					{
						// noop
					}

					public WaspAuthorizationStrategy newStrategy()
					{
						return new TestStrategy(getSecureClass());
					}

				};
			}

			@Override
			public Class< ? extends Page> getHomePage()
			{
				return WaspAbstractTestBase.this.getHomePage();
			}

			public WaspActionFactory getActionFactory()
			{
				return actionFactory;
			}

			public Class< ? extends Page> getLoginPage()
			{
				return WaspAbstractTestBase.this.getLoginPage();
			}

			public StrategyFactory getStrategyFactory()
			{
				return strategyFactory;
			}
		}, "src/test/java/" + getClass().getPackage().getName().replace('.', '/'));
	}

	/**
	 * 
	 * @see TestCase#tearDown()
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
		setHomePage(HomePage.class);
		setLoginPage(LoginPage.class);
		setSecureClass(ISecureComponent.class);
	}

	/**
	 * @return Returns the secureClass.
	 */
	public Class< ? extends ISecureComponent> getSecureClass()
	{
		return secureClass;
	}

	/**
	 * @param secureClass
	 *            The secureClass to set.
	 */
	public void setSecureClass(Class< ? extends ISecureComponent> secureClass)
	{
		this.secureClass = secureClass;
	}

	/**
	 * @return Returns the homePage.
	 */
	public Class< ? extends Page> getHomePage()
	{
		return homePage;
	}

	/**
	 * @param homePage
	 *            The homePage to set.
	 */
	public void setHomePage(Class< ? extends Page> homePage)
	{
		this.homePage = homePage;
	}

	/**
	 * @return Returns the loginPage.
	 */
	public Class< ? extends Page> getLoginPage()
	{
		return loginPage;
	}

	/**
	 * @param loginPage
	 *            The loginPage to set.
	 */
	public void setLoginPage(Class< ? extends Page> loginPage)
	{
		this.loginPage = loginPage;
	}

	/**
	 * Login through the login page.
	 */
	public void doLogin()
	{
		mock.setupRequestAndResponse();
		mock.processRequestCycle();
		mock.assertRenderedPage(getLoginPage());
		// assertTrue(Session.get().isTemporary()); does not work in test
		FormTester form = mock.newFormTester("signInPanel:signInForm");
		form.setValue("username", "test");
		form.setValue("password", "test");
		form.submit();
		mock.assertRenderedPage(getHomePage());
		assertFalse(Session.get().isTemporary());
	}

	/**
	 * login through the session bypassing the loginpage. Note that the login page
	 * automaticly grants access to the homepage, here you must do it yourself. Note these
	 * rights are added to any existing ones replacing only if the class was already added
	 * previously.
	 * 
	 * @param authorized
	 *            map containing classes and the actions the users has on them.
	 */
	protected void login(Map<String, WaspAction> authorized)
	{
		try
		{
			((WaspSession) mock.getWicketSession()).login(authorized);
		}
		catch (LoginException e)
		{
			log.error(e.getMessage(), e);
			fail(e.getMessage());
		}
	}

	/**
	 * Logoff. If a map is specified only a partial logoff is performed removing only
	 * those rights in the map.
	 * 
	 * @param authorized
	 */
	protected void logoff(Map<String, WaspAction> authorized)
	{
		((WaspSession) mock.getWicketSession()).logoff(authorized);
	}

	protected WicketTester getMock()
	{
		return mock;
	}

	protected WaspWebApplication getApplication()
	{
		return application;
	}
}
