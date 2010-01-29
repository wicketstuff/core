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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;

/**
 * Test the HomePage and follow up pages.
 * 
 * @author Olger Warnier
 */
public class HomePageTest extends AbstractSecureTestPage
{
	protected static final Log log = LogFactory.getLog(HomePageTest.class);

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		ApplicationContextMock appctx = new ApplicationContextMock();

		application.addComponentInstantiationListener(new SpringComponentInjector(application,
			appctx, true));

	}

	/**
	 * Test Login to the application. This makes use of a mock login page as the
	 * application is based upon Spring Security authentication.
	 */
	public void testLogin()
	{
		loginToApp("test");
		logoutOfApp();
	}

	public void testStartFirstSecure()
	{
		/*
		 * mock.startPage(MockLoginPage.class); mock.setupRequestAndResponse();
		 * assertTrue(mock.getWicketSession().isTemporary());
		 * mock.processRequestCycle(MockLoginPage.class); // loginpage, else the homepage
		 * will be used which will trigger a bind // because a throw
		 * restartResponseAtInterceptPageexception will trigger // a session.bind
		 * SwarmFormTester form = mock.newFormTester("form"); form.setValue("username",
		 * "test"); form.submit(); mock.assertRenderedPage(MockHomePage.class);
		 * mock.setupRequestAndResponse(); assertFalse(Session.get().isTemporary());
		 * mock.processRequestCycle(MockLoginPage.class);
		 */
		ApplicationContextMock appctx = new ApplicationContextMock();
		// appctx.putBean("patientService", patientService);

		application.addComponentInstantiationListener(new SpringComponentInjector(application,
			appctx, true));

		loginToApp("user");
		// first link is the create patient link
		// have to set the proper controls in order to proceed.
		mock.assertRenderedPage(HomePage.class);

		// mock.clickLink("to_firstsecure");
		// Page thePage = mock.getLastRenderedPage();

		// mock.assertRenderedPage(FirstSecurePage.class);

		logoutOfApp();

	}

}
