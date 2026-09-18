# Wicket Spring Boot Starter

A lightweight, zero-boilerplate starter that integrates **Apache Wicket 10.x** with **Spring Boot 4.x**. It configures the Wicket web context automatically, bridges Spring injection into Wicket components, and enables simple configuration via `application.properties` or `application.yml`.

---

## Motivation

Running Wicket on Spring Boot always needs the same handful of pieces: a `WebApplication`, a filter
registration for it, and a `SpringComponentInjector` so that `@SpringBean` works. None of it is
difficult, but it is the same every time, it is easy to get subtly wrong, and it is the first thing
standing between someone and a running Wicket page.

This starter supplies exactly those pieces and then gets out of the way. Everything past that point
is ordinary Wicket and ordinary Spring, documented in their own projects.

**Staying small is the point, not a limitation.** A deliberately narrow starter is cheap to keep
alive: when Wicket or Spring Boot publishes a new major version, there is very little surface to
re-verify. It also lives inside `wicketstuff-core` and is released with it, so the version you need
is simply the one matching your Wicket version — there is no separate compatibility matrix to
consult and no second release cycle to wait for.

### Is this the right starter for you?

There is an established alternative, [`MarcGiffing/wicket-spring-boot`][giffing]
(`com.giffing.wicket.spring.boot.starter`), which is a much broader integration: Spring Security,
native WebSockets, bean validation, CSRF protection, several serializers, session datastores,
monitoring and a set of development-mode helpers.

Pick that one if you want those batteries included. Pick this one if you would rather start from the
smallest thing that works and add what you need yourself. They solve the same problem with opposite
philosophies, and neither is a replacement for the other.

[giffing]: https://github.com/MarcGiffing/wicket-spring-boot

---

## Features

- **Auto-Configuration**: Automatically registers Wicket's `WicketFilter` in Spring Boot's embedded servlet container.
- **Auto-Discovery**: Detects any subclass of Wicket's `WebApplication` registered as a Spring bean/component and binds it.
- **Spring Injection Support**: Automatically adds `SpringComponentInjector` to the application, allowing you to use `@SpringBean` to inject Spring-managed components directly into Wicket Pages and Panels.
- **Externalized Settings**: Configure Wicket's execution mode (`DEVELOPMENT` vs `DEPLOYMENT`), filter paths, and filter names directly from Spring properties.
- **IDE Auto-Completion Support**: Ships with built-in configuration metadata, enabling auto-completion and documentation tooltips for Wicket properties in popular IDEs (IntelliJ, Eclipse, VS Code).

---

## How It Works

The starter contributes a single auto-configuration, `WicketAutoConfiguration`. It applies only when
all of the following hold:

- the application is a **servlet** web application (a reactive application is left alone);
- Wicket's `WebApplication` and `WicketFilter` are on the classpath;
- `wicket.enabled` is not set to `false`.

When it applies, three beans are contributed:

| Bean | What it does |
|---|---|
| `webApplication` | The Wicket `WebApplication`. Falls back to a built-in default (see below) if you have not defined one. |
| `springComponentInjector` | Registers Wicket's `SpringComponentInjector` against the `WebApplication`, which is what makes `@SpringBean` work inside pages and components. |
| `wicketFilterRegistration` | Registers `WicketFilter` with the servlet container, mapped at `wicket.filter-path`, passing Wicket's filter-mapping and `configuration` init parameters from your settings. |

### Living alongside Spring MVC

The starter brings `spring-boot-starter-webmvc`, so your application also has Spring MVC available.
Wicket's filter is mapped at `/*` but forwards anything it does not handle further down the filter
chain, so `@RestController` endpoints keep working next to Wicket pages:

```java
@RestController
class GreetingController {

    @GetMapping("/api/greeting")
    String greeting() {
        return "hello";
    }
}
```

With the defaults, `/api/greeting` reaches the controller while `/` renders your Wicket home page.
If you would rather keep the two strictly apart, confine Wicket to its own prefix with
`wicket.filter-path=/app/*`.

### Deploying as a WAR

The starter brings an embedded Tomcat, but it does not commit you to it. Spring Boot's usual recipe
for deploying to an external servlet container works unchanged — set `war` packaging, extend
`SpringBootServletInitializer`, and re-declare the container at `provided` scope:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-tomcat</artifactId>
    <scope>provided</scope>
</dependency>
```

A direct declaration wins over the transitive one, so the embedded container is demoted to
`provided` and stays out of `WEB-INF/lib` while Wicket and the starter are packaged as normal.

---

## Dependency Configuration

This is the only dependency you need: it brings Apache Wicket, the Wicket/Spring bridge and the
embedded servlet container with it. Add it to your Maven `pom.xml` (check
[Maven Central](https://central.sonatype.com/artifact/org.wicketstuff/wicketstuff-spring-boot-starter)
for the latest released version):

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-spring-boot-starter</artifactId>
    <version>10.12.0-SNAPSHOT</version>
</dependency>
```

---

## Compatibility Matrix

Ensure you match the correct starter version with your Spring Boot and Java environment:

| WicketStuff / Starter Version | Spring Boot Version | Spring Framework Version | Minimum Java Version |
| :--- | :--- | :--- | :--- |
| **`10.x.y`** (Current) | `4.x.y` | `7.x.y` | Java 17 |

---

## The Default Page

Add the dependency, start the application, and you already have a running Wicket application: with
no `WebApplication` bean of your own, the starter registers a built-in one that serves a placeholder
home page telling you how to replace it.

