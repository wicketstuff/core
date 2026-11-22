---
title: Spring Reference
category: infra
description: Spring bean references for when @SpringBean's dynamic proxies are not an option.
artifactId: wicketstuff-springreference
tags: spring injection
---

The wicketstuff-springreference module can be used to integrate a [Wicket](https://wicket.apache.org) web application with [Spring](https://spring.io). It is independent from wicket-spring and can be used for cases where @SpringBean is not an option. With wicketstuff-springreference is it also possible to use spring annotation driven injection (for example @Autowired, @Qualifier).

## Why?
From wicket-spring the @SpringBean annotation together with the SpringComponentInjector class give a slick way to inject spring beans into components. However it is using a technology called "dynamic proxy" and this is not always working. This limits the usability of @SpringBean in some cases. When you hit these limitations you probably see strange exceptions like these:

```text
Caused by: java.lang.IllegalArgumentException: Protected method: fooMethod()V
	at net.sf.cglib.proxy.MethodProxy.invoke(MethodProxy.java:196)
	at org.apache.wicket.proxy.LazyInitProxyFactory$CGLibInterceptor.intercept(LazyInitProxyFactory.java:320)
```

or

```text
Caused by: java.lang.IllegalArgumentException: No visible constructors in class FooClass
	at net.sf.cglib.proxy.Enhancer.filterConstructors(Enhancer.java:531)
	at net.sf.cglib.proxy.Enhancer.generateClass(Enhancer.java:448)
```

The **SpringReference** and **AbstractSpringDependencies** class from wicketstuff-springreference are not annotation/dynamic proxy based so they work in all cases (but using them does not look that cool as an annotation). Both can be used independently or together for spring - wicket integration.

Because SpringReference does not depend on IComponentInstantiationListener-s and looks up beans lazily it could also be slightly faster than @SpringBean.

AbstractSpringDependencies makes spring annotation driven injection possible in wicket apps.

## Maven

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-springreference</artifactId>
</dependency>
```

Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

## Java snippet
To enable **SpringReference** or **AbstractSpringDependencies** support you have to register a **SpringReferenceSupporter** with your WebApplication. This can be done in the init() method:

```java
public class App extends WebApplication {
	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

	@Override
	protected void init() {
		super.init();
		getMarkupSettings().setDefaultMarkupEncoding(CharEncoding.UTF_8);

		/* --> */ SpringReferenceSupporter.register(this); /* <-- */
	}
}
```

Now you can refer spring beans in your wicket components, models, etc. and in your custom classes too:

### SpringReference
Declaring SpringReference is simple enough:

```java
// reference by type and bean id
private final SpringReference<AuthenticationManager> authenticationManagerRef =
					SpringReference.of(AuthenticationManager.class, "authenticationManager");
// reference by type only
private final SpringReference<AbstractRememberMeServices> rememberMeServicesRef =
					SpringReference.of(AbstractRememberMeServices.class);
```

And to access the beans you can use the get() method of the SpringReference objects:

```java
authenticationManagerRef.get().authenticate(token);
AbstractRememberMeServices rememberMeServices = rememberMeServicesRef.get();
```

### AbstractSpringDependencies
This is a bit trickier. You have to define a subclass to encapsulate the spring dependencies. Note the Deps class in the example below. Also note that the dependencies are marked as transient so they are not serialized by wicket. They are reinjected by Deps (AbstractSpringDependencies) during deserialization.

```java
public class MySession extends AuthenticatedWebSession
{
	private static final long serialVersionUID = 1L;

	// This nested class holds all our spring dependencies. Annotated in spring style.
	static class Deps extends AbstractSpringDependencies
	{
		private static final long serialVersionUID = 1L;

		@Autowired
		@Qualifier("authenticationManager")
		transient AuthenticationManager authenticationManager;

		@Autowired
		transient AbstractRememberMeServices abstractRememberMeServices;
	}

	private final Deps deps = new Deps();

	// ...

	public boolean authenticate(String username, String password)
	{
		// ...
		deps.authenticationManager.authenticate(token);
		AbstractRememberMeServices rememberMeServices = deps.abstractRememberMeServices;
		// ...
	}
}
```

## Dependencies
Wicketstuff-springreference depends on [Wicket](https://wicket.apache.org) and [spring-web](https://spring.io).

## Further reading
For more info see the javadoc of the `SpringReference`, `AbstractSpringDependencies` and `SpringReferenceSupporter` classes. For a complete `@SpringBean` - `SpringReference` - `AbstractSpringDependencies` example see the examples web application.

The [`wicketstuff-springreference-examples`](https://github.com/wicketstuff/core/tree/wicket-10.x/wicketstuff-springreference-parent/wicketstuff-springreference-examples) module contains a runnable example application.

Source: [`wicketstuff-springreference-parent`](https://github.com/wicketstuff/core/tree/wicket-10.x/wicketstuff-springreference-parent)
