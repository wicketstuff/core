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

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;

/**
 * a label with onclick ajaxeventbehavior which on click creates new items of quickview
 * @author Vineet Semwal
 */
public class MoreLabel extends Label {

    private ItemsNavigatorBase navigator;

    public ItemsNavigatorBase getNavigator() {
        return navigator;
    }

    public MoreLabel(String id, IModel model, ItemsNavigatorBase navigator) {
        super(id,model);
        Args.notNull(navigator,"navigator");
        this.navigator = navigator;
        setOutputMarkupId(true);
    }


    protected AjaxEventBehavior newOnClickBehavior(){
       return new OnClickBehavior();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(newOnClickBehavior());
       }


    @Override
    protected void onConfigure() {
        super.onConfigure();
         // no need to render for the last page hence check if current page smaller than (pages count -1)
        setVisible(navigator.getRepeater().getCurrentPage() < navigator.getRepeater().getPageCount()-1) ;
    }


    protected void onClick(AjaxRequestTarget target) {
        navigator.onStatefulEvent();
    }

    public class OnClickBehavior extends AjaxEventBehavior{
        public OnClickBehavior() {
           super("click");
        }
            @Override
            protected void onEvent(AjaxRequestTarget target) {
                MoreLabel.this.onClick(target);
            }
    }
    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem.forReference(NavigatorCssReference.get()));
    }

}