That default disappears the moment you declare your own `WebApplication` bean, which is what the
Quickstart below does. It exists so a freshly generated project runs and shows something, not as a
page you are meant to keep.

---

## Quickstart

### 1. Create a Wicket Application Component
Subclass `WebApplication` and register it as a Spring bean using `@Component`:

```java
package com.example;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.stereotype.Component;

@Component
public class WicketApplication extends WebApplication {

    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

    @Override
    protected void init() {
        super.init();
        // Additional custom Wicket configurations...
    }
}
```

### 2. Inject Spring Beans into Pages
Use Wicket's standard `@SpringBean` annotation to inject Spring-managed services:

```java
package com.example;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class HomePage extends WebPage {
    private static final long serialVersionUID = 1L;

    @SpringBean
    private MySpringService mySpringService;

    public HomePage() {
        add(new Label("message", mySpringService.sayHello()));
    }
}
```

### 3. Bootstrap your Spring Boot App
Annotate your entry point with `@SpringBootApplication` and run it:

```java
package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }
}
```

---

## Overriding the Defaults

Every bean the starter contributes steps aside as soon as you define your own, so you can take over
as much or as little as you need:

| Bean | Steps aside when | Define your own to |
|---|---|---|
| `webApplication` | any `WebApplication` bean exists | use your own Wicket application (the usual case — see the Quickstart) |
| `springComponentInjector` | any `SpringComponentInjector` bean exists | control how Spring injection is wired |
| `wicketFilterRegistration` | a `FilterRegistrationBean<WicketFilter>` bean exists | control the filter registration, e.g. its order relative to other filters |

Overriding the filter registration does **not** cost you Spring injection: the injector is bound to
the `WebApplication` rather than to the filter, so `@SpringBean` keeps working in your pages either
way.

### Migrating an existing Wicket + Spring application

Applications that wire Wicket and Spring by hand usually register the injector themselves, along
these lines:

```java
@Override
protected void init() {
    super.init();
    getComponentInstantiationListeners().add(new SpringComponentInjector(this));
}
```

Keeping that line as well as the starter's bean would register two injectors. Either drop it and let
the starter do it, or, if you want to keep control of the wiring, declare your own
`SpringComponentInjector` bean so the starter's backs off.

---

## Configuration Properties

The following properties can be configured in your `application.properties` or `application.yml` file:

| Property | Default Value | Description |
|---|---|---|
| `wicket.enabled` | `true` | Set to `false` to switch the auto-configuration off entirely. |
| `wicket.filter-path` | `/*` | URL mapping pattern for the Wicket filter. |
| `wicket.filter-name` | `wicket-filter` | The name of the registered Wicket servlet filter. |
| `wicket.configuration` | `DEVELOPMENT` | The configuration type: `DEVELOPMENT` or `DEPLOYMENT`. |

For example:
```properties
wicket.configuration=DEPLOYMENT
wicket.filter-path=/app/*
wicket.filter-name=my-custom-wicket-filter
```

---

## Testing

Wicket keys its application registry by the filter name, and Spring keeps test contexts cached for
the lifetime of the JVM. Two tests that each start a real servlet container therefore collide on the
default filter name, failing with `Application with name 'wicket-filter' already exists`.

Give each such test its own filter name:

```java
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "wicket.filter-name=my-test-filter")
class MyIntegrationTest {
    // ...
}
```

Tests that do not start a container (the default `MOCK` web environment) are unaffected, because the
filter is never initialised.

---

## Spring Initializr Metadata Registration

If you are requesting this starter to be listed on `start.spring.io` (or registering it in an internal Initializr instance), configure the dependency metadata as follows. `versionRange` expresses the Spring Boot versions this starter supports (see the Compatibility Matrix above), not the starter's own version. It's left open-ended here (`4.0.0` and any later version) since nothing in the starter's autoconfiguration mechanism is expected to break across future Spring Boot majors — add an explicit upper bound only once a specific incompatibility is found:

### YAML Configuration (e.g. `application.yml`)
```yaml
initializr:
  dependencies:
    - name: Web
      content:
        - name: Wicket
          id: wicket
          description: Apache Wicket component-oriented web application framework integrated with Spring Boot.
          groupId: org.wicketstuff
          artifactId: wicketstuff-spring-boot-starter
          versionRange: "4.0.0"
          links:
            - rel: reference
              href: https://github.com/wicketstuff/core/tree/master/wicketstuff-spring-boot-starter-parent
            - rel: guide
              href: https://wicket.apache.org/start/quickstart.html
```

### JSON Metadata API Representation
```json
{
  "name": "Wicket",
  "id": "wicket",
  "description": "Apache Wicket component-oriented web application framework integrated with Spring Boot.",
  "groupId": "org.wicketstuff",
  "artifactId": "wicketstuff-spring-boot-starter",
  "versionRange": "4.0.0",
  "links": [
    {
      "rel": "reference",
      "href": "https://github.com/wicketstuff/core/tree/master/wicketstuff-spring-boot-starter-parent"
    },
    {
      "rel": "guide",
      "href": "https://wicket.apache.org/start/quickstart.html"
    }
  ]
}
```

---

## Further Reading

- [Apache Wicket documentation](https://wicket.apache.org/) — writing pages, components and markup.
- [WicketStuff wiki](https://github.com/wicketstuff/core/wiki) — documentation for the other modules
  in this project.
- [API documentation](https://www.javadoc.io/doc/org.wicketstuff/wicketstuff-spring-boot-starter) — the
  starter's own Javadoc, published per release.
