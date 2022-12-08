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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.IItemFactory;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.util.*;

/**
 * @author Vineet Semwal
 */
public class QuickViewTest {
    Component updateBefore;

    WicketTester tester;

    @BeforeEach
    void setup() {
        tester = new WicketTester(createMockApplication());
    }

    private static WebApplication createMockApplication() {
        return new MockApplication();
    }

    /**
     * check when everything is passed from constructor
     */
    @WicketTest
    public void constructor_1() {
        int oneBlock = 2;
        final String repeaterId = "repeater";
        IDataProvider<TestObj> provider = mockProvider(oneBlock);
        IQuickReuseStrategy reuse = Mockito.mock(IQuickReuseStrategy.class);
        QuickView<TestObj> repeater = new QuickView<TestObj>(repeaterId, provider, reuse, oneBlock) {

            @Override
            protected void populate(Item<TestObj> item) {
            }

        };
        repeater.setMarkupId("con");
        assertEquals(repeater.getReuseStrategy(), reuse);
        assertEquals(repeater.getDataProvider(), provider);
        assertEquals(repeater.getItemsPerRequest(), oneBlock);
    }

    /**
     * reuse  is not passed from constructor
     */
    @WicketTest
    public void constructor_2() {
        int oneBlock = 12;
        final String id = "connn", repeaterId = "repeat";
        IDataProvider<TestObj> provider = mockProvider(oneBlock);
        QuickView<TestObj> repeater = new QuickView<TestObj>(repeaterId, provider, oneBlock) {
            @Override
            public void populate(Item<TestObj> item) {
            }
        };
        repeater.setMarkupId("con");
        assertTrue(repeater.getReuseStrategy() instanceof DefaultQuickReuseStrategy);
        assertEquals(repeater.getDataProvider(), provider);
        assertEquals(repeater.getItemsPerRequest(), oneBlock);

    }


    /**
     * reuse is set
     */

    @WicketTest
    public void constructor_3() {
        final String id = "connn", repeaterId = "repeat";
        IDataProvider<TestObj> provider = mockProvider(10);
        IQuickReuseStrategy reuse = Mockito.mock(IQuickReuseStrategy.class);
        QuickView<TestObj> repeater = new QuickView<TestObj>(repeaterId, provider, reuse) {

            @Override
            public void populate(Item<TestObj> item) {
            }
        };
        repeater.setMarkupId("con");
        assertEquals(repeater.getReuseStrategy(), reuse);
        assertEquals(repeater.getDataProvider(), provider);
        assertEquals(repeater.getItemsPerRequest(), Integer.MAX_VALUE);

    }


    @WicketTest
    public void constructor_4() {
        final String id = "connn", repeaterId = "repeat";
        IDataProvider<TestObj> provider = mockProvider(10);
        QuickView<TestObj> repeater = new QuickView<TestObj>(repeaterId, provider) {

            @Override
            public void populate(Item<TestObj> item) {
            }
        };

        repeater.setMarkupId("con");
        assertTrue(repeater.getReuseStrategy() instanceof DefaultQuickReuseStrategy);
        assertEquals(repeater.getDataProvider(), provider);
        assertEquals(repeater.getItemsPerRequest(), Integer.MAX_VALUE);

    }

    @UtilTest
    public void constructor_6() {
        assertThrows(IllegalArgumentException.class, () -> {
            IDataProvider data = Mockito.mock(IDataProvider.class);
            QuickView quickView = new QuickView("id", data, null) {
                @Override
                protected void populate(Item item) {
                }
            };
        });
    }


    @WicketTest
    public void setReuseStrategy_1() {
        IQuickReuseStrategy strategy = Mockito.mock(IQuickReuseStrategy.class);
        int oneBlock = 12;
        final String id = "connn", repeaterId = "repeat";
        IDataProvider<TestObj> provider = mockProvider(oneBlock);
        QuickView<TestObj> repeater = new QuickView<TestObj>(repeaterId, provider, oneBlock) {

            @Override
            public void populate(Item<TestObj> item) {
            }
        };

        repeater.setReuseStrategy(strategy);
        assertEquals(repeater.getReuseStrategy(), strategy);
    }


    /**
     * add one component ,synchronizer NOT null
     * request handler is AjaxRequestTarget
     */
    @WicketTest
    public void add_1() {
        final int itemsPerRequest = 2;
        final WebMarkupContainer parent = Mockito.mock(WebMarkupContainer.class);
        IDataProvider dataProvider = Mockito.mock(IDataProvider.class);
        final IRepeaterUtil util = mockRepeaterUtil();
        final Synchronizer synchronizer = Mockito.mock(Synchronizer.class);
        final Item c = Mockito.mock(Item.class);
        AjaxRequestTarget target = Mockito.mock(AjaxRequestTarget.class);
        QuickView<TestObj> quickView = new QuickView<TestObj>("repeater", dataProvider, itemsPerRequest) {

            public void populate(Item<TestObj> item) {
            }

        };
        QuickView<TestObj> spy = Mockito.spy(quickView);
        Mockito.doNothing().when(spy)._contributeAddAtEndScripts(c);
        Mockito.doReturn(spy).when(spy).simpleAdd(c);
        Mockito.doReturn(synchronizer).when(spy).getSynchronizer();
        Mockito.doReturn(target).when(spy).getAjaxRequestTarget();
        Mockito.doReturn(true).when(spy).isAjax();
        Mockito.doReturn(parent).when(spy)._getParent();
        Mockito.when(synchronizer.isRequestHandlerAjaxRequestTarget()).thenReturn(true);
        spy.add(c);
        Mockito.verify(spy).simpleAdd(c);
        Mockito.verify(synchronizer).add(c);
        Mockito.verify(spy)._contributeAddAtEndScripts(c);
        Mockito.verify(synchronizer, Mockito.never()).submit();
    }


    /**
     * add one component ,synchronizer NOT null
     * request handler is NOT AjaxRequestTarget based
     */
    @WicketTest
    public void add_2() {
        final int itemsPerRequest = 2;
        final WebMarkupContainer parent = Mockito.mock(WebMarkupContainer.class);
        IDataProvider dataProvider = Mockito.mock(IDataProvider.class);
        final IRepeaterUtil util = mockRepeaterUtil();
        final Synchronizer synchronizer = Mockito.mock(Synchronizer.class);
        final Item c = Mockito.mock(Item.class);
        QuickView<TestObj> quickView = new QuickView<TestObj>("repeater", dataProvider, itemsPerRequest) {
            public void populate(Item<TestObj> item) {
            }
        };
        QuickView<TestObj> spy = Mockito.spy(quickView);
        Mockito.doNothing().when(spy)._contributeAddAtEndScripts(c);
        Mockito.doReturn(spy).when(spy).simpleAdd(c);
        Mockito.doReturn(synchronizer).when(spy).getSynchronizer();
        Mockito.doReturn(null).when(spy).getAjaxRequestTarget();
        Mockito.doReturn(true).when(spy).isAjax();
        Mockito.doReturn(parent).when(spy)._getParent();
        Mockito.when(synchronizer.isRequestHandlerAjaxRequestTarget()).thenReturn(false);
        spy.add(c);
        Mockito.verify(spy).simpleAdd(c);
        Mockito.verify(synchronizer).add(c);
        Mockito.verify(spy)._contributeAddAtEndScripts(c);
        Mockito.verify(synchronizer).submit();
    }


    /**
     * add one component ,synchronizer is null
     */
    @WicketTest
    public void add_3() {
        int oneBlock = 2;
        final WebMarkupContainer parent = Mockito.mock(WebMarkupContainer.class);
        IDataProvider dataProvider = Mockito.mock(IDataProvider.class);
        final Item c = Mockito.mock(Item.class);
        QuickView<TestObj> quickView = new QuickView<TestObj>("repeater", dataProvider, oneBlock) {

            public void populate(Item<TestObj> item) {
            }

        };
        QuickView<TestObj> spy = Mockito.spy(quickView);
        Mockito.doReturn(false).when(spy).isAjax();
        Mockito.doReturn(parent).when(spy)._getParent();
        Mockito.doReturn(null).when(spy).getSynchronizer();
        spy.add(c);
        Mockito.verify(spy).simpleAdd(c);
    }

