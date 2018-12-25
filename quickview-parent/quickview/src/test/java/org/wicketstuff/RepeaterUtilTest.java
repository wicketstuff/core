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


import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vineet Semwal
 */
public class RepeaterUtilTest {

    @Test(groups = {"utilTests"})
    public void get_1() {
        WicketTester tester = new WicketTester(createMockApplication());
        RepeaterUtil util = RepeaterUtil.get();
        RepeaterUtil util2 = RepeaterUtil.get();
        Assert.assertNotNull(util);
        Assert.assertTrue(util == util2);
    }

    @Test(groups = {"utilTests"})
    public void prepend_1() {
        WicketTester tester = new WicketTester(createMockApplication());
        final String child = "child", parent = "parent", tag = "div", start = "start", end = "end";
        String actual = RepeaterUtil.get().prepend(tag, child, parent, start, end);
        String expected = "QuickView.prepend('div','child','parent','start','end');";
        Assert.assertEquals(actual.trim(), expected.trim());
    }


    /**
     * test without boundaries
     */

    @Test(groups = {"utilTests"})
    public void prepend_2() {
        QuickMockApplication app = new QuickMockApplication();
        WicketTester tester = new WicketTester(app);
        final QuickViewParent parent = new QuickViewParent("parent");
        List<Integer> list = new ArrayList<Integer>();
        list.add(100);
        IDataProvider<Integer> dataProvider = new ListDataProvider<Integer>(list);
        final QuickView<Integer> quickView = new QuickView<Integer>("quickview", dataProvider, 2, null, null) {
            @Override
            protected void populate(Item<Integer> item) {
            }
        };
        quickView.setReuseStrategy(new ItemsNavigationStrategy());
        parent.setOutputMarkupId(true);
        parent.add(quickView);

        TestQuickViewContainer panel = new TestQuickViewContainer("panel") {

            @Override
            public QuickViewParent newParent() {
                return parent;
            }
        };
        tester.startComponentInPage(panel);
        Item newItemAtStart = new Item("363", 37, new Model());
        quickView.simpleAdd(newItemAtStart);
        String expected = String.format("QuickView.prepend('%s','%s','%s','','');", TestQuickViewContainer.TAG_NAME,
                newItemAtStart.getMarkupId(), parent.getMarkupId());
        String actual = RepeaterUtil.get().prepend(newItemAtStart, parent, null, null);
        Assert.assertEquals(actual.trim(), expected.trim());
    }


    /**
     * test with boundaries
     */
    @Test(groups = {"utilTests"})
    public void prepend_3() {
        QuickMockApplication app = new QuickMockApplication();
        WicketTester tester = new WicketTester(app);
        List<Integer> list = new ArrayList<Integer>();
        list.add(100);
        IDataProvider<Integer> dataProvider = new ListDataProvider<Integer>(list);
        EmptyPanel start = new EmptyPanel("start");
        start.setMarkupId("start");
        EmptyPanel end = new EmptyPanel("end");
        end.setMarkupId("end");
        final QuickView<Integer> quickView = new QuickView<Integer>("quickview", dataProvider, 2, start, end) {
            @Override
            protected void populate(Item<Integer> item) {
            }
        };
        quickView.setReuseStrategy(new ItemsNavigationStrategy());


        TestQuickViewContainerWithBoundaries panel = new TestQuickViewContainerWithBoundaries("panel") {
            @Override
            public QuickViewBase newView() {
                return quickView;
            }
        };
        panel.setOutputMarkupPlaceholderTag(true);
        panel.add(start);
        panel.add(end);
        tester.startComponentInPage(panel);
        Item newItemAtStart = new Item("363", 37, new Model());
        quickView.simpleAdd(newItemAtStart);
        String expected = String.format("QuickView.prepend('%s','%s','%s','start','end');", TestQuickViewContainer.TAG_NAME,
                newItemAtStart.getMarkupId(), panel.getMarkupId());
        String actual = RepeaterUtil.get().prepend(newItemAtStart, panel, start, end);
        Assert.assertEquals(actual.trim(), expected.trim());
    }

    /**
     * check with testpanel2
     */

    @Test(groups = {"utilTests"})
    public void getComponentTag_2() {

        QuickMockApplication app = new QuickMockApplication();
        WicketTester tester = new WicketTester(app);
        final QuickViewParent parent = new QuickViewParent("parent");
        List<Integer> list = new ArrayList<Integer>();
        list.add(100);
        IDataProvider<Integer> dataProvider = new ListDataProvider<Integer>(list);
        final QuickView<Integer> quickView = new QuickView<Integer>("quickview", dataProvider, 2) {
            @Override
            protected void populate(Item<Integer> item) {
            }
        };
        quickView.setReuseStrategy(new ItemsNavigationStrategy());
        parent.setOutputMarkupId(true);
        parent.add(quickView);


        TestQuickViewContainer panel = new TestQuickViewContainer("panel") {
            @Override
            public QuickViewParent newParent() {
                return parent;
            }
        };
        tester.startComponentInPage(panel);
        Item newItem = new Item("363", 37, new Model());
        quickView.add(newItem);
        ComponentTag actual = RepeaterUtil.get().getComponentTag(newItem);
        Assert.assertEquals(actual.getName(), TestQuickViewContainer.TAG_NAME);
    }


