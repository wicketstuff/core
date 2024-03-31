/*
 * $Id: Calendar.java 5044 2006-03-20 16:46:35 -0800 (Mon, 20 Mar 2006)
 * jonathanlocke $ $Revision: 5159 $ $Date: 2006-03-20 16:46:35 -0800 (Mon, 20
 * Mar 2006) $
 *
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.yui.markup.html.calendar;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.util.template.PackageTextTemplate;
import org.wicketstuff.yui.markup.html.contributor.YuiHeaderContributor;

/**
 * Calendar component based on the Calendar of Yahoo UI Library.
 *
 * @author Eelco Hillenius
 */
public class Calendar extends Panel {
    /**
     * The container/ receiver of the javascript component.
     */
    private final class CalendarElement extends FormComponent<String> {
        private static final long serialVersionUID = 1L;

        /**
         * Construct.
         *
         * @param id
         */
        public CalendarElement(String id) {
            super(id);
            add(new AttributeModifier("id", new IModel<String>() {
                private static final long serialVersionUID = 1L;

                @Override
                public String getObject() {
                    return elementId;
                }
            }));
        }

        @Override
        public void updateModel() {
            Calendar.this.updateModel();
        }
    }

    private static final long serialVersionUID = 1L;

    /**
     * the receiving component.
     */
    private CalendarElement calendarElement;

    /**
     * The DOM id of the element that hosts the javascript component.
     */
    private String elementId;

    /**
     * The JavaScript variable name of the calendar component.
     */
    private String javaScriptId;

    /**
     * Construct.
     *
     * @param id the component id
     */
    public Calendar(String id) {
        super(id);

        Label initialization = new Label("initialization", new IModel() {
            private static final long serialVersionUID = 1L;

            @Override
            public Object getObject() {
                return getJavaScriptComponentInitializationScript();
            }
        });
        initialization.setEscapeModelStrings(false);
        add(initialization);
        add(calendarElement = new CalendarElement("calendarContainer"));
    }

    @Override
	public void renderHead(IHeaderResponse response) {
        response.render(OnLoadHeaderItem.forScript("init" + javaScriptId + "();"));

        YuiHeaderContributor.forModule("calendar").renderHead(response);
        response.render(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(Calendar.class, "calendar.js")));
        response.render(CssHeaderItem.forReference(new CssResourceReference(Calendar.class, "calendar.css")));
    }

    /**
     * TODO implement
     */
    public void updateModel() {
    }

    /**
     * Gets the initilization script for the javascript component.
     *
     * @return the initilization script
     */
    protected String getJavaScriptComponentInitializationScript() {
        CharSequence leftImage = RequestCycle.get().urlFor(new PackageResourceReference(Calendar.class, "callt.gif"), null).toString();
        CharSequence rightImage = RequestCycle.get().urlFor(new PackageResourceReference(Calendar.class, "calrt.gif"), null).toString();

        Map<String, Object> variables = new HashMap<String, Object>(4);
        variables.put("javaScriptId", javaScriptId);
        variables.put("elementId", elementId);
        variables.put("navigationArrowLeft", leftImage);
        variables.put("navigationArrowRight", rightImage);

        PackageTextTemplate template = new PackageTextTemplate(Calendar.class, "init.js");
        template.interpolate(variables);

        return template.getString();
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();

        // initialize lazily
        if (elementId == null) {
            // assign the markup id
            String id = getMarkupId();
            elementId = id + "Element";
            javaScriptId = elementId + "JS";
        }
    }
}
