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
public abstract class TestQuickViewContainer extends WebMarkupContainer {

    public static final String quickViewId = "quickview", parentId = "parent", ajaxLinkId = "link", navigatorId = "navigator",
            TAG_NAME = "div";
    private AbstractLink link;
    private QuickViewParent parent;
    private ItemsNavigatorBase navigator;

    public ItemsNavigatorBase getNavigator() {
        return navigator;
    }

    public QuickViewParent getQuickViewParent() {
        return parent;
    }

    public TestQuickViewContainer(String id) {
        super(id);
    }

    public AbstractLink getLink() {
        return link;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(parent = newParent());
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

    public abstract QuickViewParent newParent();

    public ItemsNavigatorBase newNavigator() {
        AjaxItemsNavigator navigator = new AjaxItemsNavigator(navigatorId, parent.getChild());
        return navigator;
    }

    @Override
    protected IMarkupSourcingStrategy newMarkupSourcingStrategy() {
        return new PanelMarkupSourcingStrategy(false);
    }

                    /*
    @Override
    public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> containerClass)
    {
        return new StringResourceStream("<wicket:panel> " +
                "<div wicket:id=\""+ getQuickViewParent().getId()+ "\">" +

                "<div wicket:id=\""+getQuickViewParent().getChild().getId()+"\"> </div>" +

                "</div>"  +

                " <div wicket:id=\""+ getNavigator().getId()+"\"></div>" +

                "<a wicket:id= \""+ getLink().getId()  +"\" > </a>"+



                "</wicket:panel>"

        );
    }             */

}
