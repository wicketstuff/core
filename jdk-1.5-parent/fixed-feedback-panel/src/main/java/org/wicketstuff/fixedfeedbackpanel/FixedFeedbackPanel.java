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
package org.wicketstuff.fixedfeedbackpanel;

import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Panel that shows an FeedbackPanel inside an div tag with protected
 * dimensions. If the FeedbackPanel content overflow the div tag dimensions, an
 * icon is presented and the content will to be shown on div onmouseover
 * handler.
 * 
 * @author Pedro Henrique Oliveira dos Santos
 */

public class FixedFeedbackPanel extends Panel implements IHeaderContributor {
    private WebMarkupContainer outerPanel;
    private FeedbackPanel innerPanel;
    private Image expand;
    public static ResourceReference expandImageRef = new ResourceReference(
	    FixedFeedbackPanel.class, "res/window_nofullscreen.png");
    public static final ResourceReference FIXED_PANEL_JS = new ResourceReference(
	    FixedFeedbackPanel.class, "res/fixed-panel.js");

    public FixedFeedbackPanel(String id) {
	this(id, null);
    }

    public FixedFeedbackPanel(String id, Component component) {
	super(id);
	outerPanel = new WebMarkupContainer("outerPanel");
	if (component == null) {
	    innerPanel = new FeedbackPanel("innerPanel");
	} else {
	    innerPanel = new ComponentFeedbackPanel("innerPanel", component);
	}
	expand = new Image("expand", expandImageRef);
	outerPanel.add(expand.setOutputMarkupId(true));
	outerPanel.add(innerPanel.setOutputMarkupId(true));
	add(outerPanel.setOutputMarkupId(true));
    }

    public void renderHead(IHeaderResponse response) {
	response.renderJavascriptReference(FIXED_PANEL_JS);
	response.renderOnDomReadyJavascript("fixPanel( '" + outerPanel.getMarkupId() + "', '"
		+ innerPanel.getMarkupId() + "', '" + expand.getMarkupId() + "' );");
    }
}
