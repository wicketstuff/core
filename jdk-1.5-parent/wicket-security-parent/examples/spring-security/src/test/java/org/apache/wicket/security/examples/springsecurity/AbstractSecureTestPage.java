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

import java.io.File;
import java.util.Locale;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.security.WaspSession;
import org.apache.wicket.security.examples.springsecurity.security.MockLoginPage;
import org.apache.wicket.security.examples.springsecurity.security.SpringSecureLoginContext;
import org.apache.wicket.security.hive.HiveMind;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;

/**
 * Abstract base page for testing wicket pages based on the ACEGI security setup. This
 * base creates the WicketTester as a mock, usable by the testpages that extend this page.
 * <p/>
 * Two convenience methods are available to login and logoff
 * 
 * @author Olger Warnier
 */
public abstract class AbstractSecureTestPage extends TestCase
{
	private static final Log log = LogFactory.getLog(AbstractSecureTestPage.class);

	/**
	 * The swarm application used for the test.
	 */
	protected WebApplication application;

	/**
	 * Handle to the mock environment.
	 */
	protected WicketTester mock;

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception
	{
		Locale.setDefault(Locale.UK);

		String webAppLocation;

		File currentDir = new File(".");
		log.debug("CurrentDir:" + currentDir.getCanonicalPath());
		if (currentDir.getCanonicalPath().endsWith("examples/spring-security"))
		{
			webAppLocation = "src/main/webapp";
		}
		else
		{
			webAppLocation = "examples/spring-security/src/main/webapp";
		}

		log.debug("start the testapplication with location: " + webAppLocation);
		application = new SpringSecureWicketTestApplication();
		mock = new WicketTester(application, webAppLocation);
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown()
	{
		mock.setupRequestAndResponse();
		mock.getWicketSession().invalidate();
		mock.processRequestCycle();
		mock.destroy();
		mock = null;
		application = null;
		HiveMind.unregisterHive("test");
	}

	/**
	 * Convenience method to login to the application, mostly required as the whole is
	 * protected by ACEGI and Swarm The test structure makes us of the ACEGI
	 * TestingAuthentication provider, meaning that you can pass on any username (the
	 * password is the same).
	 * <p/>
	 * The user 'medical' has an additional role 'ROLE_MEDICAL'. All other users have
	 * 'ROLE_USER'
	 * 
	 * @param userName
	 */
	protected void loginToApp(String userName)
	{
		mock.startPage(MockLoginPage.class);
		mock.assertRenderedPage(MockLoginPage.class);
		FormTester form = mock.newFormTester("form");
		form.setValue("username", userName);
		form.submit();
		mock.assertRenderedPage(HomePage.class);
		mock.processRequestCycle();

	}

	/**
	 * Convenience method to logout of the application.
	 */
	protected void logoutOfApp()
	{
		assertTrue(((WaspSession) mock.getWicketSession()).logoff(new SpringSecureLoginContext()));
		mock.startPage(HomePage.class);
		mock.assertRenderedPage(HomePage.class);
		mock.processRequestCycle();
	}
}
