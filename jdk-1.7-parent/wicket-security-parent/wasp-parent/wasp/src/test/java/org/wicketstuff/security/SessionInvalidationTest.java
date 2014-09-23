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

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Session;
import org.junit.Test;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.components.SecureComponentHelper;
import org.wicketstuff.security.components.markup.html.form.SecureForm;
import org.wicketstuff.security.strategies.WaspAuthorizationStrategy;

/**
 * Test session invalidation
 * 
 * @author marrink
 */
public class SessionInvalidationTest extends WaspAbstractTestBase
{

	/**
	 * make sure the session is invalidated after a logoff
	 */
	@Test
	public void testSessionInvalidationWithSingleLogin()
	{
		doLogin();
		Session session = Session.get();
		assertNotNull(session);
		assertEquals(session, mock.getSession());
		assertTrue(((WaspSession)mock.getSession()).logoff(null));
		mock.processRequest();
		assertTrue(session.isSessionInvalidated());
		assertFalse(((WaspAuthorizationStrategy)mock.getSession().getAuthorizationStrategy()).isUserAuthenticated());

	}

	/**
	 * make sure the session is invalidated after a logoff
	 */
	@Test
	public void testSessionInvalidationWithMultiLogin()
	{
		doLogin();
		Session session = Session.get();
		assertNotNull(session);
		assertEquals(session, mock.getSession());

		Map<String, WaspAction> authorized = new HashMap<String, WaspAction>();
		authorized.put(SecureComponentHelper.alias(SecureForm.class),
			application.getActionFactory().getAction("access render"));
		login(authorized);
		mock.processRequest();
		assertTrue(((WaspAuthorizationStrategy)mock.getSession().getAuthorizationStrategy()).isUserAuthenticated());
		assertEquals(session, mock.getSession());
		logoff(authorized);

		mock.processRequest();
		assertTrue(((WaspAuthorizationStrategy)mock.getSession().getAuthorizationStrategy()).isUserAuthenticated());
		assertEquals(session, mock.getSession());

		((WaspSession)mock.getSession()).logoff(null);
		mock.processRequest();
		assertTrue(session.isSessionInvalidated());
		assertFalse(((WaspAuthorizationStrategy)mock.getSession().getAuthorizationStrategy()).isUserAuthenticated());

	}

}
