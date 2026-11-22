---
title: Wicket Security
category: security
description: "Permission-based authorization: the WASP API and its SWARM implementation."
artifactId: wicketstuff-security-swarm
tags: wasp swarm hive auth
---

> This documentation was written for Wicket Security 1.x.

*Wicket Security* is an attempt to create an out of the box reusable
authenticating and authorization framework for [Apache
Wicket](https://wicket.apache.org). It contains several projects which can be
used standalone or in conjunction with each other.

* [Introduction](introduction.md)
* [Getting started with Swarm](gettingstarted.md)
* [Integrate with Spring Security](springsecurity.md)
* [License](license.md)

## Maven

Wasp and Swarm can be added to your project by including the following
fragment in your project pom.

```xml
<dependency>
	<groupId>org.wicketstuff</groupId>
	<artifactId>wicketstuff-security-swarm</artifactId>
</dependency>
```

A separate dependency on Wasp is not necessary since Maven will automatically
fetch it with Swarm. However if you are only interested in Wasp you can use
the following fragment.

```xml
<dependency>
	<groupId>org.wicketstuff</groupId>
	<artifactId>wicketstuff-security-wasp</artifactId>
</dependency>
```

Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

## Project maintainers

Emond Papegaaij, Martijn Dashorst

## Bug reports

Bugs can be filed at the [Wicket Stuff issue tracker](https://github.com/wicketstuff/core/issues).

The [module README](https://github.com/wicketstuff/core/blob/master/wicket-security-parent/README.md) has more details.

Source: [`wicket-security-parent`](https://github.com/wicketstuff/core/tree/master/wicket-security-parent)
