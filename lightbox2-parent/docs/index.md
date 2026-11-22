---
title: Lightbox2
category: js
description: Show images in an overlay with the Lightbox2 plugin.
artifactId: lightbox2
tags: image gallery
---

[Lightbox](https://lokeshdhakar.com/projects/lightbox2/) is a simple, unobtrusive script used to overlay images on top of the current page. It's a snap to setup and works on all modern browsers.

## Example

Java code:

```java
add(new LightboxPanel("lightbox", "images/image-1.jpg", "images/thumb-1.jpg"));
```

HTML:

```html
<wicket:container wicket:id="lightbox"></wicket:container>
```

With a resource reference, Java code:

```java
ResourceReference image = new PackageResourceReference(HomePage.class,
			"resources/image-1.jpg");
ResourceReference thumbnail = new PackageResourceReference(HomePage.class,
			"resources/thumb-1.jpg");
add(new LightboxLink("link", image).add(new Image("image", thumbnail)));
```

HTML:

```html
<a wicket:id="link" href="images/image-1.jpg" rel="lightbox" title="Lightbox with ResourceReference">
	<img wicket:id="image" alt="ResourceReference" />
</a>
```

## Maven

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>lightbox2</artifactId>
</dependency>
```

Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

The [`lightbox2-examples`](https://github.com/wicketstuff/core/tree/master/lightbox2-parent/lightbox2-examples) module contains a runnable example application.

Source: [`lightbox2-parent`](https://github.com/wicketstuff/core/tree/master/lightbox2-parent)
