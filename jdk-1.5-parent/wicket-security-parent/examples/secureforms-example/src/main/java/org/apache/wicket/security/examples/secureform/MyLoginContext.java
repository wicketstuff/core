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

package org.apache.wicket.security.examples.secureform;

import org.apache.wicket.security.authentication.LoginException;
import org.apache.wicket.security.hive.authentication.DefaultSubject;
import org.apache.wicket.security.hive.authentication.Subject;
import org.apache.wicket.security.hive.authentication.UsernamePasswordContext;
import org.apache.wicket.security.hive.authorization.SimplePrincipal;
import org.apache.wicket.util.lang.Objects;

/**
 * MyLoginContext gives a succesfull authentication when the username is the same as the
 * password By default all succesfull logged on users receive the 'user' principal. When
 * you login with admin/admin, you'll get the admin principal too.
 * 
 * @author Olger Warnier
 * 
 */
public class MyLoginContext extends UsernamePasswordContext
{

	public MyLoginContext(String username, String password)
	{
		super(username, password);
	}

	@Override
	protected Subject getSubject(String username, String password) throws LoginException
	{
		if (username != null && Objects.equal(username, password))
		{
			DefaultSubject subject = new DefaultSubject();
			subject.addPrincipal(new SimplePrincipal("user"));

			if (username.equalsIgnoreCase("admin"))
			{
				subject.addPrincipal(new SimplePrincipal("admin"));
			}
			return subject;
		}
		throw new LoginException("Username and password do not match any known user.");
	}
}
