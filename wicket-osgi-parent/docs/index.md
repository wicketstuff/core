---
title: OSGi
category: infra
description: Run Wicket applications in an OSGi container and inject OSGi services.
artifactId: wicketstuff-osgi
tags: bundle
---

The OSGi Integration Project lets you use Wicket in OSGi environments. It provides some glue code to let your Wicket components and applications interact with the OSGi service registry.

## Maven

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-osgi</artifactId>
</dependency>
```

Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

## Summary

The `wicketstuff-osgi` bundle provides the following entry points:

- `OsgiWebApplicationFactory` supports bootstrapping a `WebApplication` from the OSGi service registry, matching a property value specified as a `WicketFilter` init parameter in `web.xml`.

- `OsgiClassResolver` supports Wicket page deserialization.

- `OsgiComponentInjector` lets you inject OSGi services into Wicket components by annotating fields with JSR-330 `@Inject`.

## Sample code

`wicket-osgi-test-web` and `wicket-osgi-test-service` are two simple test bundles for testing the glue code in an OSGi web container (Pax Web). The sample uses Aries Blueprint to interact with the service registry, but of course you can also use Declarative Services or a `BundleActivator` to publish your services.

## Running the OSGi sample webapp

```text
cd wicket-osgi-parent/wicket-osgi-test-web
mvn install pax:run
```

Then open <http://localhost:8080/library> in your browser.

## Using wicketstuff-osgi

To use `wicketstuff-osgi` in your own projects, have a look at `web.xml` and `LibraryApplication.java` in `wicket-osgi-test-web`, and at the Javadoc in `wicket-osgi`.

## Author

[Harald Wellmann](http://hwellmann.blogspot.com)

Source: [`wicket-osgi-parent`](https://github.com/wicketstuff/core/tree/wicket-11.x/wicket-osgi-parent)
