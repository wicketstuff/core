---
title: ModalX
category: ui
description: Builds on Wicket's ModalDialog with managed modal forms, message boxes and close listeners.
artifactId: modalx
tags: dialog popup
---

A lightweight extension to Wicket's modal dialog capabilities that comes with standardized MessageBox class and allows easy definition of Modal dialog box classes, similar in nature to desktop application development in frameworks like MFC, .net, OWL, Swing etc.

* Wicket's `ModalDialog` is genericized as `ModalContentWindow` and is automatically managed. Definition of a new Modal Form needs only to extend a single class `ModalFormPanel`.
* Generic modal window management - open from anywhere in your application without explicitly defining the modal window tag markup in a component that might need it.
* MessageBox feature
* `IWindowCloseListener` - optional listener on any `ModalContentPanel` close event.
* ModalResult - a desktop framework concept. Allows a `IWindowCloseListener` to query the button the user pressed to close a modal window.

ModalX home page at Visual Classworks: [ModalX Home](http://www.visualclassworks.com/content/list/o/76914/type/modalx/)

## ModalX class diagram

This Visual Classworks/Javelin class diagram shows the core classes and how they relate to each other. The base class `ModalContentPanel` implements the behavior necessary to display itself inside a generic modal window (`ModalContentWindow`) instance which means the modal window has been genericized and can be effectively ignored when defining a new `ModalFormPanel` class.

![ModalX Class Diagram](http://ems.pagebloom.com/pagebloom/o/76914/images/modalX_JavelinDiagram.png)

## Maven

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>modalx</artifactId>
</dependency>
```

Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

## Java

Definition of a form class:

```java
public class TestForm extends ModalFormPanel {
    public TestForm(ModalContentWindow iModalContentWindow,
        IWindowCloseListener iWindowCloseListener, Person iPerson) {
        super(iModalContentWindow, iWindowCloseListener);

        person = iPerson;
        setTitle("Personal Details");
    }

    public void addFormComponents() {
        form.add(new RequiredTextField("firstName",
             new PropertyModel(person, "firstName")).setLabel(new Model("First Name")));
        form.add(new RequiredTextField("lastName",
             new PropertyModel(person, "lastName")).setLabel(new Model("Last Name")));
        etc.,
    }
}
```

Opening a form:

```java
TestForm testForm = new TestForm(allocateModalWindow(), null, person);

testForm.show(target);
```

## Project maintainers

Chris Colman

The [`modalx-examples`](https://github.com/wicketstuff/core/tree/master/modalx-parent/modalx-examples) module contains a runnable example application.

Source: [`modalx-parent`](https://github.com/wicketstuff/core/tree/master/modalx-parent)
