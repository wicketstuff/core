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

import org.apache.wicket.security.components.SecureComponentHelper;
import org.apache.wicket.security.components.markup.html.links.SecurePageLink;
import org.apache.wicket.security.checks.LinkSecurityCheck;
import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.Page;

import java.util.Map;
import java.util.HashMap;

/**
 * Test links
 *
 * @author marrink
 */
public class SecureLinkTest extends WaspAbstractTestBase {

    /**
	 * Test a link that will allow people to replace panels / containers much
	 * like the {@link ITab} from extensions
	 */
	public void testContainerLink()
	{
        //TODO check the meaning of this test, rewrite to test to match
        /*
        doLogin();
		// change to default behavior of ClassAuthorizationStrategy
		setSecureClass(ISecurePage.class);
		setUp();
		// continueto originaldestination does not work if there is no url
		// available, so we need to fake one here(testing only hack)
		mock.setupRequestAndResponse();
		WebRequestCycle cycle = mock.createRequestCycle();
		String url1 = cycle.urlFor(
				new BookmarkablePageRequestTarget(SecureComponentPage.class, null)).toString();
		mock.getServletRequest().setURL("/GeneralTest$1/GeneralTest$1/" + url1);
		mock.assertRenderedPage(LoginPage.class);
		FormTester form = mock.newFormTester("signInPanel:signInForm");
		form.setValue("username", "test");
		form.setValue("password", "test");
		form.submit();
		mock.assertRenderedPage(SecureComponentPage.class);
		mock.assertVisible("replaceMe");
		mock.assertInvisible("link"); // no enable action on
		// webmarkupcontainer
		// need to arrange enable rights for webmarkupcontainer
		*/
    }

    public void testContainerLink2() {
        // TODO check the meaning of this class, rewrite the test to match
        /*
        doLogin();
		Map authorized = new HashMap();
		authorized.put(SecureComponentPage.MyReplacementContainer.class, application
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
		*/

	}

	/**
	 * Test visibility and clickability of a secure link.
	 */
	public void testLink()
	{
		doLogin();
		mock.assertInvisible("link");
		mock.assertVisible("sorry");

		Page lastPage = mock.getLastRenderedPage();
		SecurePageLink link = (SecurePageLink)lastPage.get("link");
		LinkSecurityCheck linkcheck = ((LinkSecurityCheck)link.getSecurityCheck())
				.setUseAlternativeRenderCheck(true);
		// need to fake inherit for the link to show up.
		Map authorized = new HashMap();
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
}

    public void testLink2() {
        //TODO check the meaning of this test, rewrite to match
        /*
        doLogin();
		Map authorized = new HashMap();
		authorized.put(PageA.class, application.getActionFactory()
				.getAction("access, render, enable"));
		login(authorized);
		mock.startPage(mock.getLastRenderedPage());
        mock.dumpPage();
		TagTester tag = mock.getTagByWicketId("link");
		//TODO this assert gives a NULL !
        assertNotNull(tag.getAttribute("href"));
		logoff(authorized);
		authorized.clear();
        Page lastPage = mock.getLastRenderedPage();
        SecurePageLink link = (SecurePageLink)lastPage.get("link");

        LinkSecurityCheck linkcheck = ((LinkSecurityCheck)link.getSecurityCheck())
                .setUseAlternativeRenderCheck(false);
		authorized.put(PageA.class, application.getActionFactory().getAction("render"));
		login(authorized);
		mock.setupRequestAndResponse();
		mock.processRequestCycle();
		mock.assertRenderedPage(getHomePage());
		mock.assertInvisible("sorry");
		mock.assertVisible("link");
		// TODO the accessdenied page is not returned (It could be a behaviour change of this release ???), an UnauthorizedActionException is thrown ....
        mock.clickLink("link", false);
		mock.assertRenderedPage(application.getApplicationSettings().getAccessDeniedPage());
		authorized.put(PageA.class, application.getActionFactory()
				.getAction("access render enable"));
		// Note that normally access is implied by render, just not in this
		// simple
		// testcase
		login(authorized);
		mock.startPage(getHomePage());
		mock.clickLink("link", false);
		mock.assertRenderedPage(PageA.class);
        */
	}

}