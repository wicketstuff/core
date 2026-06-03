# Wicket Spring Boot Starter

A lightweight, zero-boilerplate starter that integrates **Apache Wicket 10.x** with **Spring Boot 4.x**. It configures the Wicket web context automatically, bridges Spring injection into Wicket components, and enables simple configuration via `application.properties` or `application.yml`.

---

## Features

- **Auto-Configuration**: Automatically registers Wicket's `WicketFilter` in Spring Boot's embedded servlet container.
- **Auto-Discovery**: Detects any subclass of Wicket's `WebApplication` registered as a Spring bean/component and binds it.
- **Spring Injection Support**: Automatically adds `SpringComponentInjector` to the application, allowing you to use `@SpringBean` to inject Spring-managed components directly into Wicket Pages and Panels.
- **Externalized Settings**: Configure Wicket's execution mode (`DEVELOPMENT` vs `DEPLOYMENT`), filter paths, and filter names directly from Spring properties.
- **IDE Auto-Completion Support**: Ships with built-in configuration metadata, enabling auto-completion and documentation tooltips for Wicket properties in popular IDEs (IntelliJ, Eclipse, VS Code).

---

## Dependency Configuration

To use the starter, add the following dependency to your Maven `pom.xml`:

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicket-spring-boot-starter</artifactId>
    <version>${wicketstuff.version}</version>
</dependency>
```

---

## Compatibility Matrix

Ensure you match the correct starter version with your Spring Boot and Java environment:

| WicketStuff / Starter Version | Spring Boot Version | Spring Framework Version | Minimum Java Version |
| :--- | :--- | :--- | :--- |
| **`10.x.y`** (Current) | `4.x.y` | `7.x.y` | Java 17 |
| **`9.x.y`** | `3.x.y` | `6.x.y` | Java 11 |

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

## Configuration Properties

The following properties can be configured in your `application.properties` or `application.yml` file:

| Property | Default Value | Description |
|---|---|---|
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

## Spring Initializr Metadata Registration

If you are requesting this starter to be listed on `start.spring.io` (or registering it in an internal Initializr instance), configure the dependency metadata as follows:

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
          artifactId: wicket-spring-boot-starter
          versionRange: "[4.0.0,4.1.0-M1)"
          links:
            - rel: reference
              href: https://github.com/wicketstuff/core/tree/master/wicket-spring-boot-starter-parent
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
  "artifactId": "wicket-spring-boot-starter",
  "versionRange": "[4.0.0,4.1.0-M1)",
  "links": [
    {
      "rel": "reference",
      "href": "https://github.com/wicketstuff/core/tree/master/wicket-spring-boot-starter-parent"
    },
    {
      "rel": "guide",
      "href": "https://wicket.apache.org/start/quickstart.html"
    }
  ]
}
```
