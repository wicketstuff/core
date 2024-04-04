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

import org.apache.wicket.markup.repeater.IItemFactory;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.model.IModel;

import java.util.Iterator;


/**
 * reuse  strategy that does NOT support addition of items without re-rendering the view.
 * <p>
 * used with {@link org.apache.wicket.markup.html.navigation.paging.PagingNavigator}
 * <p>
 * basically it's a wrapper of {@link ReuseIfModelsEqualStrategy}
 * <p>
 * to read more about it @see ReuseIfModelsEqualStrategy
 *
 * @author Vineet Semwal
 */
public class QuickReuseIfModelsEqualStrategy extends AbstractPagingNavigationStrategy {

    /**
     * @see org.apache.wicket.markup.repeater.IItemReuseStrategy#getItems(org.apache.wicket.markup.repeater.IItemFactory,
     * java.util.Iterator, java.util.Iterator)
     */
    @Override
    public <T> Iterator<Item<T>> getItems(IItemFactory<T> factory, Iterator<IModel<T>> newModels, Iterator<Item<T>> existingItems) {
        return ReuseIfModelsEqualStrategy.getInstance().getItems(factory, newModels, existingItems);
    }

}