    /**
     * addNewItems(object1,object2)
     */
    @WicketTest
    public void addNewItems_1() {
        int oneBlock = 2;
        final WebMarkupContainer parent = Mockito.mock(WebMarkupContainer.class);
        IDataProvider dataProvider = Mockito.mock(IDataProvider.class);
        final IRepeaterUtil util = mockRepeaterUtil();
        TestObj obj1 = Mockito.mock(TestObj.class);
        TestObj obj2 = Mockito.mock(TestObj.class);
        final Iterator<Item<TestObj>> newItems = Mockito.mock(Iterator.class);
        final int size = 12;
        final int index1 = size;
        final int index2 = size + 1;
        final Item<TestObj> item1 = new Item<TestObj>("123", index1, new Model<TestObj>(obj1));
        final Item<TestObj> item2 = new Item<TestObj>("124", index2, new Model<TestObj>(obj2));
        Mockito.when(newItems.next()).thenReturn(item1).thenReturn(item2);
        Mockito.when(newItems.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);

        QuickView<TestObj> arc = new QuickView<TestObj>("repeater", dataProvider, oneBlock) {

            public void populate(Item<TestObj> item) {
            }

            public MarkupContainer _getParent() {
                return parent;
            }

            public MarkupContainer simpleAdd(Component... c) {
                return this;
            }


            @Override
            public IRepeaterUtil getRepeaterUtil() {
                return util;
            }


            @Override
            public Item buildItem(int index, TestObj object) {
                return null;
            }

            @Override
            public MarkupContainer add(Component... components) {
                return this;
            }

            @Override
            public int size() {
                return size;
            }
        };

        QuickView<TestObj> sparc = Mockito.spy(arc);
        Mockito.when(sparc.buildItem(index1, obj1)).thenReturn(item1);
        Mockito.when(sparc.buildItem(index2, obj2)).thenReturn(item2);
        sparc.addNewItems(obj1, obj2);
        Mockito.verify(sparc, Mockito.times(1)).buildItem(index1, obj1);
        Mockito.verify(sparc, Mockito.times(1)).buildItem(index2, obj2);
        Mockito.verify(sparc, Mockito.times(1)).add(item1);
        Mockito.verify(sparc, Mockito.times(1)).add(item2);

    }


    /**
     * addNewItemsAtStart(T ... object)
     */
    @WicketTest
    public void addNewItemsAtStart_1() {
        int oneBlock = 2;
        final WebMarkupContainer parent = Mockito.mock(WebMarkupContainer.class);
        IDataProvider dataProvider = Mockito.mock(IDataProvider.class);
        final IRepeaterUtil util = mockRepeaterUtil();
        TestObj obj1 = Mockito.mock(TestObj.class);
        TestObj obj2 = Mockito.mock(TestObj.class);
        final Iterator<Item<TestObj>> newItems = Mockito.mock(Iterator.class);
        final int size = 12;
        final int index1 = size;
        final int index2 = size + 1;
        final Item<TestObj> item1 = new Item<TestObj>("123", index1, new Model<TestObj>(obj1));
        final Item<TestObj> item2 = new Item<TestObj>("124", index2, new Model<TestObj>(obj2));
        Mockito.when(newItems.next()).thenReturn(item1).thenReturn(item2);
        Mockito.when(newItems.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);

        QuickView<TestObj> arc = new QuickView<TestObj>("repeater", dataProvider, oneBlock) {

            public void populate(Item<TestObj> item) {
            }

            public MarkupContainer _getParent() {
                return parent;
            }

            public MarkupContainer simpleAdd(Component... c) {
                return this;
            }


            @Override
            public IRepeaterUtil getRepeaterUtil() {
                return util;
            }


            @Override
            public Item buildItem(int index, TestObj object) {
                return null;
            }

            @Override
            public MarkupContainer add(Component... components) {
                return this;
            }

            @Override
            public int size() {
                return size;
            }
        };

        QuickView<TestObj> sparc = Mockito.spy(arc);
        Mockito.when(sparc.buildItem(index1, obj1)).thenReturn(item1);
        Mockito.when(sparc.buildItem(index2, obj2)).thenReturn(item2);
        sparc.addNewItemsAtStart(obj1, obj2);
        Mockito.verify(sparc, Mockito.times(1)).buildItem(index1, obj1);
        Mockito.verify(sparc, Mockito.times(1)).buildItem(index2, obj2);
        Mockito.verify(sparc, Mockito.times(1)).addAtStart(item1, item2);

    }

    /**
     * synchronizer NOT null,request handler is AjaxRequestTarget based
     */
    @WicketTest
    public void remove_1() {
        final int itemsPerRequest = 2;
        final WebMarkupContainer parent = Mockito.mock(WebMarkupContainer.class);
        IDataProvider dataProvider = Mockito.mock(IDataProvider.class);
        final IRepeaterUtil util = mockRepeaterUtil();
        final Item c = Mockito.mock(Item.class);
        final String itemId = "item";
        Mockito.when(c.getMarkupId()).thenReturn(itemId);
        final Synchronizer synchronizer = Mockito.mock(Synchronizer.class);
        QuickView<TestObj> arc = new QuickView<TestObj>("repeater", dataProvider, itemsPerRequest) {

            public void populate(Item<TestObj> item) {
            }
        };
        final String script = "script..";
        Mockito.when(util.removeItem(c, parent)).thenReturn(script);
        IRepeaterUtil repeaterUtil = mockRepeaterUtil();
        QuickView spy = Mockito.spy(arc);
        Mockito.doReturn(parent).when(spy)._getParent();
        Mockito.doReturn(synchronizer).when(spy).getSynchronizer();
        Mockito.when(synchronizer.isRequestHandlerAjaxRequestTarget()).thenReturn(true);
        Mockito.doReturn(parent).when(spy).simpleRemove(c);
        Mockito.doReturn(repeaterUtil).when(spy).getRepeaterUtil();
        Mockito.when(repeaterUtil.removeItem(c, parent)).thenReturn(script);
        spy.remove(c);
        Mockito.verify(spy).simpleRemove(c);
        Mockito.verify(synchronizer, Mockito.never()).add(c);
        Mockito.verify(synchronizer).prependScript(script);
        Mockito.verify(synchronizer, Mockito.never()).submit();
    }


    /**
     * synchronizer NOT null ,request handler NOT AjaxRequestTarget based
     */
    @WicketTest
    public void remove_2() {
        final int itemsPerRequest = 2;
        final WebMarkupContainer parent = Mockito.mock(WebMarkupContainer.class);
        IDataProvider dataProvider = Mockito.mock(IDataProvider.class);
        final IRepeaterUtil util = mockRepeaterUtil();
        final Item c = Mockito.mock(Item.class);
        final String itemId = "item";
        Mockito.when(c.getMarkupId()).thenReturn(itemId);
        final Synchronizer synchronizer = Mockito.mock(Synchronizer.class);
        QuickView<TestObj> arc = new QuickView<TestObj>("repeater", dataProvider, itemsPerRequest) {

            public void populate(Item<TestObj> item) {
            }
        };
        final String script = "script..";
        Mockito.when(util.removeItem(c, parent)).thenReturn(script);
        IRepeaterUtil repeaterUtil = mockRepeaterUtil();
        QuickView spy = Mockito.spy(arc);
        Mockito.doReturn(parent).when(spy)._getParent();
        Mockito.doReturn(synchronizer).when(spy).getSynchronizer();
        Mockito.when(synchronizer.isRequestHandlerAjaxRequestTarget()).thenReturn(false);
        Mockito.doReturn(parent).when(spy).simpleRemove(c);
        Mockito.doReturn(repeaterUtil).when(spy).getRepeaterUtil();
        Mockito.when(repeaterUtil.removeItem(c, parent)).thenReturn(script);
        spy.remove(c);
        Mockito.verify(spy).simpleRemove(c);
        Mockito.verify(synchronizer, Mockito.never()).add(c);
        Mockito.verify(synchronizer).prependScript(script);
        Mockito.verify(synchronizer).submit();
    }


