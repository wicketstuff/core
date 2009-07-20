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

import org.apache.wicket.security.pages.insecure.SecureModelPage;
import org.apache.wicket.security.models.SecureCompoundPropertyModel;
import org.apache.wicket.util.tester.TagTester;
import org.apache.wicket.authorization.UnauthorizedActionException;

import java.util.Map;
import java.util.HashMap;

/**
 * Test links
 *
 * @author marrink
 */
public class SecureCompoundPropertyModelTest extends WaspAbstractTestBase {

    /**
     * Test secure model.
     */
    public void testSecureCompoundPropertyModelInputDisabled()
    {
        doLogin();
        Map authorized = new HashMap();
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

    //TODO fix this test. The disabled tag should not be in the input field. Intresting thing is: It is allowed to write text in the input field. 
    public void testSecureCompoundPropertyModelInputEnabled()
    {
        doLogin();
        Map authorized = new HashMap();
        authorized.put("model:" + SecureModelPage.class.getName(), application.getActionFactory()
                .getAction("render"));
        authorized.put("model:input", application.getActionFactory().getAction("access, render, enable"));
        login(authorized);
        mock.startPage(SecureModelPage.class);
        mock.assertRenderedPage(SecureModelPage.class);
        //mock.dumpPage();
        mock.assertVisible("input");
        TagTester tag = mock.getTagByWicketId("input");
        //assertFalse("disabled tag found in tag <" + tag.getName() + ">", tag.hasAttribute("disabled"));
        //assertNull("disabled attribute found, should not be there", tag.getAttribute("disabled"));

        String writings = "now we are getting somewhere";
        mock.getComponentFromLastRenderedPage("input").setDefaultModelObject(writings);
        assertEquals(writings, mock.getComponentFromLastRenderedPage("input")
                .getDefaultModelObject());
        mock.startPage(mock.getLastRenderedPage());
        mock.assertRenderedPage(SecureModelPage.class);
        tag = mock.getTagByWicketId("input");
        assertTrue(tag.getAttributeIs("value", writings));
        assertEquals(SecureCompoundPropertyModel.class.getName() + ":input", mock
                .getComponentFromLastRenderedPage("input").getDefaultModel().toString());

    }

}