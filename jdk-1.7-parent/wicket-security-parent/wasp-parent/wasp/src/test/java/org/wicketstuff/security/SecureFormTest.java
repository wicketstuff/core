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
package org.wicketstuff.security;


import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.markup.html.pages.AccessDeniedPage;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.TagTester;
import org.junit.Test;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.components.SecureComponentHelper;
import org.wicketstuff.security.components.markup.html.form.SecureForm;
import org.wicketstuff.security.pages.secure.FormPage;

/**
 * Test links
 * 
 * @author marrink
 */
public class SecureFormTest extends WaspAbstractTestBase
{

	/**
	 * Test secure form component. Form is not available as a whole = invisible.
	 */
	@Test
	public void testSecureFormInvisible()
	{
		doLogin();
		mock.startPage(FormPage.class);
		mock.assertRenderedPage(FormPage.class);
		mock.assertInvisible("form");
		mock.processRequest();
	}

	/**
	 * Test secure form component. Form is visible but it is not possible to submit or enter new
	 * values.
	 */
	@Test
	public void testSecureFormVisibleDisabled()
	{
		doLogin();
		Map<String, WaspAction> authorized = new HashMap<String, WaspAction>();
		authorized.put(SecureComponentHelper.alias(SecureForm.class),
			application.getActionFactory().getAction("access render"));
		login(authorized);
		mock.startPage(FormPage.class);
		mock.assertRenderedPage(FormPage.class);
		mock.assertVisible("form");
		TagTester tag = mock.getTagByWicketId("form");
		assertEquals("div", tag.getName());
		tag = mock.getTagByWicketId("text");
		assertEquals("disabled", tag.getAttribute("disabled"));
		tag = mock.getTagByWicketId("area");
		assertEquals("disabled", tag.getAttribute("disabled"));
		tag = mock.getTagByWicketId("button");
		assertEquals("disabled", tag.getAttribute("disabled"));
		// fake form submit since the tag can not

		FormTester form = mock.newFormTester("form");
		form.setValue("text", "not allowed");
		form.setValue("area", "also not allowed");
		form.submit();
		mock.assertRenderedPage(AccessDeniedPage.class);

	}

	/**
	 * Test secure form component. Form is available and able to process values.
	 */
	@Test
	public void testSecureFormVisibleEnabled()
	{
		doLogin();
		Map<String, WaspAction> authorized = new HashMap<String, WaspAction>();
		authorized.put(SecureComponentHelper.alias(SecureForm.class),
			application.getActionFactory().getAction("access render enable"));
		authorized.put(SecureComponentHelper.alias(FormPage.class), application.getActionFactory()
			.getAction("access render enable"));
		login(authorized);
		mock.startPage(FormPage.class);
		mock.assertRenderedPage(FormPage.class);
		mock.dumpPage();
		mock.assertVisible("form");
		TagTester tag = mock.getTagByWicketId("text");
		assertNull(tag.getAttribute("disabled"));
		tag = mock.getTagByWicketId("area");
		assertNull(tag.getAttribute("disabled"));
		tag = mock.getTagByWicketId("button");
		assertNull(tag.getAttribute("disabled"));
		FormTester form = mock.newFormTester("form");
		form = mock.newFormTester("form");
		form.setValue("text", "allowed");
		form.setValue("area", "also allowed");
		form.submit();
		mock.assertRenderedPage(FormPage.class);
		assertEquals("allowed", mock.getComponentFromLastRenderedPage("form:text")
			.getDefaultModelObject());
		assertEquals("also allowed", mock.getComponentFromLastRenderedPage("form:area")
			.getDefaultModelObject());
	}

}
