---
title: QuickView
category: ui
description: "Repeaters that add items without re-rendering the whole list: partial updates and infinite scroll."
artifactId: quickview
tags: repeater listview scroll
---

This library contains repeaters which support partial updates in Ajax use cases.

`QuickView` is a Wicket component (`RepeatingView`). It lets you add or remove the rows without the need to re-render the whole repeater again in Ajax use cases. The way of using `QuickView` is almost like `DataView`.

`QuickGridView` renders items in rows and columns in table/grid format. It also lets you add or remove the rows without the need to re-render the whole repeater again in Ajax use cases. The way of using `QuickGridView` is almost like `GridView`.

## Maven

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>quickview</artifactId>
</dependency>
```

Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

## Why is it useful?

The repeaters Wicket has, like `DataView`, are amazing but they can't be partially updated with Ajax so you can't basically render new rows unless you add the parent to the `AjaxRequestTarget` (which will cause re-rendering of the whole repeater), or say if you remove a row you can only show your change to the user by adding the parent to the `AjaxRequestTarget`. This can work in some scenarios but it might not be acceptable at places where performance is the concern: you just don't want to retrieve the whole data again and then render all of that again. Not good at server side, not good at client/user side.

QuickView solves this problem for almost all of these scenarios. It can just render new row(s) in the end or at the start without the need to re-render the whole repeater, depending on where you want to add the new rows; similarly you can remove the row(s). The way of using QuickView is exactly like `DataView`; in fact by default QuickView works fine in case of paging (i.e. in case where the whole view is rendered). QuickView by default uses `DefaultQuickReuseStrategy` (which implements `IQuickReuseStrategy`). See the examples to see the use of different strategies and how they are used.

**Please note `IQuickReuseStrategy` is the replacement for the `Reuse` constant used in earlier versions.**

## Add new row in QuickView

```java
// start and end are boundaries that are used to determine start and end of
// quickview in markup
Component start = new EmptyPanel("start");
Component end = new EmptyPanel("end");
IDataProvider<Integer> data = new ListDataProvider<Integer>(list);
WebMarkupContainer numbers = new WebMarkupContainer("numbers");   //quickview's parent
numbers.setOutputMarkupId(true);  //needed for ajax
final QuickView<Integer> number = new QuickView<Integer>("number", data, start, end)
{
    @Override
    protected void populate(Item<Integer> item) {
        item.add(new Label("display", item.getModel()));
    }
};
numbers.add(number);
add(numbers);

AjaxLink<Void> addLink = new AjaxLink<Void>("addLink") {
    @Override
    public void onClick(AjaxRequestTarget target) {
        int newObject = list.get(list.size() - 1) + 1;
        list.add(newObject);
        //just enough to create and add a new item in the end
        number.addNewItems(newObject);
    }
};
add(addLink);
```

A complete example is provided in the examples module.

The above code might look very familiar to you: using QuickView looks exactly like a `DataView`. In the `AjaxLink`'s `onClick` you create a new item by just using `quickview.addNewItems(newObject)`. Adding the item to QuickView also means a new row/item will be rendered. There is no need to add QuickView's parent to the `AjaxRequestTarget` in the above code and you should also NOT do that, as it will mean the whole QuickView is rendered again, which is what you don't want in this case.

## Add new row in QuickGridView

```java
Component start = new EmptyPanel("start");
Component end = new EmptyPanel("end");
IDataProvider<Integer> data = new ListDataProvider<Integer>(list);
WebMarkupContainer numbers = new WebMarkupContainer("numbers");   //parent for quickview
numbers.setOutputMarkupId(true);  //needed for ajax
final QuickGridView<Integer> number = new QuickGridView<Integer>("number", data, start, end) {
    @Override
    protected void populate(CellItem<Integer> item) {
        item.add(new Label("display", item.getModel()));
    }

    @Override
    protected void populateEmptyItem(CellItem<Integer> item) {
        item.add(new Label("display"));
    }
};
number.setColumns(2);
numbers.add(number);
add(numbers);

