---
title: FST Serializer
category: infra
description: An ISerializer based on fast-serialization (FST) 2.
artifactId: wicketstuff-serializer-fast2
tags: serialization performance
---

A Wicket serializer using the Fast 2 (FST) library.

Fast Serialization reimplements Java Serialization with focus on speed, size and compatibility.

This allows the use of FST with minimal code change.

Fast 2 is a complete rewrite of Fast based on JDK 7.

- Fast website: <https://ruedigermoeller.github.io/fast-serialization/>
- Fast on GitHub: <https://github.com/RuedigerMoeller/fast-serialization>

## Maven

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-serializer-fast2</artifactId>
</dependency>
```

Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

The [module README](https://github.com/wicketstuff/core/blob/master/serializer-fast2/README.md) has more details.

Source: [`serializer-fast2`](https://github.com/wicketstuff/core/tree/master/serializer-fast2)
