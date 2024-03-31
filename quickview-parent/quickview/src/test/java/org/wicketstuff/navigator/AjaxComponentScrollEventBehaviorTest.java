/**
 *
 Copyright 2012 Vineet Semwal

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package org.wicketstuff.navigator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.servlet.ServletContext;

import org.wicketstuff.QuickMockApplication;
import org.wicketstuff.RepeaterUtil;
import org.wicketstuff.WicketTest;
import org.apache.wicket.ThreadContext;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.IAjaxCallListener;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.mock.MockWebResponse;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.mock.MockHttpServletRequest;
import org.apache.wicket.protocol.http.mock.MockHttpSession;
import org.apache.wicket.protocol.http.mock.MockServletContext;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.cycle.RequestCycleContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

/**
 *
 * @author Vineet Semwal
 *
 */
public class AjaxComponentScrollEventBehaviorTest {

    @WicketTest
    public void  getPrecondition() {
        WebMarkupContainer parent = new WebMarkupContainer("parent");
        parent.setMarkupId("parent");
        AjaxComponentScrollEventBehavior.ParentScrollListener listener = new AjaxComponentScrollEventBehavior.ParentScrollListener();
        String actual = listener.getPrecondition(parent).toString();
        String expected = "return "+ RepeaterUtil.get().isComponentScrollBarAtBottom(parent);
        assertEquals(actual,expected);
    }

    @WicketTest
    public void updateAjaxAttributes(){
        AjaxComponentScrollEventBehavior behavior = new AjaxComponentScrollEventBehavior() {
            @Override
            protected void onScroll(AjaxRequestTarget target) {
            }
        };
        AjaxRequestAttributes attributes = new AjaxRequestAttributes();
        behavior.updateAjaxAttributes(attributes);
        boolean isAdded = false;
        for(IAjaxCallListener listener:attributes.getAjaxCallListeners()) {
            if (listener instanceof AjaxComponentScrollEventBehavior.ParentScrollListener) {
              isAdded = true;
            }
        }
        assertTrue(isAdded);
    }

    @BeforeAll
    static void createApplication() {
        WebApplication app = new QuickMockApplication();
        app.setName("quickview");
        ServletContext ctx = new MockServletContext(app, null);
        app.setServletContext(ctx);
        ThreadContext.setApplication(app);
        app.initApplication();
        ServletWebRequest req = new ServletWebRequest(new MockHttpServletRequest(app, new MockHttpSession(ctx), ctx), "");
        RequestCycleContext rctx = new RequestCycleContext(req, new MockWebResponse(), app.getRootRequestMapper(), app.getExceptionMapperProvider().get());
        ThreadContext.setRequestCycle(new RequestCycle(rctx));
    }

    @AfterAll
    static void dropApplication() {
        ThreadContext.setApplication(null);
        ThreadContext.setRequestCycle(null);
    }
}
