---
title: Scala Extensions
category: dev
description: Utilities, components and an archetype for writing Wicket applications in Scala.
artifactId: org.wicketstuff.scala:wicketstuff-scala
tags: scala
---

This small project improves the syntax of Wicket models when using the Scala programming language.

- [The blog post with the original idea](http://technically.us/code/x/the-escape-hatch)
- [The original announcement with more information](http://www.mail-archive.com/users@wicket.apache.org/msg40899.html)
- [Samples](https://github.com/wicketstuff/core/blob/master/scala-extensions-parent/wicket-scala-sample/src/main/scala/org/wicketstuff/scala/sample/HelloWicketWorld.scala)

You can set up a new Scala and Wicket project including this library with the following archetype (replace `<version>` with the current Wicket Stuff release):

```text
mvn archetype:generate -DarchetypeGroupId=org.wicketstuff.scala -DarchetypeArtifactId=wicketstuff-scala-archetype -DarchetypeVersion=<version>
```

## Scala DSL for Wicket

This project also offers a Scala DSL for Wicket, originally from the Gamboa Project. Add the trait `DSLWicket` to your page and check the features.

## Maven

```xml
<dependency>
    <groupId>org.wicketstuff.scala</groupId>
    <artifactId>wicketstuff-scala</artifactId>
</dependency>
```

Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

The [`wicket-scala-sample`](https://github.com/wicketstuff/core/tree/master/scala-extensions-parent/wicket-scala-sample) module contains a runnable example application.

Source: [`scala-extensions-parent`](https://github.com/wicketstuff/core/tree/master/scala-extensions-parent)
