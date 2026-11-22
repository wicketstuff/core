---
title: Minis
category: ui
description: Assorted small components and behaviors that are too small to warrant a project of their own.
artifactId: wicketstuff-minis
tags: misc
---

Wicket Stuff Minis is a collection of assorted components and behaviors that are too small to warrant their own project.

## Maven

```xml
<dependency>
	<groupId>org.wicketstuff</groupId>
	<artifactId>wicketstuff-minis</artifactId>
</dependency>
```

Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

## Behaviors & Validators

### ImageDimensionProvider & AbstractImageDimensionProvider

Simple behaviors to provide width + height attributes for html img tags. Specifying dimensions can speed up page rendering.

### StringValidatorWithMaxLength

Similar to Wicket's `StringValidator` this class can be used to construct exact length, max length, length between validators. The difference is that these validators also add a "maxlength" attribute to the html input element they are bound to.

## Components

### DefaultInvisibleFeedbackPanel

A simple `FeedbackPanel` variant which is only visible if there are messages attached to it. Useful if your designer is putting always visible elements (like border, sign image) on your feedback panels.

### ReplacingResourceModel

The `ReplacingResourceModel` is used to replace other keys marked up like `${key}` with their corresponding values in the given key. The replacing mechanism also uses the default way to get the localized String.

Java:

```java
add(new Label("label", new ReplacingResourceModel("key2")));
```

HTML:

```html
<span wicket:id="label"></span>
```

Properties:

```properties
key1=value of key1
key2=value of key2 contains ${key1}
```

Output:

```text
value of key2 contains value of key1
```

## Dependencies

Wicket Stuff Minis depends only on [Wicket](https://wicket.apache.org/).

## Further reading

The above lists of behaviors and components are not complete. For more info see the javadoc of the classes.

The [`minis-examples`](https://github.com/wicketstuff/core/tree/wicket-11.x/minis-parent/minis-examples) module contains a runnable example application.

Source: [`minis-parent`](https://github.com/wicketstuff/core/tree/wicket-11.x/minis-parent)
