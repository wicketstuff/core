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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.wicket.markup.repeater.IItemFactory;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Vineet Semwal
 */
public class ItemsNavigationStrategyTest extends AbstractItemsNavigationStrategyTest {

    @WicketTest
    public void addItems_1() {
        super.assertAddItems(new ItemsNavigationStrategy());
    }

    @WicketTest
    public void isPaging_1() {
        super.assertIsPartialUpdatesSupported(new ItemsNavigationStrategy());
    }

    @WicketTest
    public void pageCreatedOnReRender_1() {
        assertTrue(new ItemsNavigationStrategy().getPageCreatedOnRender() == 0);
    }

    @WicketTest
    public void getItems_1() {
        IQuickReuseStrategy strategy = new ItemsNavigationStrategy();
        List<Integer> list = new ArrayList<Integer>();
        list.add(45);
        list.add(76);

        IItemFactory factory = Mockito.mock(IItemFactory.class);
        final int index = 0;
        final int index2 = 1;
        IModel<Integer> model1 = new Model<Integer>(list.get(0));
        IModel<Integer> model2 = new Model<Integer>(list.get(1));
        Item item1 = new Item("0", 0, model1);
        Mockito.when(factory.newItem(0, model1)).thenReturn(item1);
        Item item2 = new Item("1", index2, model2);
        Mockito.when(factory.newItem(index2, model2)).thenReturn(item2);
        List<IModel<Integer>> newModels = new ArrayList<IModel<Integer>>();
        newModels.add(model1);
        newModels.add(model2);

        Iterator<Item<Integer>> actual = strategy.getItems(factory, newModels.iterator(), null);
        assertEquals(actual.next(), item1);
        assertEquals(actual.next(), item2);
    }
}