    @Test(groups = {"utilTests"})
    public void append_1() {
        WicketTester tester = new WicketTester(createMockApplication());
        final String child = "child", parent = "parent", tag = "div", start = "start20", end = "end30";
        String call = RepeaterUtil.get().append(tag, child, parent, start, end);
        String expected = "QuickView.append('div','child','parent','start20','end30');";
        Assert.assertEquals(call, expected);
    }


    /**
     * test without boundaries
     */
    @Test(groups = {"utilTests"})
    public void append_2() {
        QuickMockApplication app = new QuickMockApplication();
        WicketTester tester = new WicketTester(app);
        final QuickViewParent parent = new QuickViewParent("parent");
        List<Integer> list = new ArrayList<Integer>();
        list.add(100);
        IDataProvider<Integer> dataProvider = new ListDataProvider<Integer>(list);
        final QuickView<Integer> quickView = new QuickView<Integer>("quickview", dataProvider, 2) {
            @Override
            protected void populate(Item<Integer> item) {
            }
        };
        quickView.setReuseStrategy(new ItemsNavigationStrategy());
        parent.setOutputMarkupId(true);
        parent.add(quickView);

        TestQuickViewContainer panel = new TestQuickViewContainer("panel") {
            @Override
            public QuickViewParent newParent() {
                return parent;
            }
        };
        tester.startComponentInPage(panel);
        Item newItem = new Item("363", 37, new Model());
        quickView.simpleAdd(newItem);
        String expected = String.format("QuickView.append('%s','%s','%s','','');",
                TestQuickViewContainer.TAG_NAME, newItem.getMarkupId(), parent.getMarkupId());
        String actual = RepeaterUtil.get().append(newItem, parent, null, null);
        Assert.assertEquals(actual.trim(), expected.trim());
    }


    /**
     * test with boundaries
     */
    @Test(groups = {"utilTests"})
    public void append_3() {
        QuickMockApplication app = new QuickMockApplication();
        WicketTester tester = new WicketTester(app);
        List<Integer> list = new ArrayList<Integer>();
        list.add(100);
        IDataProvider<Integer> dataProvider = new ListDataProvider<Integer>(list);
        EmptyPanel start = new EmptyPanel("start");
        start.setMarkupId("start");
        EmptyPanel end = new EmptyPanel("end");
        end.setMarkupId("end");
        final QuickView<Integer> quickView = new QuickView<Integer>("quickview", dataProvider, 2, start, end) {
            @Override
            protected void populate(Item<Integer> item) {
            }
        };
        quickView.setReuseStrategy(new ItemsNavigationStrategy());

        TestQuickViewContainerWithBoundaries panel = new TestQuickViewContainerWithBoundaries("panel") {
            @Override
            public QuickViewBase newView() {
                return quickView;
            }
        };
        panel.add(start);
        panel.add(end);
        panel.setOutputMarkupPlaceholderTag(true);
        tester.startComponentInPage(panel);
        Item newItem = new Item("363", 37, new Model());
        quickView.simpleAdd(newItem);
        String expected = String.format("QuickView.append('%s','%s','%s','start','end');",
                TestQuickViewContainer.TAG_NAME, newItem.getMarkupId(),
                panel.getMarkupId(), start.getMarkupId(), end.getMarkupId());
        String actual = RepeaterUtil.get().append(newItem, panel, start, end);
        Assert.assertEquals(actual.trim(), expected.trim());
    }

    @Test(groups = {"utilTests"})
    public void removeItem_1() {
        final String itemId = "67", parentId = "p1";
        final String expected = "QuickView.removeItem('67','p1');";
        final String actual = RepeaterUtil.get().removeItem(itemId, parentId);
        Assert.assertEquals(actual.trim(), expected.trim());
    }

    @Test(groups = {"utilTests"})
    public void removeItem() {
        final String itemMarkupId = "76";
        Item item = Mockito.mock(Item.class);
        Mockito.when(item.getMarkupId()).thenReturn(itemMarkupId);
        Component parent = new WebMarkupContainer("p");
        parent.setMarkupId("p1");
        final String actual = RepeaterUtil.get().removeItem(item, parent);
        final String expected = "QuickView.removeItem('76','p1');";
        Assert.assertEquals(actual.trim(), expected.trim());
    }


