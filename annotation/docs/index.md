---
title: Annotations
category: dev
description: Mount pages with annotations and class path scanning.
artifactId: wicketstuff-annotation
tags: mount url
---

The annotation library is used to mount your pages declaratively via Java annotations instead of programmatically via `mountPage()` calls in your `WebApplication.init()` method.

## Maven

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-annotation</artifactId>
</dependency>
```

Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

## Quick overview

A typical Wicket application might have several pages mounted like the following:

```java
public class MyWicketApplication extends WebApplication
{
    @Override
    protected void init()
    {
        super.init();
        mountPage("terms", Terms.class);
        mountPage("dogs/${sport}/${show}", Dogs.class);
    }
}
```

With annotation, you annotate each page like this:

```java
@MountPath("terms")
public class Terms extends WebPage { ... }

@MountPath("dogs/${sport}/${show}")
public class Dogs extends WebPage { ... }
```

And use the `AnnotatedMountScanner` in your `init` method like this:

```java
public class MyWicketApplication extends WebApplication
{
    @Override
    protected void init()
    {
        super.init();
        new AnnotatedMountScanner().scanPackage("com.acme.wicket.pages").mount(this);
    }
}
```

## History

This library was created because of the desire for the following capabilities:

1. The ability to encapsulate how a page is mounted with the code for the page itself
2. Remove the need to edit the `WebApplication` class each time a new page is added.

Java annotations and class-path scanning are a good way to achieve these goals.

**Note:** Not all developers like the use of annotations and not all Wicket applications may benefit from this library. Our goal is to offer a nice alternative way to address the need to mount pages in Wicket in order to create nice URLs.

## How it works

The `AnnotatedMountScanner` does all the work. It uses another class, `MatchingResources`, to scan the class path looking for classes annotated with `@MountPath`. See the [MatchingResources](#matchingresources) section for details on this class.

The entire class path is not necessarily scanned. You can limit what packages to look in. In the example above, only classes under `com.acme.wicket.pages` are searched. The class path scanning is very efficient by itself and limiting the search only makes it faster.

The `@MountPath` annotation specifies the primary path (`value`) and alternate paths (`alt`) that the page should be mounted on. The primary path is the one chosen when Wicket maps a page to a path. The primary and alternate paths are used when Wicket maps paths back to pages. If no path is given, the simple name of the page class is used. Each path is mounted with a `MountedMapper`, or a `HomePageMapper` for `/`.

### MatchingResources

Matching is done via Spring's `PathMatchingResourcePatternResolver` class. This means that annotation has a dependency on Spring (`spring-core`).

The `MatchingResources` class is useful for finding any resources on the path. It was adapted from Donohoe Digital's core architecture and contributed as part of this library. Technically, it does not depend on Wicket, so it could at some point be moved into another package.

The annotation project uses the `getAnnotatedMatches()` method to find classes with the `@MountPath` annotation. Note that Spring does not load the class to determine this information. Instead, it uses a meta-data reader to determine this (which is faster than going through class loading).

## License

Just like Wicket itself, the annotation library is licensed under Apache 2.0.

## Author

Doug Donohoe of Donohoe Digital LLC is the author of annotation. He can be reached at doug (at) donohoe (dot) info.

Source: [`annotation`](https://github.com/wicketstuff/core/tree/wicket-10.x/annotation)
