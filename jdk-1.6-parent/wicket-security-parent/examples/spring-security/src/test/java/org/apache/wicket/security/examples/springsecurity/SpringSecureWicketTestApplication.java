/*
 * Copyright 2008 Stichting JoiningTracks, The Netherlands
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.wicket.security.examples.springsecurity;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.Page;
import org.apache.wicket.security.examples.springsecurity.security.MockLoginPage;
import org.apache.wicket.security.hive.HiveMind;
import org.apache.wicket.security.hive.config.PolicyFileHiveFactory;
import org.apache.wicket.security.hive.config.SwarmPolicyFileHiveFactory;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.TestingAuthenticationProvider;

/**
 * WicketTestApplication for testing Spring Security and Wicket Security based
 * applications
 * 
 * @author Olger Warnier
 */
public class SpringSecureWicketTestApplication extends SpringSecureWicketApplication
{

	private static final Log log = LogFactory.getLog(SpringSecureWicketTestApplication.class);

	private AuthenticationManager authenticationManager;

	@Override
	public void init()
	{
		ApplicationContextMock appctx = new ApplicationContextMock();

		// Make injection of spring beans in wicket-related classes possible using
		// @SpringBean.
		addComponentInstantiationListener(new SpringComponentInjector(this, appctx, true));

		ProviderManager authMan = new ProviderManager();
		List<TestingAuthenticationProvider> providerList =
			new ArrayList<TestingAuthenticationProvider>();
		providerList.add(new TestingAuthenticationProvider());
		authMan.setProviders(providerList);

		// appctx.putBean("testAuthenticationProvider", authProvider);
		// appctx.putBean("authenticationManager", authMan);
		this.authenticationManager = authMan;

		this.setupStrategyFactory();
		this.setupActionFactory();

		this.setUpHive(); // this one should be called automatically ?
		// Wicket markup setting.
		getMarkupSettings().setStripComments(false);
		getMarkupSettings().setStripWicketTags(false);
		getMarkupSettings().setDefaultBeforeDisabledLink("");
		getMarkupSettings().setDefaultAfterDisabledLink("");
	}

	@Override
	protected Object getHiveKey()
	{
		return "test";
	}

	/*
	 * @Override public Session newSession(Request request, Response response) {
	 * ZeuzSession zeuzSession = new ZeuzSession(this, request); return zeuzSession; }
	 */

	@Override
	protected void setUpHive()
	{
		PolicyFileHiveFactory factory = new SwarmPolicyFileHiveFactory(getActionFactory());
		try
		{
			log.debug("realpath:" + getServletContext().getResource("."));
			if (!factory.addPolicyFile(getServletContext().getResource("WEB-INF/standard.hive")))
			{
				log
					.error("Could not add the standard.hive policy file, authorization will not work");
				return;
			}
		}
		catch (MalformedURLException e)
		{
			log.error(e.getMessage(), e);
		}

		HiveMind.registerHive(this.getHiveKey(), factory);

	}

	@Override
	public Class< ? extends Page> getHomePage()
	{
		return HomePage.class;
	}

	/**
	 * Return the special test mock login page that enables the use of the ACEGI
	 * TestingAuthenticationProvider
	 * 
	 * @return MockLoginPage page for use with the TestingAuthenticationProvider
	 */
	@Override
	public Class< ? extends Page> getLoginPage()
	{
		return MockLoginPage.class;
	}

	/**
	 * authenticationManager for ACEGI. In the test setup, the manager contains the
	 * TestingAuthenticationProvider
	 * 
	 * @return Spring Security authentication manager
	 */
	@Override
	public AuthenticationManager getAuthenticationManager()
	{
		return authenticationManager;
	}

	@Override
	@Required
	public void setAuthenticationManager(AuthenticationManager authenticationManager)
	{
		this.authenticationManager = authenticationManager;
	}

}
