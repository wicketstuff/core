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

import java.util.Iterator;

/**
 *   abstract reuse strategy that does NOT support addition of items to the view without the need to re-render .
 *   this strategy is typically used only for paging foreg. when QuickView is used with {@link org.apache.wicket.markup.html.navigation.paging.PagingNavigator}
 *
 *   also to read more @see IQuickReuseStrategy
 *
 *
 * @author Vineet Semwal
 */
public abstract class AbstractPagingNavigationStrategy implements IQuickReuseStrategy {

    @Override
    public <T> Iterator<Item<T>> addItems(int startIndex, IItemFactory<T> factory, Iterator<IModel<T>> newModels) {
        throw new IRepeaterUtil.ReuseStrategyNotSupportedException("adding items dynamically for partial updates is not supported by this strategy");
    }


    /**
     * false means partial updates not supported
     *
     * @return false
     */
    @Override
    public boolean isPartialUpdatesSupported() {
        return false;
    }

    @Override
    public long getPageCreatedOnRender() {
        return -1;
    }
}
