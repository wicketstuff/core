/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wicketstuff.stateless;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.page.XmlPartialPageUpdate;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wicketstuff.stateless.components.StatelessAjaxFallbackLink;

/**
 * @author jfk
 * 
 */
public class StatelessAjaxComponentsBehaviorsTest {
    protected WicketTester tester;

    @Before
    public void setUp() {
        tester = new WicketTester(new WicketApplication());

    }

    @After
    public void teardown() {
        final boolean dump = Boolean.getBoolean("dumpHtml");

        if (dump) {
            tester.dumpPage();
        }
        //things must stay stateless
        assertTrue(Session.get().isTemporary());
    }

    /**
     * Test method for {@link org.wicketstuff.stateless.components.StatelessAjaxFallbackLink#getStatelessHint()}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetStatelessHint() {
        tester.startPage(HomePage.class);

        final HomePage page = (HomePage) tester.getLastRenderedPage();
        final StatelessAjaxFallbackLink<Object> l1 = (StatelessAjaxFallbackLink<Object>) page
                .get(2);

        assertTrue(l1.isStateless());

        l1.onClick();

        final List<? extends Behavior> behaviors = l1.getBehaviors();
        final AjaxEventBehavior behavior = (AjaxEventBehavior) behaviors.get(0);

        behavior.onRequest();
    }
    
    @Test
	public void testSubmitForm() throws Exception {
    	tester.startPage(HomePage.class);
    	
    	FormTester formTester = tester.newFormTester("inputForm");
    	formTester.setValue("name", "myname");
    	formTester.setValue("surname", "mysurname");
    	
    	tester.executeAjaxEvent("inputForm:submit", "click");
    	
    	String response = tester.getLastResponseAsString();
    	
    	boolean isAjaxResponse = response.contains(XmlPartialPageUpdate.START_ROOT_ELEMENT)
    		&& response.contains(XmlPartialPageUpdate.END_ROOT_ELEMENT);
    	
    	assertTrue(isAjaxResponse);
    	
    	boolean formAjaxSubmit = response.contains(HomePage.FORM_SUBMIT) &&
    		response.contains(HomePage.AJAX_SUBMIT);
    	
    	assertTrue(formAjaxSubmit);
	}
    
    /**
     * Test method for {@link StatelessAjaxFallbackLink#getStatelessHint()}.
     */
    @Test
    public void testHomePage() {
        // start and render the test page
        tester.startPage(HomePage.class);

        // assert rendered page class
        tester.assertRenderedPage(HomePage.class);

        tester.clickLink("more");
    }
}
