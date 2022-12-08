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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.wicket.Page;
import org.apache.wicket.markup.repeater.IItemFactory;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.util.Iterator;

/**
 * @author Vineet Semwal
 */
public class AbstractPagingNavigationStrategyTest {
    private WicketTester tester;

    public WicketTester getTester() {
        return tester;
    }

    @BeforeEach
    public void setup() {
        tester = new WicketTester(new TestApplication());
    }

    public void assertIsPartialUpdatesSupported(IQuickReuseStrategy strategy) {
        assertFalse(strategy.isPartialUpdatesSupported());
    }

    public void assertAddItems(IQuickReuseStrategy strategy) {
        Iterator newModels = Mockito.mock(Iterator.class);
        IItemFactory factory = Mockito.mock(IItemFactory.class);
        int startIndex = 345;
        strategy.addItems(startIndex, factory, newModels);
    }

    public void assertPageCreatedOnReRender(IQuickReuseStrategy strategy) {
        assertTrue(strategy.getPageCreatedOnRender() < 0);
    }

    public class TestApplication extends WebApplication {
        @Override
        public Class<? extends Page> getHomePage() {
            return null;
        }
    }

}
