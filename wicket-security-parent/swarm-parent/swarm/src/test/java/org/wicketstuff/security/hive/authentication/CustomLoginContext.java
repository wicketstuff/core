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

import org.wicketstuff.security.hive.authorization.Principal;

/**
 * LoginContext which returns a subject containing the principal passed on to the constructor.
 * 
 * @author marrink
 */
public class CustomLoginContext extends LoginContext
{
	private Principal principal;

	/**
	 * Constructor for logoff.
	 */
	public CustomLoginContext()
	{
	}

	/**
	 * 
	 * Constructor.
	 * 
	 * @param principal
	 */
	public CustomLoginContext(Principal principal)
	{
		super(false);
		this.principal = principal;
	}

	/**
	 * @see org.wicketstuff.security.hive.authentication.LoginContext#login()
	 */
	@Override
	public Subject login()
	{
		DefaultSubject subject = new DefaultSubject();
		subject.addPrincipal(principal);
		return subject;
	}

}
