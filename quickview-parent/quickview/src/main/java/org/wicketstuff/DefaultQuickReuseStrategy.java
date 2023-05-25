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

import org.apache.wicket.markup.repeater.DefaultItemReuseStrategy;
import org.apache.wicket.markup.repeater.IItemFactory;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

import java.util.Iterator;

/**
 * default reuse strategy used by QuickView ,this does NOT support addition of new items without re-rendering
 * the view .it's  used with {@link org.apache.wicket.markup.html.navigation.paging.PagingNavigator}
 * or {@link org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator}
 *
 * basically it's a wrapper of DefaultItemReuseStrategy
 *
 *  to read more about it see {@link DefaultItemReuseStrategy}
 *
 * @author Vineet Semwal
 */
public class DefaultQuickReuseStrategy extends AbstractPagingNavigationStrategy {

    /**
     * Returns an iterator over items that will be added to the view. The iterator needs to return
     * all the items because the old ones are removed prior to the new ones added.
     *
     * @param <T>
     *            type of Item
     *
     * @param factory
     *            implementation of IItemFactory
     * @param newModels
     *            iterator over models for items
     * @param existingItems
     *            iterator over child items
     * @return iterator over items that will be added after all the old items are moved.
     */
    @Override
    public <T> Iterator<Item<T>> getItems(IItemFactory<T> factory, Iterator<IModel<T>> newModels, Iterator<Item<T>> existingItems) {
        return DefaultItemReuseStrategy.getInstance().getItems(factory, newModels, existingItems);
    }

}
