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
 * this strategy supports addition of new items without the need to re-render the view  .
 * on re-render all items are removed and items for first page are created again
 * <p>
 * used with  {@link org.wicketstuff.navigator.AjaxItemsNavigator}
 * or {@link org.wicketstuff.navigator.AjaxScrollEventBehaviorBase}
 * <p/>
 * 1)all children are removed and children of first page are created again on re-render <br/>
 * 2) new children for next page is created in  {@link org.wicketstuff.QuickViewBase#addItemsForNextPage()}
 * <p/>
 * <p>
 * earlier it was used as Reuse.ITEMSNAVIGATION
 *
 * @author Vineet Semwal
 */
public class ItemsNavigationStrategy extends AbstractItemsNavigationStrategy {

    /**
     * Returns an iterator over items that will be added to the view. The iterator needs to return
     * all the items because the old ones are removed prior to the new ones added.
     *
     * @param <T>           type of Item
     * @param factory       implementation of IItemFactory
     * @param newModels     iterator over models for items
     * @param existingItems iterator over child items
     * @return iterator over items that will be added after all the old items are moved.
     */
    @Override
    public <T> Iterator<Item<T>> getItems(IItemFactory<T> factory, Iterator<IModel<T>> newModels, Iterator<Item<T>> existingItems) {
        return DefaultItemReuseStrategy.getInstance().getItems(factory, newModels, existingItems);
    }

    @Override
    public long getPageCreatedOnRender() {
        return 0;
    }
}
