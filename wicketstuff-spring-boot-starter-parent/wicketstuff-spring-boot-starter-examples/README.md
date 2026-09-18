# WicketStuff Spring Boot Starter — Examples

A runnable example for the starter, plus the integration tests that demonstrate and verify its
documented behaviour.

## Running it

```bash
mvn -pl wicketstuff-spring-boot-starter-parent/wicketstuff-spring-boot-starter-examples -am spring-boot:run
```

Then open <http://localhost:8080>.

`ExampleApplication` contains nothing but a Spring Boot entry point — no `WebApplication`, no page,
no Wicket configuration. That is deliberate: it shows what the starter gives you on its own, which
is a working Wicket application serving the built-in placeholder page.

`src/main/resources/application.properties` overrides a couple of `wicket.*` settings purely to
show that externalised configuration reaches Wicket; none of it is required.

## Where each pattern is demonstrated

The patterns you would write in a real application live in `src/test/java`, so they are exercised on
every build rather than drifting out of date as untested sample code:

| What you want to see | Where |
|---|---|
| A custom `WebApplication` and a page using `@SpringBean` | `SpringBeanInjectionQuickstartTest` (with `QuickstartWebApplication`, `QuickstartHomePage`, `GreetingService`) |
| That `@SpringBean` still works if you supply your own filter registration | `SpringBeanInjectionWithCustomFilterRegistrationTest` |
| REST controllers living alongside Wicket pages | `WicketAndSpringMvcCoexistenceTest` |
| `wicket.*` properties actually reaching the filter | `WicketPropertiesBindingTest` |
| That the starter alone is enough to boot a web application | `StarterSelfSufficiencyTest` |

## A note on the dependencies

This module depends on **nothing but the starter** at compile scope, and an enforcer rule in its
`pom.xml` keeps it that way. That is what makes `StarterSelfSufficiencyTest` meaningful: the
embedded servlet container it boots can only have come from the starter. If you need another
dependency here, it almost certainly belongs in the starter instead.

Note also that each test which starts a real servlet container sets its own `wicket.filter-name`.
Wicket keys its application registry by that name and Spring caches test contexts, so tests would
otherwise collide — see the Testing section of the [starter README](../README.md).
