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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.wicket.markup.repeater.IItemFactory;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.util.Iterator;

/**
 * @author Vineet Semwal
 */
public class AbstractItemsNavigationStrategyTest {
    private WicketTester tester;

    public WicketTester getTester() {
        return tester;
    }

    @BeforeEach
    public void setup() {
        tester = new WicketTester(createMockApplication());
    }

    public void assertAddItems(IQuickReuseStrategy strategy) {
        IModel model1 = Mockito.mock(IModel.class);
        IModel model2 = Mockito.mock(IModel.class);
        Iterator newModels = Mockito.mock(Iterator.class);
        Mockito.when(newModels.next()).thenReturn(model1).thenReturn(model2);
        Mockito.when(newModels.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
        IItemFactory factory = Mockito.mock(IItemFactory.class);
        final int startIndex = 345;
        final int index2 = startIndex + 1;
        Item item1 = new Item("345", startIndex, model1);
        Mockito.when(factory.newItem(startIndex, model1)).thenReturn(item1);
        Item item2 = new Item("346", index2, model2);
        Mockito.when(factory.newItem(index2, model2)).thenReturn(item2);

        Iterator<Item> actual = strategy.addItems(startIndex, factory, newModels);
        Mockito.verify(factory, Mockito.times(1)).newItem(startIndex, model1);
        Mockito.verify(factory, Mockito.times(1)).newItem(index2, model2);
        assertEquals(actual.next(), item1);
        assertEquals(actual.next(), item2);
        assertFalse(actual.hasNext());

    }

    public void assertIsPartialUpdatesSupported(IQuickReuseStrategy strategy) {
        assertTrue(strategy.isPartialUpdatesSupported());
    }

    private static WebApplication createMockApplication() {
        return new MockApplication();
    }
}
