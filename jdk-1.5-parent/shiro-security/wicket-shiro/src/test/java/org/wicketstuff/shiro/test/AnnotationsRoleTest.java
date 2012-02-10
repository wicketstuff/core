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
package org.wicketstuff.shiro.test;

import junit.framework.TestCase;

import org.apache.wicket.util.tester.WicketTester;

/**
 * Test the annotations package of the auth-roles project.
 * 
 * @author Eelco Hillenius
 */
public class AnnotationsRoleTest extends TestCase
{
	WicketTester tester;

	/**
	 * Construct.
	 */
	public AnnotationsRoleTest()
	{
		super();
	}

	/**
	 * Construct.
	 * 
	 * @param arg0
	 */
	public AnnotationsRoleTest(String arg0)
	{
		super(arg0);
	}

	@Override
	protected void setUp() throws Exception
	{
		tester = new WicketTester();
	}

	@Override
	protected void tearDown() throws Exception
	{
		tester.destroy();
	}

	public void testSomething()
	{
		// TODO -- actually test something!
	}

// /**
// * @throws Exception
// */
// public void testClear() throws Exception
// {
// tester.getApplication().getSecuritySettings().setAuthorizationStrategy(
// new RoleAuthorizationStrategy(new UserRolesAuthorizer("FOO")));
// tester.startPage(new ITestPageSource()
// {
// private static final long serialVersionUID = 1L;
//
// public Page getTestPage()
// {
// return new NormalPage();
// }
// });
// tester.assertRenderedPage(NormalPage.class);
// }
//
// /**
// * @throws Exception
// */
// public void testAuthorized() throws Exception
// {
// WicketTester tester = new WicketTester();
// tester.getApplication().getSecuritySettings().setAuthorizationStrategy(
// new RoleAuthorizationStrategy(new UserRolesAuthorizer("ADMIN")));
// tester.startPage(new ITestPageSource()
// {
// private static final long serialVersionUID = 1L;
//
// public Page getTestPage()
// {
// return new AdminPage();
// }
// });
// tester.assertRenderedPage(AdminPage.class);
// }
//
// /**
// * @throws Exception
// */
// public void testNotAuthorized() throws Exception
// {
// WicketTester tester = new WicketTester();
// tester.getApplication().getSecuritySettings().setAuthorizationStrategy(
// new RoleAuthorizationStrategy(new UserRolesAuthorizer("USER")));
// final class Listener implements IUnauthorizedComponentInstantiationListener
// {
// private boolean eventReceived = false;
//
// public void onUnauthorizedInstantiation(Component component)
// {
// eventReceived = true;
// }
// }
// Listener listener = new Listener();
// tester.getApplication()
// .getSecuritySettings()
// .setUnauthorizedComponentInstantiationListener(listener);
//
// try
// {
// tester.startPage(new ITestPageSource()
// {
// private static final long serialVersionUID = 1L;
//
// public Page getTestPage()
// {
// return new AdminPage();
// }
// });
// assertTrue("an authorization exception event should have been received",
// listener.eventReceived);
// }
// catch (Exception e)
// {
// if (!(e.getCause() instanceof InvocationTargetException &&
// ((InvocationTargetException)e.getCause()).getTargetException() instanceof
// UnauthorizedInstantiationException))
// {
// throw e;
// }
// }
// }
//
// /**
// * Authorizer class that uses the TS user and it's defined string[] roles.
// */
// private static final class UserRolesAuthorizer implements IRoleCheckingStrategy, Serializable
// {
// private static final long serialVersionUID = 1L;
//
// private final Roles roles;
//
// /**
// * Construct.
// *
// * @param roles
// */
// public UserRolesAuthorizer(String roles)
// {
// this.roles = new Roles(roles);
// }
//
// /**
// * @see org.wicketstuff.ki.strategies.IRoleCheckingStrategy#hasAnyRole(Roles)
// */
// public boolean hasAnyRole(Roles roles)
// {
// return this.roles.hasAnyRole(roles);
// }
// }
}
