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
package org.apache.wicket.security.examples.multilogin.authentication;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.security.authentication.LoginException;
import org.apache.wicket.security.examples.authorization.MyPrincipal;
import org.apache.wicket.security.hive.authentication.DefaultSubject;
import org.apache.wicket.security.hive.authentication.LoginContext;
import org.apache.wicket.security.hive.authentication.Subject;
import org.apache.wicket.security.hive.authentication.WicketSubject;
import org.apache.wicket.util.lang.Objects;

/**
 * Secondary authentication for topsecret pages like the commit page for transactions.
 * 
 * @author marrink
 * 
 */
public class Level1Context extends LoginContext
{
	/**
	 * Subject for secondary Login. authenticates all pages.
	 * 
	 * @author marrink
	 */
	private static final class MySecondarySubject extends DefaultSubject
	{
		private static final long serialVersionUID = 1L;

		/**
		 * @see WicketSubject#isClassAuthenticated(java.lang.Class)
		 */
		@Override
		public boolean isClassAuthenticated(Class< ? > class1)
		{
			// we could return true only if the page is a Topsecretpage,
			// but this way we can also login inmediatly on the second
			// login page, without being required to go through the first.
			// if we had a bookmarkable link to this page.
			return true;
		}

		/**
		 * @see WicketSubject#isComponentAuthenticated(org.apache.wicket.Component)
		 */
		@Override
		public boolean isComponentAuthenticated(Component component)
		{
			return true;
		}

		/**
		 * @see WicketSubject#isModelAuthenticated(org.apache.wicket.model.IModel,
		 *      org.apache.wicket.Component)
		 */
		@Override
		public boolean isModelAuthenticated(IModel< ? > model, Component component)
		{
			return true;
		}
	}

	private String username;

	private String token;

	/**
	 * @param token
	 * @param username
	 */
	public Level1Context(String username, String token)
	{
		super(1);
		this.username = username;
		this.token = token;

	}

	/**
	 * @see org.apache.wicket.security.hive.authentication.LoginContext#login()
	 */
	@Override
	public Subject login() throws LoginException
	{
		// irrelevant check
		if (Objects.equal(username, token))
		{
			// usually there will be a db call to verify the credentials
			DefaultSubject subject = new MySecondarySubject();
			// add principals as required
			// Note if topsecret implied basic we would not have to add it here.
			// Also we only need this because we can login through a
			// bookmarkable url, thereby bypassing the first login page.
			// if we know we always come through the first loginpage we can
			// remove basic here.
			subject.addPrincipal(new MyPrincipal("basic"));
			subject.addPrincipal(new MyPrincipal("topsecret"));
			return subject;
		}
		throw new LoginException("username does not match token");
	}

	/**
	 * 
	 * @see org.apache.wicket.security.hive.authentication.LoginContext#preventsAdditionalLogins()
	 */
	@Override
	public boolean preventsAdditionalLogins()
	{
		// we don't want / need to upgrade the credentials of this user any
		// further
		return true;
	}

}