    @Test(groups = {"utilTests"})
    public void scrollToTop() {
        final String repeaterMarkupId = "quick";
        final String expected = "QuickView.scrollToTop('quick');";
        final String actual = RepeaterUtil.get().scrollToTop(repeaterMarkupId);
        Assert.assertEquals(actual.trim(), expected.trim());
    }

    @Test(groups = {"utilTests"})
    public void scrollToBottom() {
        final String repeaterMarkupId = "quick";
        final String expected = "QuickView.scrollToBottom('quick');";
        final String actual = RepeaterUtil.get().scrollToBottom(repeaterMarkupId);
        Assert.assertEquals(actual.trim(), expected.trim());
    }

    @Test(groups = {"utilTests"})
    public void scrollTo_1() {
        final int height = 7;
        final String repeaterMarkupId = "quick";
        final String expected = "QuickView.scrollTo('quick',7);";
        final String actual = RepeaterUtil.get().scrollTo(repeaterMarkupId, height);
        Assert.assertEquals(actual.trim(), expected.trim());
    }

    @Test(groups = {"utilTests"})
    public void scrollTo_2() {
        final int height = 9;
        final String repeaterMarkupId = "quick";
        final String expected = "QuickView.scrollTo('quick',9);";
        final String actual = RepeaterUtil.get().scrollTo(repeaterMarkupId, height);
        Assert.assertEquals(actual.trim(), expected.trim());
    }

    /**
     * integer overflow
     */
    @Test(groups = {"utilTests"}, expectedExceptions = IllegalArgumentException.class)
    public void safeLongToInt_1() {
        long val = (long) Integer.MAX_VALUE + 1l;
        RepeaterUtil.get().safeLongToInt(val);
    }

    /**
     * integer overflow
     */
    @Test(groups = {"utilTests"}, expectedExceptions = IllegalArgumentException.class)
    public void safeLongToInt_2() {
        long val = (long) Integer.MIN_VALUE - (long) 1;
        RepeaterUtil.get().safeLongToInt(val);
    }

    /**
     * integer doesn't overflow
     */
    @Test(groups = {"utilTests"})
    public void safeLongToInt_3() {
        int min = RepeaterUtil.get().safeLongToInt(Integer.MIN_VALUE);
        Assert.assertEquals(min, Integer.MIN_VALUE);
        int max = RepeaterUtil.get().safeLongToInt(Integer.MAX_VALUE);
        Assert.assertEquals(max, Integer.MAX_VALUE);
    }

    private static WebApplication createMockApplication() {
        WebApplication app = new QuickMockApplication();
        return app;
    }

    @Test(groups = {"utilTests"}, expectedExceptions = RepeaterUtil.ReuseStrategyNotSupportedException.class)
    public void reuseStategyNotSupportedForItemsNavigation_1() {
        IQuickView quickView = Mockito.mock(IQuickView.class);
        IQuickReuseStrategy strategy = Mockito.mock(IQuickReuseStrategy.class);
        Mockito.when(strategy.isPartialUpdatesSupported()).thenReturn(false);
        Mockito.when(quickView.getReuseStrategy()).thenReturn(strategy);
        RepeaterUtil.get().reuseStategyNotSupportedForItemsNavigation(quickView);
    }


    @Test(groups = {"utilTests"})
    public void reuseStategyNotSupportedForItemsNavigation_5() {
        IQuickView quickView = Mockito.mock(IQuickView.class);
        IQuickReuseStrategy strategy = Mockito.mock(IQuickReuseStrategy.class);
        Mockito.when(strategy.isPartialUpdatesSupported()).thenReturn(true);
        Mockito.when(quickView.getReuseStrategy()).thenReturn(strategy);

        RepeaterUtil.get().reuseStategyNotSupportedForItemsNavigation(quickView);
    }


    @Test(groups = {"utilTests"}, expectedExceptions = RepeaterUtil.OutputMarkupIdNotTrueException.class)
    public void outPutMarkupIdNotTrue_1() {
        IDataProvider data = Mockito.mock(IDataProvider.class);
        IQuickReuseStrategy strategy = Mockito.mock(IQuickReuseStrategy.class);
        Mockito.when(strategy.isPartialUpdatesSupported()).thenReturn(true);
        QuickView quickView = new QuickView("id", data, strategy) {
            @Override
            protected void populate(Item item) {
            }
        };
        WebMarkupContainer parent = new WebMarkupContainer("parent");
        parent.add(quickView);
        RepeaterUtil.get().outPutMarkupIdNotTrue(quickView);
    }

