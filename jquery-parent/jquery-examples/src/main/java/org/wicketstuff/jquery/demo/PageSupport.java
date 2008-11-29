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
package org.wicketstuff.jquery.demo;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.jquery.ajaxbackbutton.Page4AjaxBackButton;
import org.wicketstuff.jquery.demo.dnd.Page4ClientSideOnly;
import org.wicketstuff.jquery.demo.dnd.Page4MultiGroup;
import org.wicketstuff.jquery.demo.dnd.Page4OneGroup;
import org.wicketstuff.jquery.demo.dnd.Page4SimpleList;
import org.wicketstuff.jquery.demo.ui.Page4Slider;

public class PageSupport extends WebPage {
    private transient Logger logger_;

    public PageSupport() {
        add(new BookmarkablePageLink("clientSideOnly", Page4ClientSideOnly.class));
        add(new BookmarkablePageLink("simpleList", Page4SimpleList.class));
        add(new BookmarkablePageLink("oneGroup", Page4OneGroup.class));
        add(new BookmarkablePageLink("multiGroup", Page4MultiGroup.class));
        add(new BookmarkablePageLink("accordion", Page4Accordion.class));
        add(new BookmarkablePageLink("tabs", Page4Tabs.class));
        add(new BookmarkablePageLink("datePicker", Page4DatePicker.class));
        add(new BookmarkablePageLink("jgrowl", Page4JGrowl.class));
        add(new BookmarkablePageLink("ajaxbackbutton", Page4AjaxBackButton.class));
        add(new BookmarkablePageLink("uislider", Page4Slider.class));

        add(new FeedbackPanel("feedback").setOutputMarkupId(true));
    }

    protected Logger logger() {
        if (logger_ == null) {
            logger_ = LoggerFactory.getLogger(this.getClass());
        }
        return logger_;
    }
}
