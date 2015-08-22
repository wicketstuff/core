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
package org.apache.wicket.security.examples.httplogin.basic;

import java.net.MalformedURLException;

import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.security.examples.httplogin.basic.pages.HomePage;
import org.apache.wicket.security.examples.httplogin.basic.pages.LoginPage;
import org.apache.wicket.security.hive.HiveMind;
import org.apache.wicket.security.hive.config.PolicyFileHiveFactory;
import org.apache.wicket.security.hive.config.SwarmPolicyFileHiveFactory;
import org.apache.wicket.security.swarm.SwarmWebApplication;

/**
 * default implementation of a swarm app.
 * 
 * @author marrink
 * 
 */
public class MyApplication extends SwarmWebApplication
{
	/**
	 * 
	 */
	public MyApplication()
	{
		super();
	}

	/**
	 * @see org.apache.wicket.security.swarm.SwarmWebApplication#init()
	 */
	@Override
	protected void init()
	{
		super.init();
		// misc settings
		getMarkupSettings().setCompressWhitespace(true);
		getMarkupSettings().setStripComments(true);
		getMarkupSettings().setStripWicketTags(true);
		getRequestCycleSettings().setResponseRequestEncoding("UTF-8");
	}

	/**
	 * @see org.apache.wicket.security.swarm.SwarmWebApplication#getHiveKey()
	 */
	@Override
	protected Object getHiveKey()
	{
		// if you are using servlet api 2.5 i would suggest using:
		// return getServletContext().getContextPath();

		// if not you have several options:
		// -an initparam in web.xml
		// -a static object
		// -a random object
		// -whatever you can think of

		// for this example we will be using a fixed string
		return "http auth";

	}

	/**
	 * @see org.apache.wicket.security.swarm.SwarmWebApplication#setUpHive()
	 */
	@Override
	protected synchronized void setUpHive()
	{
		// example of shared hive
		// check first
		if (HiveMind.getHive(getHiveKey()) == null)
		{
			// create factory
			PolicyFileHiveFactory factory = new SwarmPolicyFileHiveFactory(getActionFactory());
			try
			{
				// this example uses 1 policy file but you can add as many as
				// you
				// like
				factory.addPolicyFile(getServletContext().getResource("/WEB-INF/httpauth.hive"));
			}
			catch (MalformedURLException e)
			{
				throw new WicketRuntimeException(e);
			}
			// register factory
			HiveMind.registerHive(getHiveKey(), factory);
		}

	}

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class< ? extends Page> getHomePage()
	{
		return HomePage.class;
	}

	/**
	 * @see org.apache.wicket.security.WaspApplication#getLoginPage()
	 */
	public Class< ? extends Page> getLoginPage()
	{
		return LoginPage.class;
	}

}
