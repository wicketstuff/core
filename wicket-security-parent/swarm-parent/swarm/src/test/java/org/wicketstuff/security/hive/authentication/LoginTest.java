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
package org.wicketstuff.security.hive.authentication;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.security.authentication.LoginException;
import org.wicketstuff.security.hive.authorization.SimplePrincipal;
import org.wicketstuff.security.pages.VerySecurePage;

/**
 * @author marrink
 */
public class LoginTest
{
	private static final Logger log = LoggerFactory.getLogger(LoginTest.class);

	/**
	 * Test method for
	 * {@link org.wicketstuff.security.hive.authentication.LoginContainer#login(org.wicketstuff.security.hive.authentication.LoginContext)}
	 * .
	 */
	@Test
	public void testLogin()
	{
		LoginContainer container = new LoginContainer();
		try
		{
			container.login(null);
			fail("LoginContext is required");
		}
		catch (LoginException e)
		{
		}
		LoginContext ctx = new LoginContext(true)
		{
			@Override
			public Subject login()
			{
				DefaultSubject subject = new DefaultSubject();
				subject.addPrincipal(new SimplePrincipal("home"));
				return subject;
			}
		};
		assertFalse(container.isClassAuthenticated(getClass()));
		try
		{
			WicketTester mock = new WicketTester();
			container.login(ctx);
			mock.processRequest();
			mock.destroy();

		}
		catch (LoginException e)
		{
			log.error(e.getMessage(), e);
			fail(e.getMessage());
		}
		assertNotNull(container.getSubject());
		assertTrue(container.isClassAuthenticated(getClass()));
		// shows that even though the new context does not authenticate anything
		// the
		// previous one does
		ctx = new LoginContext(1)
		{
			// bad example, do not create an anonymous Subject in a LoginContext
			// as it will cause the context to be serialized along with the
			// subject
			@Override
			public Subject login()
			{
				return new MyDefaultSubject();
			}

			/**
			 * @see org.wicketstuff.security.hive.authentication.LoginContext#preventsAdditionalLogins()
			 */
			@Override
			public boolean preventsAdditionalLogins()
			{
				return true;
			}
		};
		try
		{
			WicketTester mock = new WicketTester();
			container.login(ctx);
			mock.processRequest();
			mock.destroy();
		}
		catch (LoginException e)
		{
			log.error(e.getMessage(), e);
			fail(e.getMessage());
		}
		assertTrue(container.isClassAuthenticated(getClass()));
		// note changing the order does not matter since the first that
		// authenticates true
		// is used.
	}

	/**
	 * Test method for
	 * {@link org.wicketstuff.security.hive.authentication.LoginContainer#logoff(org.wicketstuff.security.hive.authentication.LoginContext)}
	 * .
	 */
	@Test
	public void testLogoff()
	{
		LoginContainer container = new LoginContainer();
		LoginContext ctx = new LoginContext()
		{
			@Override
			public Subject login()
			{
				return new DefaultSubject();
			}
		};
		try
		{
			WicketTester mock = new WicketTester();
			container.login(ctx);
			mock.processRequest();
			mock.destroy();
		}
		catch (LoginException e)
		{
			log.error(e.getMessage(), e);
			fail(e.getMessage());
		}
		assertNotNull(container.getSubject());
		WicketTester mock = new WicketTester();
		container.logoff(ctx);
		mock.processRequest();
		mock.destroy();
		assertNull(container.getSubject());
	}

	/**
	 * Test method for multi login.
	 */
	@Test
	public void testIsClassAuthenticated()
	{
		// for multilogin to work the least authenticating login should be at
		// the bottom
		LoginContainer container = new LoginContainer();
		LoginContext low = new PrimaryLoginContext();
		LoginContext high = new SecondaryLoginContext();
		try
		{
			WicketTester mock = new WicketTester();
			container.login(low);
			assertFalse(container.isClassAuthenticated(VerySecurePage.class));
			assertTrue(container.getSubject()
				.getPrincipals()
				.contains(new SimplePrincipal("basic")));
			assertFalse(container.getSubject()
				.getPrincipals()
				.contains(new SimplePrincipal("admin")));
			container.login(high);
			assertTrue(container.isClassAuthenticated(VerySecurePage.class));
			assertTrue(container.getSubject()
				.getPrincipals()
				.contains(new SimplePrincipal("admin")));
			mock.processRequest();
			mock.destroy();
		}
		catch (LoginException e)
		{
			log.error(e.getMessage(), e);
			fail(e.getMessage());
		}
	}

	/**
	 * tests if the preventadditionallogin flag works as expected
	 * 
	 */
	@Test
	public void testPreventLogin()
	{
		LoginContainer container = new LoginContainer();
		try
		{
			WicketTester mock = new WicketTester();
			container.login(new myContext());
			mock.processRequest();
			mock.destroy();
		}
		catch (LoginException e)
		{
			log.error(e.getMessage(), e);
			fail(e.getMessage());
		}
		try
		{
			WicketTester mock = new WicketTester();
			container.login(new myContext());
			fail("Should not be able to login");
			mock.processRequest();
			mock.destroy();
		}
		catch (LoginException e)
		{
		}
		WicketTester mock = new WicketTester();
		container.logoff(new myContext());
		mock.processRequest();
		mock.destroy();
		try
		{
			mock = new WicketTester();
			container.login(new myContext());
			mock.processRequest();
			mock.destroy();
		}
		catch (LoginException e)
		{
			log.error(e.getMessage(), e);
			fail(e.getMessage());
		}
	}

	private static final class MyDefaultSubject extends DefaultSubject
	{
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 * @see org.wicketstuff.security.hive.authentication.DefaultSubject#isClassAuthenticated(java.lang.Class)
		 */
		@Override
		public boolean isClassAuthenticated(Class<?> class1)
		{
			return false;
		}

		/**
		 * 
		 * @see org.wicketstuff.security.hive.authentication.DefaultSubject#isComponentAuthenticated(org.apache.wicket.Component)
		 */
		@Override
		public boolean isComponentAuthenticated(Component component)
		{
			return false;
		}

		/**
		 * 
		 * @see org.wicketstuff.security.hive.authentication.DefaultSubject#isModelAuthenticated(org.apache.wicket.model.IModel,
		 *      org.apache.wicket.Component)
		 */
		@Override
		public boolean isModelAuthenticated(IModel<?> model, Component component)
		{
			return false;
		}
	}

	private static final class myContext extends LoginContext
	{
		/**
		 * @see org.wicketstuff.security.hive.authentication.LoginContext#login()
		 */
		@Override
		public Subject login()
		{
			return new DefaultSubject();
		}

	}
}
