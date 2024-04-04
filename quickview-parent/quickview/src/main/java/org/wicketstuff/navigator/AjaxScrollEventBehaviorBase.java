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

import java.util.List;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.repeater.Item;
import org.wicketstuff.IQuickView;

/**
 *  base ajax scroll event behavior which are extended by {@link AjaxPageScrollEventBehavior} and {@link AjaxComponentScrollEventBehavior}
 *
 *@author Vineet Semwal
 * .
 */
public abstract class AjaxScrollEventBehaviorBase extends AjaxEventBehavior{
    public AjaxScrollEventBehaviorBase(){
        super("scroll");
    }

    @Override
    protected final void onEvent(AjaxRequestTarget target) {
        onScroll(target);
    }

    /**
     * Listener method for the ajax scroll event
     * when you implement this method call {@link AjaxScrollEventBehaviorBase#addItemsForNextPage(org.wicketstuff.IQuickView)} to create items for next page/request
     *
     * @param target
     *      the current request handler
     */
    protected abstract void onScroll(AjaxRequestTarget target);

    public List<Item> addItemsForNextPage(IQuickView quickView) {
        return quickView.addItemsForNextPage();
    }

}
