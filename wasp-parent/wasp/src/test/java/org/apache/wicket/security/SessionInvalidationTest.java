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

import org.apache.wicket.security.components.markup.html.form.SecureForm;
import org.apache.wicket.security.strategies.WaspAuthorizationStrategy;
import org.apache.wicket.Session;

import java.util.Map;
import java.util.HashMap;

/**
 * Test session invalidation
 *
 * @author marrink
 */
public class SessionInvalidationTest extends WaspAbstractTestBase {

	/**
	 * make sure the session is invalidated after a logoff
	 */
	public void testSessionInvalidationWithSingleLogin()
	{
		doLogin();
		Session session = Session.get();
		assertNotNull(session);
		assertEquals(session, mock.getWicketSession());
		mock.setupRequestAndResponse();
		assertTrue(((WaspSession)mock.getWicketSession()).logoff(null));
		mock.processRequestCycle();
		assertNotSame(session, mock.getWicketSession());
		assertFalse(((WaspAuthorizationStrategy)mock.getWicketSession().getAuthorizationStrategy())
				.isUserAuthenticated());

	}

	/**
	 * make sure the session is invalidated after a logoff
	 */
	public void testSessionInvalidationWithMultiLogin()
	{
		doLogin();
		Session session = Session.get();
		assertNotNull(session);
		assertEquals(session, mock.getWicketSession());

		Map authorized = new HashMap();
		authorized.put(SecureForm.class, application.getActionFactory().getAction("access render"));
		login(authorized);
		mock.setupRequestAndResponse();
		mock.processRequestCycle();
		assertTrue(((WaspAuthorizationStrategy)mock.getWicketSession().getAuthorizationStrategy())
				.isUserAuthenticated());
		assertEquals(session, mock.getWicketSession());
		logoff(authorized);
        
		mock.setupRequestAndResponse();
		mock.processRequestCycle();
		assertTrue(((WaspAuthorizationStrategy)mock.getWicketSession().getAuthorizationStrategy())
				.isUserAuthenticated());
		assertEquals(session, mock.getWicketSession());

		mock.setupRequestAndResponse();
		((WaspSession)mock.getWicketSession()).logoff(null);
		mock.processRequestCycle();
		assertNotSame(session, mock.getWicketSession());
		assertFalse(((WaspAuthorizationStrategy)mock.getWicketSession().getAuthorizationStrategy())
				.isUserAuthenticated());

	}

}