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
public class FirstSecurePageTest extends AbstractSecureTestPage
{
	private static final Log log = LogFactory.getLog(FirstSecurePageTest.class);

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

	public void testStartFirstSecureAsUser()
	{
		log.debug("Login as user");
		loginToApp("user");
		mock.assertRenderedPage(HomePage.class);

		mock.startPage(FirstSecurePage.class);
		mock.assertRenderedPage(FirstSecurePage.class);
		// the user has no rights to use the second page. So it is disabled.
		mock.assertInvisible("to_secondsecurepage");

		// mock.assertContains("ajaxLink");
		// the user has no rights to use the ajaxLink. So it is disabled.
		// mock.assertInvisible("ajaxLink");
		logoutOfApp();
	}

	public void testStartFirstSecureAsAdmin()
	{
		log.debug("Login as admin");
		loginToApp("admin");
		mock.assertRenderedPage(HomePage.class);

		mock.startPage(FirstSecurePage.class);
		mock.assertRenderedPage(FirstSecurePage.class);

		mock.assertContains("to_secondsecurepage");

		mock.assertContains("ajaxLink");
		mock.clickLink("ajaxLink", true);

		logoutOfApp();
	}

}