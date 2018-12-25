/**
 * Copyright 2012 Vineet Semwal
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff;

import org.wicketstuff.navigator.AjaxItemsNavigator;
import org.wicketstuff.navigator.ItemsNavigatorBase;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.IMarkupSourcingStrategy;
import org.apache.wicket.markup.html.panel.PanelMarkupSourcingStrategy;

/**
 * @author Vineet Semwal
 */
public abstract class TestQuickViewContainerWithBoundaries extends WebMarkupContainer {

    public static final String quickViewId = "quickview", ajaxLinkId = "link", navigatorId = "navigator",
            TAG_NAME = "div";
    private AbstractLink link;
    private ItemsNavigatorBase navigator;
    private QuickViewBase view;

    public ItemsNavigatorBase getNavigator() {
        return navigator;
    }


    public TestQuickViewContainerWithBoundaries(String id) {
        super(id);
    }

    public AbstractLink getLink() {
        return link;
    }

    abstract public QuickViewBase newView();

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(view = newView());
        add(navigator = newNavigator());
        add(link = newLink());

    }

    public AbstractLink newLink() {
        return new Link<Void>("link") {
            @Override
            public void onClick() {
            }
        };
    }

    public ItemsNavigatorBase newNavigator() {
        AjaxItemsNavigator navigator = new AjaxItemsNavigator(navigatorId, view);
        return navigator;
    }

    @Override
    protected IMarkupSourcingStrategy newMarkupSourcingStrategy() {
        return new PanelMarkupSourcingStrategy(false);
    }

}