    @Test(groups = {"utilTests"})
    public void outPutMarkupIdNotTrue_2() {
        IDataProvider data = Mockito.mock(IDataProvider.class);
        IQuickReuseStrategy strategy = Mockito.mock(IQuickReuseStrategy.class);
        Mockito.when(strategy.isPartialUpdatesSupported()).thenReturn(true);
        QuickView quickView = new QuickView("id", data, strategy) {
            @Override
            protected void populate(Item item) {
            }
        };
        WebMarkupContainer parent = new WebMarkupContainer("parent");
        parent.add(quickView);
        parent.setOutputMarkupId(true);
        RepeaterUtil.get().outPutMarkupIdNotTrue(quickView);
    }

    @Test(groups = {"utilTests"})
    public void outPutMarkupIdNotTrue_3() {
        IDataProvider data = Mockito.mock(IDataProvider.class);
        IQuickReuseStrategy strategy = Mockito.mock(IQuickReuseStrategy.class);
        Mockito.when(strategy.isPartialUpdatesSupported()).thenReturn(true);
        QuickView quickView = new QuickView("id", data, strategy) {
            @Override
            protected void populate(Item item) {
            }
        };

        WebMarkupContainer parent = new WebMarkupContainer("parent");
        parent.add(quickView);
        parent.setOutputMarkupPlaceholderTag(true);
        RepeaterUtil.get().outPutMarkupIdNotTrue(quickView);
    }

    /**
     * parent=null,reuse= paging
     */
    @Test(groups = {"utilTests"})
    public void parentNotSuitable_1() {
        IQuickView quickView = Mockito.mock(IQuickView.class);
        IQuickReuseStrategy strategy = Mockito.mock(IQuickReuseStrategy.class);
        Mockito.when(strategy.isPartialUpdatesSupported()).thenReturn(false);
        Mockito.when(quickView.getReuseStrategy()).thenReturn(strategy);
        RepeaterUtil.get().parentNotSuitable(quickView);
    }


    /**
     * parent=null,reuse= not paging
     */
    @Test(groups = {"utilTests"}, expectedExceptions = RepeaterUtil.QuickViewNotAddedToParentException.class)
    public void parentNotSuitable_2() {
        IQuickView quickView = Mockito.mock(IQuickView.class);
        IQuickReuseStrategy strategy = Mockito.mock(IQuickReuseStrategy.class);
        Mockito.when(strategy.isPartialUpdatesSupported()).thenReturn(true);
        Mockito.when(quickView.getReuseStrategy()).thenReturn(strategy);
        RepeaterUtil.get().parentNotSuitable(quickView);
    }

    /**
     * parent=page,reuse= not paging
     */
    @Test(groups = {"utilTests"}, expectedExceptions = RepeaterUtil.QuickViewNotAddedToParentException.class)
    public void parentNotSuitable_3() {
        IQuickReuseStrategy strategy = Mockito.mock(IQuickReuseStrategy.class);
        Mockito.when(strategy.isPartialUpdatesSupported()).thenReturn(true);
        WebPage parent = Mockito.mock(WebPage.class);
        IQuickView quickView = Mockito.mock(IQuickView.class);
        Mockito.when(quickView.getParent()).thenReturn(parent);
        Mockito.when(quickView.getReuseStrategy()).thenReturn(strategy);
        RepeaterUtil.get().parentNotSuitable(quickView);
    }


    /**
     * parent children size=1,reuse= not paging
     */
    @Test(groups = {"utilTests"})
    public void parentNotSuitable_6() {
        IQuickReuseStrategy strategy = Mockito.mock(IQuickReuseStrategy.class);
        Mockito.when(strategy.isPartialUpdatesSupported()).thenReturn(true);
        WebMarkupContainer parent = Mockito.mock(WebMarkupContainer.class);
        Mockito.when(parent.size()).thenReturn(1);
        IQuickView quickView = Mockito.mock(IQuickView.class);
        Mockito.when(quickView.getParent()).thenReturn(parent);
        Mockito.when(quickView.getReuseStrategy()).thenReturn(strategy);
        RepeaterUtil.get().parentNotSuitable(quickView);
    }


    @Test(groups = {"utilTests"})
    public void isComponentScrollBarAtBottom() {
        WebMarkupContainer c = new WebMarkupContainer("parent");
        c.setMarkupId("parent");
        String actual = RepeaterUtil.get().isComponentScrollBarAtBottom(c);
        String expected = "QuickView.isComponentScrollBarAtBottom('parent');";
        Assert.assertEquals(actual, expected);
    }

    @Test(groups = {"utilTests"})
    public void isPageScrollBarAtBottom() {
        String actual = RepeaterUtil.get().isPageScrollBarAtBottom();
        String expected = "QuickView.isPageScrollBarAtBottom();";
        Assert.assertEquals(actual, expected);
    }


}
