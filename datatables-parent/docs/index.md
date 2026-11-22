---
title: DataTables
category: js
description: Integration of the DataTables jQuery plugin for sortable, searchable tables.
artifactId: wicketstuff-datatables
tags: table grid
---

From [datatables.net](https://datatables.net/):

DataTables is a plug-in for the [jQuery](https://jquery.com/) JavaScript library. It is a highly flexible tool, based upon the foundations of progressive enhancement, which will add advanced interaction controls to any HTML table. Key features:

* Variable length pagination
* On-the-fly filtering
* Multi-column sorting with data type detection
* Smart handling of column widths
* Display data from almost any data source
* DOM, JavaScript array, Ajax file and server-side processing (PHP, C#, Perl, Ruby, AIR, Gears etc)
* Scrolling options for table viewport
* Fully internationalisable
* jQuery UI ThemeRoller support
* Rock solid - backed by a suite of 1400+ unit tests
* Wide variety of plug-ins inc. TableTools, FixedHeader, KeyTable and more
* And many more

## Example

Take a look at a simple HTML file displaying a table with 3 columns:

```html
<div style="width: 980px">
<table wicket:id="table" cellpadding="0" cellspacing="0" border="0" class="display" style="width: 980px">
        <thead>
                <tr>
                        <th>Column 1</th>
                        <th>Column 2</th>
                        <th>etc</th>
                </tr>
        </thead>
        <tbody>
                <tr wicket:id="rows">
                        <td wicket:id="col1">Row 1 Data 1</td>
                        <td wicket:id="col2">Row 1 Data 2</td>
                        <td wicket:id="col3">etc</td>
                </tr>
        </tbody>
</table>
</div>
```

Now take a look at the Java code:

```java
DemoDatatable table = new DemoDatatable("table");
add(table);

List<String[]> rows = ...

ListView<String[]> lv = new ListView<String[]>("rows", rows) {
    @Override
    protected void populateItem(ListItem<String[]> item) {
        String[] row = item.getModelObject();

        item.add(new Label("col1", row[0]));
        item.add(new Label("col2", row[1]));
        item.add(new Label("col3", row[2]));
    }
};

table.add(lv);
```

The output look and feel is exactly the same as on [datatables.net](https://datatables.net/).

## Maven

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-datatables</artifactId>
</dependency>
```

Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

The [`datatables-examples`](https://github.com/wicketstuff/core/tree/wicket-11.x/datatables-parent/datatables-examples) module contains a runnable example application.

Source: [`datatables-parent`](https://github.com/wicketstuff/core/tree/wicket-11.x/datatables-parent)
