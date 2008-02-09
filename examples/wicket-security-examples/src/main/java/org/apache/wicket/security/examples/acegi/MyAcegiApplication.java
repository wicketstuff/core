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
package org.apache.wicket.security.examples.acegi;

import org.acegisecurity.AuthenticationManager;
import org.apache.wicket.security.WaspApplication;
import org.apache.wicket.security.examples.acegi.authentication.AcegiLoginContext;
import org.apache.wicket.security.examples.acegi.pages.LoginPage;
import org.apache.wicket.security.examples.customactions.MyApplication;
import org.apache.wicket.security.hive.authentication.LoginContext;
import org.apache.wicket.security.swarm.SwarmWebApplication;
import org.apache.wicket.spring.SpringWebApplication;

/**
 * Application that uses Acegi for authentication. Note that in this example we
 * extend {@link MyApplication} but you usually will want to extend
 * {@link SwarmWebApplication} or {@link SpringWebApplication} and then
 * implement {@link WaspApplication} yourself.
 * 
 * @author marrink
 */
public class MyAcegiApplication extends MyApplication implements AcegiApplication
{
	// To be injected by Spring
	private AuthenticationManager authenticationManager;

	/**
	 * Constructor.
	 */
	public MyAcegiApplication()
	{
	}

	/**
	 * 
	 * @see org.apache.wicket.security.examples.acegi.AcegiApplication#getAuthenticationManager()
	 */
	public AuthenticationManager getAuthenticationManager()
	{
		return authenticationManager;
	}

	/**
	 * To be injected by Spring.
	 * 
	 * @param authenticationManager
	 */
	public void setAuthenticationManager(final AuthenticationManager authenticationManager)
	{
		this.authenticationManager = authenticationManager;
	}

	/**
	 * 
	 * @see org.apache.wicket.security.examples.customactions.MyApplication#getLoginPage()
	 */
	public Class getLoginPage()
	{
		return LoginPage.class;
	}

	/**
	 * 
	 * @see org.apache.wicket.security.examples.customactions.MyApplication#getLogoffContext()
	 */
	public LoginContext getLogoffContext()
	{
		return new AcegiLoginContext();
	}

}
