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

import org.apache.wicket.Page;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.pages.AccessDeniedPage;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.apache.wicket.request.target.component.BookmarkablePageRequestTarget;
import org.apache.wicket.security.actions.WaspAction;
import org.apache.wicket.security.checks.LinkSecurityCheck;
import org.apache.wicket.security.components.ISecurePage;
import org.apache.wicket.security.components.SecureComponentHelper;
import org.apache.wicket.security.components.markup.html.links.SecurePageLink;
import org.apache.wicket.security.pages.insecure.SecureComponentPage;
import org.apache.wicket.security.pages.secure.HomePage;
import org.apache.wicket.security.pages.secure.PageA;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.TagTester;

/**
 * Test links
 * 
 * @author marrink
 */
public class SecureLinkTest extends WaspAbstractTestBase
{

	/**
	 * Test a link that will allow people to replace panels / containers much like the
	 * {@link ITab} from extensions
	 */
	public void testContainerLink()
	{
		// change to default behavior of ClassAuthorizationStrategy
		setSecureClass(ISecurePage.class);
		setUp();
		// continueto originaldestination does not work if there is no url
		// available, so we need to fake one here(testing only hack)
		mock.setupRequestAndResponse();
		WebRequestCycle cycle = mock.createRequestCycle();
		String url1 =
			cycle.urlFor(new BookmarkablePageRequestTarget(SecureComponentPage.class, null))
				.toString();
		// the expected url is the base test
		mock.getServletRequest().setURL("/WaspAbstractTestBase$1/WaspAbstractTestBase$1/" + url1);
		mock.processRequestCycle();
		mock.assertRenderedPage(getLoginPage());
		FormTester form = mock.newFormTester("signInPanel:signInForm");
		form.setValue("username", "test");
		form.setValue("password", "test");
		form.submit();
		mock.assertRenderedPage(SecureComponentPage.class);
		mock.assertVisible("replaceMe");
		mock.assertInvisible("link"); // no enable action on
		// webmarkupcontainer
		// need to arrange enable rights for webmarkupcontainer
		Map<String, WaspAction> authorized = new HashMap<String, WaspAction>();
		authorized.put(SecureComponentHelper
			.alias(SecureComponentPage.MyReplacementContainer.class), application
			.getActionFactory().getAction("access render enable"));
		login(authorized);
		mock.startPage(mock.getLastRenderedPage());
		mock.assertRenderedPage(SecureComponentPage.class);
		mock.assertVisible("replaceMe");
		mock.assertVisible("link");
		TagTester tag = mock.getTagByWicketId("replaceMe");
		assertEquals("span", tag.getName());
		mock.clickLink("link", false);
		mock.assertRenderedPage(SecureComponentPage.class);
		mock.assertVisible("replaceMe");
		mock.assertInvisible("link");
		tag = mock.getTagByWicketId("replaceMe");
		assertEquals("div", tag.getName());

	}

	/**
	 * Test visibility and clickability of a secure link.
	 */
	public void testLink()
	{
		// step zero, login and you will not see the PageA link (it has no
		// authorization, the default render check
		// will prevent it from rendering
		doLogin();
		mock.assertInvisible("link");
		mock.assertVisible("sorry");
		// step one, show the secure home page without a link to PageA
		Page lastPage = mock.getLastRenderedPage();
		SecurePageLink< ? > link = (SecurePageLink< ? >) lastPage.get("link");
		LinkSecurityCheck linkcheck =
			((LinkSecurityCheck) link.getSecurityCheck()).setUseAlternativeRenderCheck(true);
		// step two, show the secure home page with a not clickable link to
		// PageA (e.g. not a href)
		Map<String, WaspAction> authorized = new HashMap<String, WaspAction>();
		authorized.put(SecureComponentHelper.alias(link), application.getActionFactory().getAction(
			"access render"));
		login(authorized);
		mock.startPage(lastPage);
		mock.assertRenderedPage(getHomePage());
		assertSame(lastPage, mock.getLastRenderedPage());
		mock.assertInvisible("sorry");
		mock.assertVisible("link");
		TagTester tag = mock.getTagByWicketId("link");
		assertNull(tag.getAttribute("href"));
		assertNull(tag.getAttribute("onclick"));
		// step three, show the secure home page with a clickable link to PageA
		authorized.clear();
		authorized.put(SecureComponentHelper.alias(HomePage.class), application.getActionFactory()
			.getAction("access render enable"));
		authorized.put(SecureComponentHelper.alias(PageA.class), application.getActionFactory()
			.getAction("render enable"));
		login(authorized);
		Page page = mock.getLastRenderedPage();
		mock.startPage(page);
		tag = mock.getTagByWicketId("link");
		assertNotNull(tag.getAttribute("href"));
		logoff(authorized);
		authorized.clear();
		// step four, show the secure home page with a clickable link and click
		// the link that is not enabled.
		linkcheck.setUseAlternativeRenderCheck(false);
		authorized.put(SecureComponentHelper.alias(HomePage.class), application.getActionFactory()
			.getAction("access render enable"));
		authorized.put(SecureComponentHelper.alias(PageA.class), application.getActionFactory()
			.getAction("access render"));
		login(authorized);
		mock.setupRequestAndResponse();
		mock.processRequestCycle();
		mock.assertRenderedPage(getHomePage());
		mock.assertInvisible("sorry");
		mock.assertVisible("link");
		mock.clickLink("link", false);
		mock.assertRenderedPage(AccessDeniedPage.class);
		// step five, add enable rights and click the link again.
		authorized.put(SecureComponentHelper.alias(PageA.class), application.getActionFactory()
			.getAction("access render enable"));
		// Note that normally access is implied by render, just not in this
		// simple
		// testcase
		login(authorized);
		mock.startPage(getHomePage());
		mock.clickLink("link", false);
		mock.assertRenderedPage(PageA.class);
	}

}