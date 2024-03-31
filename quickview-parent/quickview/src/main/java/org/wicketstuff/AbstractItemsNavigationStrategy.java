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
import org.apache.wicket.model.IModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *   abstract reuse strategy that supports addition of items to the view without the need to re-render  .
 *
 *   also to read more @see IQuickReuseStrategy
 *
 *
 * @author Vineet Semwal
 */
public abstract class AbstractItemsNavigationStrategy implements IQuickReuseStrategy {

    /**
     * @inheritDoc
     */
    @Override
    public <T> Iterator<Item<T>> addItems(int startIndex, IItemFactory<T> factory, Iterator<IModel<T>> newModels) {
        int itemIndex = startIndex;
        List<Item<T>> components = new ArrayList<Item<T>>();
        for (; newModels.hasNext(); itemIndex++) {
            IModel<T> newModel = newModels.next();
            Item<T> item = factory.newItem(itemIndex, newModel);
            components.add(item);
        }

        return components.iterator();
    }

    /**
     * @return true means repeater supports partial updates without the need to re-render whole repeater
     */
    @Override
    public boolean isPartialUpdatesSupported() {
        return true;
    }


}