    /**
     * synchronizer is null
     */
    @WicketTest
    public void remove_3() {
        final int itemsPerRequest = 2;
        final WebMarkupContainer parent = Mockito.mock(WebMarkupContainer.class);
        IDataProvider dataProvider = Mockito.mock(IDataProvider.class);
        final IRepeaterUtil util = mockRepeaterUtil();
        final Item c = Mockito.mock(Item.class);
        final String itemId = "item";
        Mockito.when(c.getMarkupId()).thenReturn(itemId);
        QuickView<TestObj> quickView = new QuickView<TestObj>("repeater", dataProvider, itemsPerRequest) {
            public void populate(Item<TestObj> item) {
            }
        };
        final String script = "script..";
        Mockito.when(util.removeItem(c, parent)).thenReturn(script);
        IRepeaterUtil repeaterUtil = mockRepeaterUtil();
        QuickView spy = Mockito.spy(quickView);
        Mockito.doReturn(parent).when(spy)._getParent();
        Mockito.doReturn(null).when(spy).getSynchronizer();
        Mockito.doReturn(parent).when(spy).simpleRemove(c);
        Mockito.doReturn(repeaterUtil).when(spy).getRepeaterUtil();
        Mockito.when(repeaterUtil.removeItem(c, parent)).thenReturn(script);
        spy.remove(c);
        Mockito.verify(spy).simpleRemove(c);

    }


    /**
     * one component added ,ajax=true ,synchronizer NOT null,
     * synchronizer is for AjaxRequestTarget
     */
    @WicketTest
    public void addAtStart_1() {
        final int itemsPerRequest = 2;
        final WebMarkupContainer parent = Mockito.mock(WebMarkupContainer.class);
        IDataProvider dataProvider = Mockito.mock(IDataProvider.class);
        final Synchronizer synchronizer = Mockito.mock(Synchronizer.class);
        final Item c = new Item("10",9);
        QuickView<TestObj> quickView = new QuickView<TestObj>("repeater", dataProvider, itemsPerRequest) {
            public void populate(Item<TestObj> item) {
            }

        };
        QuickView spy = Mockito.spy(quickView);
        Mockito.doReturn(parent).when(spy)._getParent();
        Mockito.doReturn(synchronizer).when(spy).getSynchronizer();
        Mockito.doReturn(true).when(spy).isAjax();
        Mockito.doNothing().when(spy)._contributeAddAtStartScripts(c);
        Mockito.doReturn(spy).when(spy).simpleAdd(c);
        Mockito.when(synchronizer.isRequestHandlerAjaxRequestTarget()).thenReturn(true);
        spy.addAtStart(c);
        Mockito.verify(spy).simpleAdd(c);
        Mockito.verify(spy)._contributeAddAtStartScripts(c);
        Mockito.verify(synchronizer).add(c);
        Mockito.verify(synchronizer, Mockito.never()).submit();
        assertTrue(spy.getAddAtStartStore().contains(c.getId()));

    }


    /**
     * one component added ,ajax=true ,request handler is NOT AjaxRequestTarget based
     */
    @WicketTest
    public void addAtStart_2() {
        final int itemsPerRequest = 2;
        final WebMarkupContainer parent = Mockito.mock(WebMarkupContainer.class);
        IDataProvider dataProvider = Mockito.mock(IDataProvider.class);
        final Synchronizer synchronizer = Mockito.mock(Synchronizer.class);
        final Item c = new Item("15",14);
        QuickView<TestObj> quickView = new QuickView<TestObj>("repeater", dataProvider, itemsPerRequest) {

            public void populate(Item<TestObj> item) {
            }

        };
        QuickView spy = Mockito.spy(quickView);
        Mockito.doReturn(parent).when(spy)._getParent();
        Mockito.doReturn(synchronizer).when(spy).getSynchronizer();
        Mockito.doReturn(true).when(spy).isAjax();
        Mockito.when(synchronizer.isRequestHandlerAjaxRequestTarget()).thenReturn(false);
        Mockito.doNothing().when(spy)._contributeAddAtStartScripts(c);
        Mockito.doReturn(spy).when(spy).simpleAdd(c);
        spy.addAtStart(c);
        Mockito.verify(spy).simpleAdd(c);
        Mockito.verify(spy)._contributeAddAtStartScripts(c);
        Mockito.verify(synchronizer).add(c);
        Mockito.verify(synchronizer).submit();
        assertTrue(spy.getAddAtStartStore().contains(c.getId()));

    }


    /**
     * current page=5  ,reuse#getPageCreatedOnRender() is zero  ,reuse#isAddItemsSuypported() is true
     * <p>
     * for eg. ItemsNaviagtionStrategy
     */
    @WicketTest
    public void onPopulate_1() {
        IDataProvider provider = Mockito.mock(IDataProvider.class);
        final int currentPage = 5;
        final IRepeaterUtil util = Mockito.mock(IRepeaterUtil.class);
        IQuickReuseStrategy reuse = Mockito.mock(IQuickReuseStrategy.class);
        //
        // add items supported
        //
        Mockito.when(reuse.isPartialUpdatesSupported()).thenReturn(true);
        //
        //page not set from reuse strategy
        //
        Mockito.when(reuse.getPageCreatedOnRender()).thenReturn(0l);
        final IItemFactory factory = Mockito.mock(IItemFactory.class);
        final Iterator existing = Mockito.mock(Iterator.class);
        final Iterator newModels = Mockito.mock(Iterator.class);
        final Iterator newItems = Mockito.mock(Iterator.class);
        Iterator data = Mockito.mock(Iterator.class);
        final int itemsPerRequest = 2;
        final int offset = currentPage * itemsPerRequest;
        Mockito.when(reuse.getItems(factory, newModels, existing)).thenReturn(newItems);
        QuickView repeater = new QuickView("repeater", provider, reuse, itemsPerRequest) {

            @Override
            public Iterator<Component> iterator() {
                return existing;
            }

            @Override
            public IRepeaterUtil getRepeaterUtil() {
                return util;
            }

            @Override
            protected void populate(Item item) {
            }

            @Override
            public MarkupContainer simpleAdd(Component... c) {
                return this;
            }


            @Override
            public MarkupContainer simpleRemoveAll() {
                return this;
            }

            @Override
            protected void createChildren(Iterator iterator) {
            }

            @Override
            public IItemFactory factory() {
                return factory;
            }
        };
        QuickView spy = Mockito.spy(repeater);
        Mockito.doReturn(newModels).when(spy).newModels(0, itemsPerRequest);
        spy.onPopulate();

        InOrder order = Mockito.inOrder(reuse, spy, provider);
        order.verify(spy).newModels(0, itemsPerRequest);
        order.verify(reuse).getItems(factory, newModels, existing);
        order.verify(spy).simpleRemoveAll();
        order.verify(spy).createChildren(newItems);
        Mockito.verify(spy)._setCurrentPage(0);

    }

    /**
     * current page=5  ,reuse#getPageCreatedOnRender() is -1 ,reuse#isAddItemsSuppored()==false
     * for eg. Any AbstractPagingNavigationStrategy
     */
    @WicketTest
    public void onPopulate_2() {
        IDataProvider provider = Mockito.mock(IDataProvider.class);
        final long currentPage = 5;
        final IRepeaterUtil util = Mockito.mock(IRepeaterUtil.class);
        IQuickReuseStrategy reuse = Mockito.mock(IQuickReuseStrategy.class);
        //
        // add items supported
        //
        Mockito.when(reuse.isPartialUpdatesSupported()).thenReturn(false);
        //
        //page not set from reuse strategy
        //
        Mockito.when(reuse.getPageCreatedOnRender()).thenReturn(-1l);
        final IItemFactory factory = Mockito.mock(IItemFactory.class);
        final Iterator existing = Mockito.mock(Iterator.class);
        final Iterator newModels = Mockito.mock(Iterator.class);
        final Iterator newItems = Mockito.mock(Iterator.class);
        Iterator data = Mockito.mock(Iterator.class);
        final int itemsPerRequest = 2;
        // final int offset = currentPage * itemsPerRequest;
        Mockito.when(reuse.getItems(factory, newModels, existing)).thenReturn(newItems);

        QuickView repeater = new QuickView("repeater", provider, reuse, itemsPerRequest) {

            @Override
            public Iterator<Component> iterator() {
                return existing;
            }

            @Override
            public IRepeaterUtil getRepeaterUtil() {
                return util;
            }

            @Override
            protected void populate(Item item) {
            }

            @Override
            public MarkupContainer simpleAdd(Component... c) {
                return this;
            }


            @Override
            public MarkupContainer simpleRemoveAll() {
                return this;
            }

            @Override
            protected void createChildren(Iterator iterator) {
            }

            @Override
            public IItemFactory factory() {
                return factory;
            }
        };
        QuickView spy = Mockito.spy(repeater);
        Mockito.doReturn(newModels).when(spy).newModels((currentPage * itemsPerRequest), itemsPerRequest);
        Mockito.doReturn(currentPage).when(spy)._getCurrentPage();
        spy.onPopulate();

        InOrder order = Mockito.inOrder(reuse, spy, provider);
        order.verify(spy).newModels((currentPage * itemsPerRequest), itemsPerRequest);
        order.verify(reuse).getItems(factory, newModels, existing);
        order.verify(spy).simpleRemoveAll();
        order.verify(spy).createChildren(newItems);
        Mockito.verify(spy, Mockito.never())._setCurrentPage(Mockito.anyLong());
    }