AjaxLink<Void> addLink = new AjaxLink<Void>("addLink") {

    @Override
    public void onClick(AjaxRequestTarget target) {
        int newObject = list.get(list.size() - 1) + 1;
        list.add(newObject);
        int newObject2 = list.get(list.size() - 1) + 1;
        list.add(newObject2);
        List<Integer> newOnes = new ArrayList<Integer>();
        newOnes.add(newObject);
        newOnes.add(newObject2);
        //just enough to add new rows and corresponding cells
        number.addRows(newOnes.iterator());
    }
};
```

A complete example is provided in the examples module.

## AjaxItemsNavigator

`AjaxItemsNavigator` is a navigator which creates new rows on click. It works like what you see in social networking applications: click on a link **more** to get more feeds/updates. The way to use it is very simple:

```java
AjaxItemsNavigator navigator = new AjaxItemsNavigator("navigator", quickView);
add(navigator);
```

Just create a QuickView and pass it to the navigator, and that's it.

### AjaxItemsNavigator with QuickGridView

```java
AjaxItemsNavigator navigator = new AjaxItemsNavigator("nav", gridView);
add(navigator);
```

Just create a `QuickGridView` and pass it to the navigator, and that's it. A complete example is provided in the examples module.

## AjaxPageScrollEventBehavior

An `AjaxEventBehavior` which fires an event when the scroll bar of the page is moved to the bottom; otherwise it just ignores the "scroll" events. When you implement `onScroll` just call the behavior's `addItemsForNextPage(quickview)` and new rows will be created when the scroll event is fired. Add this behavior to the page, for example:

```java
//behavior added to page
add(new AjaxPageScrollEventBehavior(){
    @Override
    protected void onScroll(AjaxRequestTarget target) {
        addItemsForNextPage(quickView);
    }
});
```

A complete example is provided in the examples module.

## AjaxComponentScrollEventBehavior

An `AjaxEventBehavior` which fires an event when the scroll bar of QuickView's parent is moved to the bottom; otherwise it just ignores the "scroll" events. When you implement `onScroll` just call the behavior's `addItemsForNextPage(quickview)` and new rows will be created when the scroll event is fired. Add this behavior to QuickView's parent, for example:

```java
//parent is quickview's parent and behavior is added to it
parent.add(new AjaxComponentScrollEventBehavior(){
    @Override
    protected void onScroll(AjaxRequestTarget target) {
        addItemsForNextPage(quickView);
    }
});
```

A complete example is provided in the examples module.

## Paging

QuickView works well with `PagingNavigator` or `AjaxPagingNavigator`. For example, to use it with `AjaxPagingNavigator`:

```java
IDataProvider<Integer> data = new ListDataProvider<Integer>(list);
final int itemsPerRequest = 4;//rows created per request
// quickview by default works fine for paging
QuickView<Integer> quickView = new QuickView<Integer>("number", data, itemsPerRequest) {
    @Override
    protected void populate(Item<Integer> item) {
        item.add(new Label("display", item.getModel()));
    }
};
//don't forget adding quickview to parent with any ajax navigator
WebMarkupContainer numbers = new WebMarkupContainer("numbers");
numbers.add(quickView);
numbers.setOutputMarkupId(true); //don't forget, required when using ajax navigators
add(numbers);
AjaxPagingNavigator navigator = new AjaxPagingNavigator("navigator", quickView);
add(navigator);
```

There is nothing new to explain: just create a QuickView and pass it on to the navigator like you do with a `DataView`. A complete example is provided in the examples module.

The idea/inspiration to write QuickView came from Igor Vaynberg's article [Repainting only newly-created repeater items via Ajax](http://wicketinaction.com/2008/10/repainting-only-newly-created-repeater-items-via-ajax/).

The [`quickview-examples`](https://github.com/wicketstuff/core/tree/master/quickview-parent/quickview-examples) module contains a runnable example application.

Source: [`quickview-parent`](https://github.com/wicketstuff/core/tree/master/quickview-parent)
