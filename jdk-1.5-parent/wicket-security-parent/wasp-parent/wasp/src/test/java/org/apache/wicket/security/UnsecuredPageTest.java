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

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.components.ISecurePage;
import org.apache.wicket.security.components.SecureComponentHelper;
import org.apache.wicket.security.pages.insecure.SecureComponentPage;
import org.apache.wicket.security.pages.insecure.SecureLinkPage;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Test;

/**
 * Test unsecured pages
 * 
 * @author marrink
 */
public class UnsecuredPageTest extends WaspAbstractTestBase
{

	/**
	 * Test accessibility of an unprotected page.
	 */
	@Test
	public void testUnsecuredPage()
	{
		mock.startPage(org.apache.wicket.security.pages.insecure.HomePage.class);
		mock.assertRenderedPage(org.apache.wicket.security.pages.insecure.HomePage.class);
	}

	/**
	 * Test accessibility of an unprotected page with a secure component.
	 */
	@Test
	public void testUnsecuredPage2()
	{
		// change to default behavior of ClassAuthorizationStrategy
		setSecureClass(ISecurePage.class);
		setUp();
		mock.startPage(SecureComponentPage.class);
		mock.assertRenderedPage(getLoginPage());
		FormTester form = mock.newFormTester("signInPanel:signInForm");
		form.setValue("username", "test");
		form.setValue("password", "test");
		form.submit();
		mock.assertRenderedPage(SecureComponentPage.class);
		mock.assertInvisible("secure"); // no render rights on the component
	}

	/**
	 * Test accessibility of an unprotected page with a secure link.
	 */
	@Test
	public void testUnsecuredPage3()
	{
		mock.startPage(SecureLinkPage.class);
		mock.assertRenderedPage(getLoginPage());
		FormTester form = mock.newFormTester("signInPanel:signInForm");
		form.setValue("username", "test");
		form.setValue("password", "test");
		form.submit();
		mock.assertRenderedPage(SecureLinkPage.class);
		// need to arrange enable rights for homepage
		Map<String, WaspAction> authorized = new HashMap<String, WaspAction>();
		authorized.put(SecureComponentHelper.alias(getHomePage()), application.getActionFactory()
			.getAction("access render enable"));
		login(authorized);
		mock.startPage(mock.getLastRenderedPage());
		mock.assertVisible("secure");
		mock.clickLink("secure", false);
		mock.assertRenderedPage(getHomePage());
	}

}