    /**
     * current page=5  ,reuse#getPageCreatedOnRender() is -1 ,reuse#isAddItemsSuppored()==false
     * foreg. ReuseAllStrategy
     */
    @WicketTest
    public void onPopulate_3() {
        IDataProvider provider = Mockito.mock(IDataProvider.class);
        final long currentPage = 5;
        final IRepeaterUtil util = Mockito.mock(IRepeaterUtil.class);
        IQuickReuseStrategy reuse = Mockito.mock(IQuickReuseStrategy.class);
        //
        // add items supported
        //
        Mockito.when(reuse.isPartialUpdatesSupported()).thenReturn(true);
        //
        //page not set from reuse strategy
        //
        Mockito.when(reuse.getPageCreatedOnRender()).thenReturn(-1l);
        final IItemFactory factory = Mockito.mock(IItemFactory.class);
        final Iterator existing = Mockito.mock(Iterator.class);
        final Iterator newModels = Mockito.mock(Iterator.class);
        final Iterator newItems = Mockito.mock(Iterator.class);
        Iterator data = Mockito.mock(Iterator.class);
        final int itemsPerRequest = 2;
        Mockito.when(reuse.getItems(factory, newModels, existing)).thenReturn(newItems);

        QuickView repeater = new QuickView("repeater", provider, reuse, itemsPerRequest) {

            @Override
            public Iterator<Component> iterator() {
                return existing;
            }

            @Override
            public IRepeaterUtil getRepeaterUtil() {
                return util;
            }

            @Override
            protected void populate(Item item) {
            }

            @Override
            public MarkupContainer simpleAdd(Component... c) {
                return this;
            }


            @Override
            public MarkupContainer simpleRemoveAll() {
                return this;
            }

            @Override
            protected void createChildren(Iterator iterator) {
            }

            @Override
            public IItemFactory factory() {
                return factory;
            }
        };
        QuickView spy = Mockito.spy(repeater);
        Mockito.doReturn(newModels).when(spy).newModels(0, (currentPage + 1) * itemsPerRequest);
        Mockito.doReturn(currentPage).when(spy)._getCurrentPage();
        spy.onPopulate();

        InOrder order = Mockito.inOrder(reuse, spy, provider);
        order.verify(spy).newModels(0, (currentPage + 1) * itemsPerRequest);
        order.verify(reuse).getItems(factory, newModels, existing);
        order.verify(spy).simpleRemoveAll();
        order.verify(spy).createChildren(newItems);
        Mockito.verify(spy, Mockito.never())._setCurrentPage(Mockito.anyLong());

    }


