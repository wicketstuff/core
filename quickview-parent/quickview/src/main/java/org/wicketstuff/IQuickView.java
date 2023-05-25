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

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.repeater.Item;

import java.util.List;

/**
 * QuickView's interface
 *
 * @author Vineet Semwal
 */

public interface IQuickView<T> extends IPageable {
    IQuickReuseStrategy getReuseStrategy();

    MarkupContainer getParent();

    /**
     * create and draw children for the provided page ,number of
     * children created are smaller than equal to getItemsPerRequest()
     *
     * @param page
     * @return list of components created
     */
    List<Item<T>> addItemsForPage(final long page);

    /**
     * adds items/rows for next page and also sets the next page ,this method can called by any sequential items/rows navigator
     * for example {@link org.wicketstuff.navigator.ItemsNavigatorBase} calls this method onClick,
     * ItemsNavigatorBase is the base of {@link org.wicketstuff.navigator.AjaxItemsNavigator}.
     */
    List<Item<T>> addItemsForNextPage();

}

