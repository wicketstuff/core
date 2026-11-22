---
title: Java EE Inject
category: infra
description: Inject @EJB, @Resource and @PersistenceUnit into Wicket components.
artifactId: wicketstuff-javaee-inject
tags: cdi ejb jakarta injection
---

JavaEE Inject is a Wicket module that provides integration through Java EE (Jakarta EE) resource injection. With JavaEE Inject you can use three annotations in your Wicket components:

* `@EJB`
* `@PersistenceUnit`
* `@Resource`

## Maven

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-javaee-inject</artifactId>
</dependency>
```

Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

## Documentation

* [How to use the @EJB annotation](ejb.md)
* [How to use the @PersistenceUnit annotation](persistenceunit.md)
* [How to use the @Resource annotation](resource.md)

The examples are in the `javaee-inject-examples` module. This example project is always maintained with the current version of Wicket (and Jakarta EE).

## Project maintainers

* [Filippo Diotalevi](http://www.diotalevi.com/)
* Maris Orbidans
* [Peter Major](http://kir-dev.sch.bme.hu)

The [`javaee-inject-examples`](https://github.com/wicketstuff/core/tree/master/javaee-inject-parent/javaee-inject-examples) module contains a runnable example application.

Source: [`javaee-inject-parent`](https://github.com/wicketstuff/core/tree/master/javaee-inject-parent)
