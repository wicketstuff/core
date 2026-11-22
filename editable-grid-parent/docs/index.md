---
title: Editable Grid
category: data
description: A data grid with adding, editing and deleting of rows built in.
artifactId: wicketstuff-editable-grid
tags: table crud
---

A grid component with add/edit/delete feature all at once, apart from supporting sorting/filtering/paging.

## Features

* Add Row
* Edit Row
* Delete Row

## Maven

```xml
<dependency>
	<groupId>org.wicketstuff</groupId>
	<artifactId>wicketstuff-editable-grid</artifactId>
</dependency>
```

Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

## Java

```java
add(new EditableGrid<Person, String>("grid", getColumns(), new EditableListDataProvider<Person>(getPersons(), "name"), 5, Person.class) {
    private static final long serialVersionUID = 1L;

    @Override
    protected void onError(final AjaxRequestTarget target, final IModel<Person> rowModel) {
        super.onError(target, rowModel);
        target.add(feedbackPanel);
    }

    @Override
    protected void onCancel(final AjaxRequestTarget target, final IModel<Person> rowModel) {
        super.onCancel(target, rowModel);
        target.add(feedbackPanel);
    }

    @Override
    protected void onDelete(final AjaxRequestTarget target, final IModel<Person> rowModel) {
        super.onDelete(target, rowModel);
        target.add(feedbackPanel);
    }

    @Override
    protected void onSave(final AjaxRequestTarget target, final IModel<Person> rowModel) {
        super.onSave(target, rowModel);
        target.add(feedbackPanel);
    }
});

private List<AbstractEditablePropertyColumn<Person, String>> getColumns() {
    List<AbstractEditablePropertyColumn<Person, String>> columns = new ArrayList<>();
    columns.add(new RequiredTextFieldColumn<Person, String>(new Model<String>("Name"), "name", "name"));
    columns.add(new RequiredTextFieldColumn<Person, String>(new Model<String>("Address"), "address"));
    columns.add(new RequiredDropDownColumn<Person, Integer, String>(new Model<String>("Age"), "age", List.of(10, 11, 12, 13, 14, 15), "age"));
    columns.add(new CheckBoxColumn<Person, String>(new Model<String>("Married?"), "married"));
    return columns;
}
```

## HTML

```html
<div wicket:id="grid"></div>
```

## Project maintainers

[mnadeem](https://github.com/mnadeem)

## Comparison

### InMethod Grid and Editable Grid

InMethod Grid is really awesome++, however it does not allow us to add a row; moreover editable components are not configurable, it is always a text field. What if I want a drop down, or a textarea?

The [`editable-grid-examples`](https://github.com/wicketstuff/core/tree/wicket-10.x/editable-grid-parent/editable-grid-examples) module contains a runnable example application.

Source: [`editable-grid-parent`](https://github.com/wicketstuff/core/tree/wicket-10.x/editable-grid-parent)