    @WicketTest
    public void createChildren_1() {
        IDataProvider provider = Mockito.mock(IDataProvider.class);
        Iterator<Item> children = Mockito.mock(Iterator.class);
        final Item item1 = new Item("1", 1);
        final Item item2 = new Item("2", 2);
        Mockito.when(children.next()).thenReturn(item1).thenReturn(item2);
        Mockito.when(children.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
        QuickView repeater = new QuickView("repeater", provider) {

            @Override
            protected void populate(Item item) {
            }

            @Override
            public MarkupContainer simpleAdd(Component... c) {
                return this;
            }


            @Override
            public MarkupContainer simpleRemoveAll() {
                return this;
            }


        };

        QuickView spy = Mockito.spy(repeater);
        spy.createChildren(children);
        Mockito.verify(spy, Mockito.times(1)).simpleAdd(item1);
        Mockito.verify(spy, Mockito.times(1)).simpleAdd(item2);

    }


    /**
     * items=10,itemsperrequest=2
     */
    @WicketTest
    public void getPageCount_1() {
        final int itemsperrequest = 2;
        IDataProvider provider = Mockito.mock(IDataProvider.class);
        Mockito.when(provider.size()).thenReturn(10l);
        QuickView repeater = new QuickView("repeater", provider, itemsperrequest) {
            @Override
            protected void populate(Item item) {
            }

        };
        long actual = repeater.getPageCount();
        assertEquals(actual, 5);
    }

    /**
     * items=10,itemsperrequest=3
     */

    @WicketTest
    public void getPageCount_2() {
        final int itemsperrequest = 3;
        IDataProvider provider = Mockito.mock(IDataProvider.class);
        Mockito.when(provider.size()).thenReturn(10l);
        QuickView repeater = new QuickView("repeater", provider, itemsperrequest) {
            @Override
            protected void populate(Item item) {
            }

        };
        long actual = repeater.getPageCount();
        assertEquals(actual, 4);
    }


    @WicketTest
    public void getItemsCount_1() {
        final int itemsPerRequest = 3;
        IDataProvider provider = Mockito.mock(IDataProvider.class);
        Mockito.when(provider.size()).thenReturn(10l);
        QuickView repeater = new QuickView("repeater", provider, itemsPerRequest) {
            @Override
            protected void populate(Item item) {
            }

        };
        long actual = repeater.getItemsCount();
        assertEquals(actual, 10l);
    }

    /*
     * when repeater is not visible in hierarchy
     */
    @WicketTest
    public void getRowsCount_1() {
        final int itemsPerRequest = 3;
        IDataProvider provider = Mockito.mock(IDataProvider.class);
        Mockito.when(provider.size()).thenReturn(10l);
        QuickView repeater = new QuickView("repeater", provider, itemsPerRequest) {
            @Override
            protected void populate(Item item) {
            }

            @Override
            public boolean isVisible() {
                return false;
            }
        };

        long actual = repeater.getRowsCount();
        assertEquals(actual, 0l);
    }

    /*
     * when repeater is  visible in hierarchy
     */
    @WicketTest
    public void getRowsCount_2() {
        final int itemsPerRequest = 3;
        IDataProvider provider = Mockito.mock(IDataProvider.class);
        Mockito.when(provider.size()).thenReturn(10l);
        QuickView repeater = new QuickView("repeater", provider, itemsPerRequest) {
            @Override
            protected void populate(Item item) {
            }

            @Override
            public boolean isVisible() {
                return true;
            }

        };

        long actual = repeater.getRowsCount();
        assertEquals(actual, 10l);
    }

    /**
     * itemsperrequest>0
     */
    @WicketTest
    public void setItemsPerRequest_1() {
        final int itemsPerRequest = 3;
        IDataProvider provider = Mockito.mock(IDataProvider.class);
        Mockito.when(provider.size()).thenReturn(10l);
        QuickView repeater = new QuickView("repeater", provider) {
            @Override
            protected void populate(Item item) {
            }

            @Override
            public boolean isVisible() {
                return true;
            }

        };
        QuickView spy = Mockito.spy(repeater);
        spy.setItemsPerRequest(itemsPerRequest);
        assertEquals(spy.getItemsPerRequest(), 3);
        Mockito.verify(spy, Mockito.times(1))._setCurrentPage(0);
    }

    /**
     * itemsperrequest<0
     */
    @WicketTest
    public void setItemsPerRequest_2() {
        final int itemsPerRequest = -1;
        IDataProvider provider = Mockito.mock(IDataProvider.class);
        Mockito.when(provider.size()).thenReturn(10l);
        QuickView repeater = new QuickView("repeater", provider) {
            @Override
            protected void populate(Item item) {
            }

            @Override
            public boolean isVisible() {
                return true;
            }

        };

        boolean isException = false;
        try {
            repeater.setItemsPerRequest(itemsPerRequest);
        } catch (IllegalArgumentException ex) {
            isException = true;
        }
        assertTrue(isException);
    }

    /**
     * itemsPerRequest changed
     */
    @WicketTest
    public void setItemsPerRequest_3() {
        final int oldItemsPerRequest = 3;
        final int newItemsPerRequest = 5;
        IDataProvider provider = Mockito.mock(IDataProvider.class);
        Mockito.when(provider.size()).thenReturn(10l);
        QuickView repeater = new QuickView("repeater", provider) {
            @Override
            protected void populate(Item item) {
            }

            @Override
            public boolean isVisible() {
                return true;
            }

        };
        repeater.setItemsPerRequest(oldItemsPerRequest);
        QuickView spy = Mockito.spy(repeater);
        spy.setItemsPerRequest(newItemsPerRequest);
        assertEquals(spy.getItemsPerRequest(), newItemsPerRequest);
        Mockito.verify(spy, Mockito.times(1)).setItemsPerRequest(newItemsPerRequest);
        Mockito.verify(spy, Mockito.times(1))._setCurrentPage(0);
    }

    /**
     * itemsPerRequest not changed  ie. if itemsPerRequest is not changed but it's set again
     */
    @WicketTest
    public void setItemsPerRequest_4() {
        final int itemsPerRequest = 3;
        IDataProvider provider = Mockito.mock(IDataProvider.class);
        Mockito.when(provider.size()).thenReturn(10l);
        QuickView repeater = new QuickView("repeater", provider) {
            @Override
            protected void populate(Item item) {
            }

            @Override
            public boolean isVisible() {
                return true;
            }

        };
        repeater.setItemsPerRequest(itemsPerRequest);
        QuickView spy = Mockito.spy(repeater);
        spy.setItemsPerRequest(itemsPerRequest);
        assertEquals(spy.getItemsPerRequest(), itemsPerRequest);
        Mockito.verify(spy, Mockito.never())._setCurrentPage(0);
    }

    @WicketTest
    public void renderHead_1() {
        IDataProvider data = Mockito.mock(IDataProvider.class);
        QuickView quick = new QuickView("id", data, 1) {
            @Override
            protected void populate(Item item) {
            }
        };
        IHeaderResponse response = Mockito.mock(IHeaderResponse.class);
        quick.renderHead(response);
        Mockito.verify(response, Mockito.times(1)).render(JavaScriptHeaderItem.forReference(RepeaterUtilReference.get()));
    }


    @WicketTest
    public void simpleRemove() {
        IDataProvider data = Mockito.mock(IDataProvider.class);
        QuickView quickView = new QuickView("id", data) {
            @Override
            protected void populate(Item item) {
            }
        };
        Item one = quickView.buildItem(89, 67);
        Item two = quickView.buildItem(90, 68);
        quickView.simpleAdd(one, two);
        quickView.simpleRemove(one);
        assertEquals(quickView.size(), 1);
    }

    /**
     * added two,removed all
     */
    @WicketTest
    public void simpleRemoveAll() {
        IDataProvider data = Mockito.mock(IDataProvider.class);
        QuickView quickView = new QuickView("id", data) {
            @Override
            protected void populate(Item item) {
            }
        };
        int index = 178;
        int index2 = 179;
        Item one = quickView.buildItem(index, 67);
        Item two = quickView.buildItem(index2, 68);
        quickView.simpleAdd(one, two);
        quickView.simpleRemoveAll();
        assertEquals(quickView.size(), 0);
    }

    @WicketTest
    public void simpleAdd() {
        IDataProvider data = Mockito.mock(IDataProvider.class);
        QuickView quickView = new QuickView("id", data) {
            @Override
            protected void populate(Item item) {
            }
        };
        Item one = quickView.buildItem(78, 67);
        quickView.simpleAdd(one);
        assertEquals(quickView.size(), 1);
        Item two = quickView.buildItem(79, 68);
        quickView.simpleAdd(two);
        assertEquals(quickView.size(), 2);

    }


    @WicketTest
    public void newItem_1() {
        final int object = 89;
        final Model<Integer> model = new Model<Integer>(object);
        IDataProvider<Integer> data = Mockito.mock(IDataProvider.class);
        Mockito.when(data.model(object)).thenReturn(model);
        QuickView<Integer> quickView = new QuickView<Integer>("id", data) {
            @Override
            protected void populate(Item<Integer> item) {
            }
        };
        final int index = 9;
        final String id = "67";
        Item<Integer> item = quickView.newItem(id, index, model);
        assertEquals(item.getModelObject().intValue(), 89);
        assertEquals(item.getId(), id);
        assertEquals(item.getIndex(), index);
        assertTrue(item.getOutputMarkupId());
    }


    /**
     * test for  buildItem(String, long, IModel)
     */
    @WicketTest
    public void buildItem_1() {
        IDataProvider data = Mockito.mock(IDataProvider.class);
        IModel model = Mockito.mock(IModel.class);

        final Item item = Mockito.mock(Item.class);
        QuickView<TestObj> quickView = new QuickView<TestObj>("id", data) {
            @Override
            protected void populate(Item item) {
            }

            @Override
            protected Item<TestObj> newItem(String id, int index, IModel<TestObj> model) {
                return item;
            }
        };
        final int index = 9;
        final String id = "87";
        final TestObj object = Mockito.mock(TestObj.class);
        Mockito.when(model.getObject()).thenReturn(object);
        Mockito.when(data.model(object)).thenReturn(model);
        QuickView<TestObj> spy = Mockito.spy(quickView);
        Item<TestObj> actual = spy.buildItem(id, index, object);
        assertEquals(actual, item);
        InOrder order = Mockito.inOrder(spy, item);
        order.verify(spy).newItem(id, index, model);
        order.verify(spy).populate(item);
    }

    /**
     * test for  buildItem(String, long, IModel)
     */
    @WicketTest
    public void buildItem_2() {
        IDataProvider data = Mockito.mock(IDataProvider.class);
        IModel model = Mockito.mock(IModel.class);

        final Item item = Mockito.mock(Item.class);
        QuickView<TestObj> quickView = new QuickView<TestObj>("id", data) {
            @Override
            protected void populate(Item item) {
            }

            @Override
            protected Item<TestObj> newItem(String id, int index, IModel<TestObj> model) {
                return item;
            }
        };
        final int index = 9;
        final String id = "87";
        final TestObj object = Mockito.mock(TestObj.class);
        Mockito.when(model.getObject()).thenReturn(object);
        Mockito.when(data.model(object)).thenReturn(model);
        QuickView<TestObj> spy = Mockito.spy(quickView);
        Item<TestObj> actual = spy.buildItem(id, index, model);
        assertEquals(actual, item);
        InOrder order = Mockito.inOrder(spy, item);
        order.verify(spy, Mockito.times(1)).newItem(id, index, model);
        order.verify(spy).populate(item);
    }


    /**
     * test for buildItem(int, Object)
     */
    @WicketTest
    public void buildItem_3() {
        IDataProvider data = Mockito.mock(IDataProvider.class);
        final Item item = Mockito.mock(Item.class);
        final String childId = "78";
        QuickView<TestObj> quickView = new QuickView<TestObj>("id", data) {
            @Override
            protected void populate(Item<TestObj> item) {
            }

            @Override
            public Item<TestObj> buildItem(String id, int index, TestObj object) {
                return item;
            }

            @Override
            public String newChildId() {
                return childId;
            }


        };
        QuickView<TestObj> spy = Mockito.spy(quickView);
        TestObj obj = Mockito.mock(TestObj.class);
        spy.buildItem(234, obj);
        Mockito.verify(spy).buildItem(childId, 234, obj);

    }


    @WicketTest
    public void addItemsForNextPage_1() {
        final long dataProviderSize = 12;
        final long current = 5, next = 6, pages = 7;
        IDataProvider dataProvider = Mockito.mock(IDataProvider.class);
        Mockito.when(dataProvider.size()).thenReturn(dataProviderSize);
        final AjaxRequestTarget target = Mockito.mock(AjaxRequestTarget.class);
        final List<Item> items = Mockito.mock(List.class);
        QuickView quickview = new QuickView("quick", dataProvider) {
            @Override
            protected void populate(Item item) {
            }

            @Override
            protected long _getPageCount() {
                return pages;
            }

            @Override
            protected long _getCurrentPage() {
                return current;
            }

            @Override
            public AjaxRequestTarget getAjaxRequestTarget() {
                return target;
            }

            @Override
            public List addItemsForPage(long page) {
                return items;
            }
        };

        QuickView spy = Mockito.spy(quickview);
        List<Item> actual = spy.addItemsForNextPage();
        Mockito.verify(spy, Mockito.times(1))._setCurrentPage(next);
        Mockito.verify(spy, Mockito.times(1)).addItemsForPage(next);
        assertEquals(actual, items);
    }

    /**
     * when current page= pages count
     */
    @WicketTest
    public void addItemsForNextPage_2() {

        final long dataProviderSize = 12;
        final long current = 5, next = 6, pages = 6;
        IDataProvider dataProvider = Mockito.mock(IDataProvider.class);
        Mockito.when(dataProvider.size()).thenReturn(dataProviderSize);
        final AjaxRequestTarget target = Mockito.mock(AjaxRequestTarget.class);
        final List<Item> items = Mockito.mock(List.class);
        QuickView quickview = new QuickView("quick", dataProvider) {
            @Override
            protected void populate(Item item) {
            }

            @Override
            protected long _getPageCount() {
                return pages;
            }

            @Override
            protected long _getCurrentPage() {
                return current;
            }

            @Override
            public AjaxRequestTarget getAjaxRequestTarget() {
                return target;
            }

            @Override
            public List addItemsForPage(long page) {
                return items;
            }
        };
        QuickView spy = Mockito.spy(quickview);
        List<Item> actual = spy.addItemsForNextPage();
        Mockito.verify(spy, Mockito.never())._setCurrentPage(next);
        Mockito.verify(spy, Mockito.never()).addItemsForPage(next);
        assertTrue(actual.isEmpty());
    }

    /**
     * when current page> pages count
     */
    @WicketTest
    public void addItemsForNextPage_3() {
        final long dataProviderSize = 12;
        final long current = 7, next = 6, pages = 6;
        final AjaxRequestTarget target = Mockito.mock(AjaxRequestTarget.class);
        final List<Item> items = Mockito.mock(List.class);
        IDataProvider dataProvider = Mockito.mock(IDataProvider.class);
        Mockito.when(dataProvider.size()).thenReturn(dataProviderSize);
        QuickView quickview = new QuickView("quick", dataProvider) {
            @Override
            protected void populate(Item item) {
            }

            @Override
            protected long _getPageCount() {
                return pages;
            }

            @Override
            protected long _getCurrentPage() {
                return current;
            }

            @Override
            public AjaxRequestTarget getAjaxRequestTarget() {
                return target;
            }

            @Override
            public List addItemsForPage(long page) {
                return items;
            }
        };
        QuickView spy = Mockito.spy(quickview);
        List<Item> actual = spy.addItemsForNextPage();
        Mockito.verify(spy, Mockito.never())._setCurrentPage(next);
        Mockito.verify(spy, Mockito.never()).addItemsForPage(next);
        assertTrue(actual.isEmpty());
    }

    /**
     * page=2 ,itemsperrequest=2 ,reuse=ReUse.ITEMSNAVIGATION
     */

    @WicketTest
    public void addItemsForPage_1() {
        int itemsPerRequest = 2;
        IDataProvider dataProvider = Mockito.mock(IDataProvider.class);
        Iterator data = mockIterator();
        Mockito.when(dataProvider.iterator(4, itemsPerRequest)).thenReturn(data);
        Mockito.when(data.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
        IModel<Integer> model1 = new Model<Integer>(11);
        IModel<Integer> model2 = new Model<Integer>(55);

        final Iterator<IModel<Integer>> newModels = Mockito.mock(Iterator.class);
        Mockito.when(newModels.next()).thenReturn(model1);
        Mockito.when(newModels.next()).thenReturn(model2);
        Mockito.when(newModels.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
        Item<Integer> item1 = new Item("1", 1, model1);
        Item<Integer> item2 = new Item("2", 2, model2);
        List<Item<Integer>> list = new ArrayList<Item<Integer>>();
        list.add(item1);
        list.add(item2);
        final Iterator newIterator = list.iterator();
        final IQuickReuseStrategy reuseStrategy = Mockito.mock(IQuickReuseStrategy.class);
        final IItemFactory factory = Mockito.mock(IItemFactory.class);
        Mockito.when(factory.newItem(1, model1)).thenReturn(item1);
        Mockito.when(factory.newItem(2, model2)).thenReturn(item2);
        Mockito.when(reuseStrategy.addItems(4, factory, newModels)).thenReturn(list.iterator());
        QuickView repeater = new QuickView("repeater", dataProvider, new ItemsNavigationStrategy(), itemsPerRequest) {

            public void populate(Item item) {
            }

            @Override
            public MarkupContainer add(Component... c) {
                return this;
            }

            @Override
            protected Iterator buildItems(int index, Iterator iterator) {
                return newIterator;
            }

            @Override
            protected Iterator newModels(long offset, long count) {
                return newModels;
            }

            @Override
            public IItemFactory factory() {
                return factory;
            }
        };
        repeater.setReuseStrategy(reuseStrategy);
        QuickView spy = Mockito.spy(repeater);
        List<Item<TestObj>> items = spy.addItemsForPage(2);
        Mockito.verify(reuseStrategy, Mockito.times(1)).addItems(4, factory, newModels);

        assertEquals(items.size(), list.size());
        Mockito.verify(spy, Mockito.times(1)).add(items.get(0));
        Mockito.verify(spy, Mockito.times(1)).add(items.get(1));


    }


    /*
     *start index=0
     */
    @WicketTest
    public void buildItems_1() {
        List<Integer> data = data(10);
        IDataProvider<Integer> dataProvider = new ListDataProvider<Integer>(data);
        QuickView quickView = new QuickView("quickview", dataProvider) {
            @Override
            protected void populate(Item item) {
            }
        };
        Iterator<? extends Integer> dataIterator = dataProvider.iterator(0, 2);
        Iterator<Item<Integer>> itemsIterator = quickView.buildItems(0, dataIterator);
        Item<Integer> item1 = itemsIterator.next();
        Item<Integer> item2 = itemsIterator.next();
        assertEquals(item1.getIndex(), 0);
        assertEquals(item1.getModelObject().intValue(), 0);
        assertEquals(item2.getIndex(), 1);
        assertEquals(item2.getModelObject().intValue(), 1);
        assertTrue(Long.parseLong(item2.getId()) > Long.parseLong(item1.getId()));
    }

    /*
     *start index=10
     */
    @WicketTest
    public void buildItems_2() {
        List<Integer> data = data(10);
        IDataProvider<Integer> dataProvider = new ListDataProvider<Integer>(data);
        QuickView<Integer> quickView = new QuickView<Integer>("quickview", dataProvider) {
            @Override
            protected void populate(Item item) {
            }
        };
        Iterator<? extends Integer> dataIterator = dataProvider.iterator(5, 2);
        Iterator<Item<Integer>> itemsIterator = quickView.buildItems(10, dataIterator);
        Item<Integer> item1 = itemsIterator.next();
        Item<Integer> item2 = itemsIterator.next();
        assertEquals(item1.getIndex(), 10);
        assertEquals(item1.getModelObject().intValue(), 5);
        assertEquals(item2.getIndex(), 11);
        assertEquals(item2.getModelObject().intValue(), 6);
        assertTrue(Long.parseLong(item2.getId()) > Long.parseLong(item1.getId()));
    }

    /*
     *start index=0
     */
    @WicketTest
    public void buildItemsList_1() {
        List<Integer> data = data(10);
        IDataProvider<Integer> dataProvider = new ListDataProvider<Integer>(data);
        QuickView quickView = new QuickView("quickview", dataProvider) {
            @Override
            protected void populate(Item item) {
            }
        };
        Iterator<? extends Integer> dataIterator = dataProvider.iterator(0, 2);
        List<Item<Integer>> items = quickView.buildItemsList(0, dataIterator);
        Item<Integer> item1 = items.get(0);
        Item<Integer> item2 = items.get(1);
        assertEquals(item1.getIndex(), 0);
        assertEquals(item1.getModelObject().intValue(), 0);
        assertEquals(item2.getIndex(), 1);
        assertEquals(item2.getModelObject().intValue(), 1);
        assertTrue(Long.parseLong(item2.getId()) > Long.parseLong(item1.getId()));
    }


    /*
     *start index=10
     */
    @WicketTest
    public void buildItemsList_2() {
        List<Integer> data = data(10);
        IDataProvider<Integer> dataProvider = new ListDataProvider<Integer>(data);
        QuickView<Integer> quickView = new QuickView<Integer>("quickview", dataProvider) {
            @Override
            protected void populate(Item item) {
            }
        };
        Iterator<? extends Integer> dataIterator = dataProvider.iterator(5, 2);
        List<Item<Integer>> items = quickView.buildItemsList(10, dataIterator);
        Item<Integer> item1 = items.get(0);
        Item<Integer> item2 = items.get(1);
        assertEquals(item1.getIndex(), 10);
        assertEquals(item1.getModelObject().intValue(), 5);
        assertEquals(item2.getIndex(), 11);
        assertEquals(item2.getModelObject().intValue(), 6);
        assertTrue(Long.parseLong(item2.getId()) > Long.parseLong(item1.getId()));
    }


    @WicketTest
    public void testAddAtEndScripts_1() {
        List<Integer> data = data(10);
        IDataProvider<Integer> dataProvider = new ListDataProvider<Integer>(data);
        Component start = Mockito.mock(Component.class);
        Component end = Mockito.mock(Component.class);
        QuickView<Integer> quickView = new QuickView<Integer>(
                "quickview", dataProvider, start, end) {
            @Override
            protected void populate(Item item) {
            }
        };

        QuickView<Integer> spy = Mockito.spy(quickView);
        Item component1 = Mockito.mock(Item.class);
        Item component2 = Mockito.mock(Item.class);
        Item component3 = Mockito.mock(Item.class);
        MarkupContainer parent = Mockito.mock(MarkupContainer.class);
        Mockito.doReturn(parent).when(spy)._getParent();
        Component[] components = {component1, component2, component3};
        String script1 = "script1", script2 = "script2", script3 = "script3";
        IRepeaterUtil repeaterUtil = mockRepeaterUtil();
        Mockito.doReturn(repeaterUtil).when(spy).getRepeaterUtil();
        Mockito.when(repeaterUtil.append(component1, parent, start, end)).
                thenReturn(script1);
        Mockito.when(repeaterUtil.append(component2, parent, start, end))
                .thenReturn(script2);
        Mockito.when(repeaterUtil.append(component3, parent, start, end))
                .thenReturn(script3);
        IPartialPageRequestHandler requestHandler = Mockito.mock(IPartialPageRequestHandler.class);
        Synchronizer synchronizer = new Synchronizer(parent, requestHandler);
        Mockito.doReturn(synchronizer).when(spy).getSynchronizer();
        spy._contributeAddAtEndScripts(components);
        String fetchedScript1 = synchronizer._getPrependScripts().get(0);
        String fetchedScript2 = synchronizer._getPrependScripts().get(1);
        String fetchedScript3 = synchronizer._getPrependScripts().get(2);
        assertEquals(fetchedScript1, script1);
        assertEquals(fetchedScript2, script2);
        assertEquals(fetchedScript3, script3);
    }


    @WicketTest
    public void testContributeAddAtStartScripts_1() {
        List<Integer> data = data(10);
        IDataProvider<Integer> dataProvider = new ListDataProvider<Integer>(data);
        Component start = Mockito.mock(Component.class);
        Component end = Mockito.mock(Component.class);
        QuickView<Integer> quickView = new QuickView<Integer>(
                "quickview", dataProvider, start, end) {
            @Override
            protected void populate(Item item) {
            }
        };

        QuickView<Integer> spy = Mockito.spy(quickView);
        Item component1 = Mockito.mock(Item.class);
        Item component2 = Mockito.mock(Item.class);
        Item component3 = Mockito.mock(Item.class);
        MarkupContainer parent = Mockito.mock(MarkupContainer.class);
        Mockito.doReturn(parent).when(spy)._getParent();
        Component[] components = {component1, component2, component3};
        String script1 = "script1", script2 = "script2", script3 = "script3";
        IRepeaterUtil repeaterUtil = mockRepeaterUtil();
        Mockito.doReturn(repeaterUtil).when(spy).getRepeaterUtil();
        Mockito.when(repeaterUtil.prepend(component1, parent, start, end)).
                thenReturn(script1);
        Mockito.when(repeaterUtil.prepend(component2, parent, start, end))
                .thenReturn(script2);
        Mockito.when(repeaterUtil.prepend(component3, parent, start, end))
                .thenReturn(script3);
        IPartialPageRequestHandler requestHandler = Mockito.mock(IPartialPageRequestHandler.class);
        Synchronizer synchronizer = new Synchronizer(parent, requestHandler);
        Mockito.doReturn(synchronizer).when(spy).getSynchronizer();
        spy._contributeAddAtStartScripts(components);
        String fetchedScript1 = synchronizer._getPrependScripts().get(0);
        String fetchedScript2 = synchronizer._getPrependScripts().get(1);
        String fetchedScript3 = synchronizer._getPrependScripts().get(2);
        assertEquals(fetchedScript1, script3);
        assertEquals(fetchedScript2, script2);
        assertEquals(fetchedScript3, script1);
    }

    /**
     * synchronizer exists in requestcycle
     */
    @WicketTest
    public void testGetSynchronizer_1() {

        List<Integer> data = data(10);
        IDataProvider<Integer> dataProvider = new ListDataProvider<Integer>(data);
        Component start = Mockito.mock(Component.class);
        Component end = Mockito.mock(Component.class);
        QuickView<Integer> quickView = new QuickView<Integer>(
                "quickview", dataProvider, start, end) {
            @Override
            protected void populate(Item item) {
            }
        };
        QuickView<Integer> spy = Mockito.spy(quickView);
        Synchronizer synchronizer = Mockito.mock(Synchronizer.class);
        Mockito.doReturn(synchronizer).when(spy)._getRequestMetaData(spy.SYNCHRONIZER_KEY);
        Mockito.doReturn(true).when(spy).isAjax();
        Synchronizer result = spy.getSynchronizer();
        assertEquals(result, synchronizer);
    }


    /**
     * synchronizer does NOT exists in requestcycle
     * and request handler NOT AjaxRequestTarget based
     */
    @WicketTest
    public void testGetSynchronizer_2() {
        List<Integer> data = data(10);
        IDataProvider<Integer> dataProvider = new ListDataProvider<Integer>(data);
        Component start = Mockito.mock(Component.class);
        Component end = Mockito.mock(Component.class);
        QuickView<Integer> quickView = new QuickView<Integer>(
                "quickview", dataProvider, start, end) {
            @Override
            protected void populate(Item item) {
            }
        };

        QuickView<Integer> spy = Mockito.spy(quickView);
        Synchronizer synchronizer = Mockito.mock(Synchronizer.class);
        Mockito.doReturn(true).when(spy).isAjax();
        Mockito.doReturn(null).when(spy)._getRequestMetaData(spy.SYNCHRONIZER_KEY);
        Mockito.doReturn(synchronizer).when(spy).nonARTSynchronizer();
        Mockito.doNothing().when(spy)._setRequestMetaData(spy.SYNCHRONIZER_KEY, synchronizer);
        Synchronizer result = spy.getSynchronizer();
        assertEquals(result, synchronizer);
        Mockito.verify(spy)._setRequestMetaData(spy.SYNCHRONIZER_KEY, synchronizer);
    }

    /**
     * synchronizer does NOT exist in requestcycle
     * and request handler is AjaxRequestTarget based
     */
    @WicketTest
    public void testGetSynchronizer_3() {
        List<Integer> data = data(10);
        IDataProvider<Integer> dataProvider = new ListDataProvider<Integer>(data);
        Component start = Mockito.mock(Component.class);
        Component end = Mockito.mock(Component.class);
        QuickView<Integer> quickView = new QuickView<Integer>(
                "quickview", dataProvider, start, end) {
            @Override
            protected void populate(Item item) {
            }
        };
        AjaxRequestTarget target = Mockito.mock(AjaxRequestTarget.class);
        QuickView<Integer> spy = Mockito.spy(quickView);
        Mockito.doReturn(target).when(spy).getAjaxRequestTarget();
        DefaultSynchronizer synchronizer = Mockito.mock(DefaultSynchronizer.class);
        Mockito.doReturn(synchronizer).when(spy).newDefaultSynchronizer();
        Mockito.doReturn(true).when(spy).isAjax();
        Mockito.doReturn(null).when(spy)._getRequestMetaData(spy.SYNCHRONIZER_KEY);
        Mockito.doNothing().when(spy)._setRequestMetaData(spy.SYNCHRONIZER_KEY, synchronizer);
        Synchronizer result = spy.getSynchronizer();
        assertEquals(result, synchronizer);
        Mockito.verify(spy)._setRequestMetaData(spy.SYNCHRONIZER_KEY, synchronizer);
    }

    /**
     * case: IPartialPageRequestHandler is AjaxRequestTarget based
     */
    @WicketTest
    public void testRegister_1() {
        IDataProvider<Integer> dataProvider = new ListDataProvider<Integer>();
        QuickView<Integer> quickView = new QuickView<Integer>(
                "quickview", dataProvider) {
            @Override
            protected void populate(Item item) {
            }
        };

        quickView.register(AjaxRequestTarget.class);
        assertFalse(quickView.getPartialRequestHandlers().contains(AjaxRequestTarget.class));
    }

    /**
     * case :partial page request handler is not AjaxRequestTarget based
     */
    @WicketTest
    public void testRegister_2() {
        IDataProvider<Integer> dataProvider = new ListDataProvider<Integer>();
        QuickView<Integer> quickView = new QuickView<Integer>(
                "quickview", dataProvider) {
            @Override
            protected void populate(Item item) {
            }
        };
        quickView.register(ICustomRequestHandler.class);
        assertTrue(quickView.getPartialRequestHandlers().contains(ICustomRequestHandler.class));
    }


    @WicketTest
    public void testAddOrderIterator_1() {
        IDataProvider<Integer> dataProvider = new ListDataProvider<Integer>();
        QuickView<Integer> quickView = new QuickView<Integer>(
                "quickview", dataProvider) {
            @Override
            protected void populate(Item item) {
            }
        };
        quickView.initializeAddAtStartStoreIfRequired();
        Item item1 = new Item("1", 0);
        quickView.add(item1);


        Item item2 = new Item("2", 1);
        quickView.add(item2);

        Item item3 = new Item("3", 2);
        quickView.addAtStart(item3);

        Item item4 = new Item("4", 3);
        quickView.addAtStart(item4);

        Item item5 = new Item("5", 4);
        quickView.addAtStart(item5);

        Item item6 = new Item("6", 5);
        quickView.add(item6);

        Item item7 = new Item("7", 6);
        quickView.add(item7);

        Item item8 = new Item("8", 7);
        quickView.addAtStart(item8);

        Iterator<Component> iterator = quickView.getItems();
        List<Component> fetched = new ArrayList<>();
        while (iterator.hasNext()) {
            fetched.add(iterator.next());
        }
        assertEquals(fetched.size(), 8);
        assertEquals(fetched.get(0).getId(), item8.getId());
        assertEquals(fetched.get(1).getId(), item5.getId());
        assertEquals(fetched.get(2).getId(), item4.getId());
        assertEquals(fetched.get(3).getId(), item3.getId());
        assertEquals(fetched.get(4).getId(), item1.getId());
        assertEquals(fetched.get(5).getId(), item2.getId());
        assertEquals(fetched.get(6).getId(), item6.getId());
        assertEquals(fetched.get(7).getId(), item7.getId());
    }

    /**
     * case:only add at end
     */
    @WicketTest
    public void testAddOrderIterator_2() {
        IDataProvider<Integer> dataProvider = new ListDataProvider<Integer>();
        QuickView<Integer> quickView = new QuickView<Integer>(
                "quickview", dataProvider) {
            @Override
            protected void populate(Item item) {
            }
        };
        quickView.initializeAddAtStartStoreIfRequired();
        Item item1 = new Item("1", 0);
        quickView.add(item1);

        Item item2 = new Item("2", 1);
        quickView.add(item2);

        Item item3 = new Item("3", 2);
        quickView.add(item3);

        Iterator<Component> iterator = quickView.getItems();
        List<Component> fetched = new ArrayList<>();
        while (iterator.hasNext()) {
            fetched.add(iterator.next());
        }
        assertEquals(fetched.size(), 3);
        assertEquals(fetched.get(0).getId(), item1.getId());
        assertEquals(fetched.get(1).getId(), item2.getId());
        assertEquals(fetched.get(2).getId(), item3.getId());

    }

    /**
     * case:only add at start
     */
    @WicketTest
    public void testAddOrderIterator_3() {
        IDataProvider<Integer> dataProvider = new ListDataProvider<Integer>();
        QuickView<Integer> quickView = new QuickView<Integer>(
                "quickview", dataProvider) {
            @Override
            protected void populate(Item item) {
            }
        };
        quickView.initializeAddAtStartStoreIfRequired();
        Item item1 = new Item("1", 0);
        quickView.addAtStart(item1);

        Item item2 = new Item("2", 1);
        quickView.addAtStart(item2);

        Item item3 = new Item("3", 2);
        quickView.addAtStart(item3);


        Iterator<Component> iterator = quickView.getItems();
        List<Component> fetched = new ArrayList<>();
        while (iterator.hasNext()) {
            fetched.add(iterator.next());
        }
        assertEquals(fetched.size(), 3);
        assertEquals(fetched.get(0).getId(), item3.getId());
        assertEquals(fetched.get(1).getId(), item2.getId());
        assertEquals(fetched.get(2).getId(), item1.getId());

    }

    /**
     *
     */
    @WicketTest
    public void testIterator_remove_1() {
        IDataProvider<Integer> dataProvider = new ListDataProvider<Integer>();
        QuickView<Integer> quickView = new QuickView<Integer>(
                "quickview", dataProvider) {
            @Override
            protected void populate(Item item) {
            }
        };
        quickView.initializeAddAtStartStoreIfRequired();
        Item item1 = new Item("1", 0);
        quickView.addAtStart(item1);

        Item item2 = new Item("2", 1);
        quickView.addAtStart(item2);

        Item item3 = new Item("3", 2);
        quickView.addAtStart(item3);

        Item item4 = new Item("4", 3);
        quickView.addAtStart(item4);

        Item item5 = new Item("5", 4);
        quickView.add(item5);


        Iterator<Component> iterator = quickView.getItems();
        iterator.next();
        iterator.next();
        iterator.remove();
        List<Component> fetched = new ArrayList<>();
        while (iterator.hasNext()) {
            Component component = iterator.next();
            fetched.add(component);
        }
        assertEquals(quickView.size(),4);
        assertEquals(fetched.size(), 3);
        assertEquals(fetched.get(0).getId(), item2.getId());
        assertEquals(fetched.get(1).getId(), item1.getId());
        assertEquals(fetched.get(2).getId(),item5.getId());
    }


    public AjaxRequestTarget mockTarget() {
        AjaxRequestTarget target = mock(AjaxRequestTarget.class);
        return target;
    }

    public Iterator<TestObj> mockIterator() {
        Iterator<TestObj> it = mock(Iterator.class);
        return it;
    }


    List<Integer> data(int size) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        return list;
    }

    public IDataProvider<TestObj> mockProvider(long size) {
        IDataProvider<TestObj> dp = mock(IDataProvider.class);
        when(dp.size()).thenReturn(size);
        return dp;
    }


    IRepeaterUtil mockRepeaterUtil() {
        return mock(IRepeaterUtil.class);

    }

    public static interface ICustomRequestHandler extends IPartialPageRequestHandler {

    }

}
