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

import org.apache.wicket.authorization.UnauthorizedActionException;
import org.apache.wicket.util.tester.TagTester;
import org.junit.Test;
import org.wicketstuff.security.actions.WaspAction;
import org.wicketstuff.security.models.SecureCompoundPropertyModel;
import org.wicketstuff.security.pages.insecure.SecureModelPage;

/**
 * Test links
 * 
 * @author marrink
 */
public class SecureCompoundPropertyModelTest extends WaspAbstractTestBase
{

	/**
	 * Test secure model.
	 */
	@Test
	public void testSecureCompoundPropertyModelForRender()
	{
		doLogin();
		Map<String, WaspAction> authorized = new HashMap<String, WaspAction>();
		authorized.put("model:" + SecureModelPage.class.getName(), application.getActionFactory()
			.getAction("render"));
		login(authorized);
		mock.startPage(SecureModelPage.class);
		mock.assertRenderedPage(SecureModelPage.class);
		mock.assertContains("<body>");
		mock.assertInvisible("label");
		mock.assertInvisible("input");
		authorized.put("model:label", application.getActionFactory().getAction("render"));
		login(authorized);
		mock.startPage(mock.getLastRenderedPage());
		mock.assertVisible("label");
		mock.assertInvisible("input");
		authorized.put("model:input", application.getActionFactory().getAction("render"));
		login(authorized);
		mock.startPage(mock.getLastRenderedPage());
		mock.assertVisible("label");
		mock.assertVisible("input");
		TagTester tag = mock.getTagByWicketId("input");
		assertTrue(tag.hasAttribute("disabled"));
		try
		{
			mock.getComponentFromLastRenderedPage("input").setDefaultModelObject(
				"writing in textfield");
			fail("should not be able to write in textfield");
		}
		catch (UnauthorizedActionException e)
		{
		}
	}

	@Test
	public void testSecureCompoundPropertyModelForEnable()
	{
		doLogin();
		Map<String, WaspAction> authorized = new HashMap<String, WaspAction>();
		// NOTE: The page as such is not secure (and thus not protected, the
		// model in the page is, so that needs authorization
		// NOTE: If you want to enable the input field, you need to enable the
		// parent. When the parent is disabled, it will
		// disable the textfield even when you put it on 'enable' like here
		// below.
		authorized.put("model:" + SecureModelPage.class.getName(), application.getActionFactory()
			.getAction("render enable"));
		authorized.put("model:label", application.getActionFactory().getAction("render"));
		authorized.put("model:input", application.getActionFactory().getAction("render enable"));
		login(authorized);
		mock.startPage(SecureModelPage.class);
		TagTester tag = mock.getTagByWicketId("input");
		assertNotNull("input tag should be available", tag);
		assertFalse(tag.hasAttribute("disabled"));
		String writings = "now we are getting somewhere";

		mock.getComponentFromLastRenderedPage("input").setDefaultModelObject(writings);
		assertEquals(writings, mock.getComponentFromLastRenderedPage("input")
			.getDefaultModelObject());
		mock.startPage(mock.getLastRenderedPage());
		mock.assertRenderedPage(SecureModelPage.class);
		tag = mock.getTagByWicketId("input");
		assertTrue(tag.getAttributeIs("value", writings));
		assertEquals(SecureCompoundPropertyModel.class.getName() + ":input",
			mock.getComponentFromLastRenderedPage("input").getDefaultModel().toString());

	}

}
