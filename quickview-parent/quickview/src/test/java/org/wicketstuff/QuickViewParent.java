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

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;

import java.util.Iterator;

/**
 * quickview's safe parent for tests
 */

public class QuickViewParent extends WebMarkupContainer {
    public QuickViewParent(String id) {
        super(id);

    }

    public QuickViewBase getChild() {
        Iterator<Component> it = iterator();
        Component child = it.next();
        if (child instanceof IQuickView) {
            QuickViewBase quickView = (QuickViewBase) child;
            return quickView;
        }
        throw new RuntimeException("quickview not found as first child");
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
        getChild();
    }
}

