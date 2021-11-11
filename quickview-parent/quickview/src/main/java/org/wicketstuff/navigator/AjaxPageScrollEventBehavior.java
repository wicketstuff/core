/**
 *
 * Copyright 2012 Vineet Semwal
 *
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
package org.wicketstuff.navigator;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.wicketstuff.RepeaterUtil;

/**
 * behavior that can be attached to page ,on page scroll event will be fired if
 * scroll-bar is moved to the the bottom of page.
 *
 * <strong>you need to call {@link AjaxScrollEventBehaviorBase#addItemsForNextPage(org.wicketstuff.IQuickView)} when you
 * implement
 * {@link AjaxPageScrollEventBehavior#onScroll(org.apache.wicket.ajax.AjaxRequestTarget)}</strong>
 *
 *
 * @author Vineet Semwal
 *
 */
public abstract class AjaxPageScrollEventBehavior extends AjaxScrollEventBehaviorBase {

    @Override
    protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
        super.updateAjaxAttributes(attributes);
        attributes.getAjaxCallListeners().add(new PageScrollListener());
    }

    /**
     * @return true if page scroll bar should be automatically provided,
     * it assures document height is greater than window height by setting min-height on body
     * if document height is already greater than window height then it does nothing.
     * by default this method returns false.
     *
     */
    protected boolean forceScrollBarForPage() {
        return false;
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);
        if (forceScrollBarForPage()) {
            response.render(OnDomReadyHeaderItem.forScript(RepeaterUtil.get().showPageScrollBar()));
        }
    }

    public static class PageScrollListener extends AjaxCallListener {

        @Override
        public CharSequence getPrecondition(Component component) {
            super.getPrecondition(component);
            String call = RepeaterUtil.get().isPageScrollBarAtBottom();
            return "return " + call;
        }
    }
}
