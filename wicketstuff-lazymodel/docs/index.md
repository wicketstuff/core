---
title: LazyModel
category: dev
description: Compile-time type-safe models, without property expression strings.
artifactId: wicketstuff-lazymodel
tags: model typesafe
---

A type-safe model implementation for Apache Wicket, supporting:

- lazy evaluation of method invocations
- arbitrary parameters
- generics
- collections
- interfaces

```java
    A a = ...;

    IModel<B> model = model(from(a).getB());
```

... or starting from a model (must be an `IObjectClassAwareModel`, e.g. `PropertyModel` or subclass of `AbstractReadonlyModel`):

```java
    IModel<A> a = ...;

    IModel<Map<String, String>> model = model(from(a).getB().getStrings());
```

... or as a `LoadableDetachableModel` (loaded once per request only):

```java
    IModel<A> a = ...;

    IModel<String> model = model(from(a).getB().getStrings().get("key")).loadableDetachable();
```

Evaluations can be nested too:

```java
    IModel<A> a = ...;
    IModel<D> d = ...;

    IModel<C> model = model(from(a1).getB().getCs().get( from(d).getIndex() ));
```

Any method returning `null` immediately stops the evaluation. The same holds for any access to an out-of-bound list index.

`DataTable`s columns can be lazily evaluated too:

```java
    column = new LazyColumn<A, Void, B>(header, from(A.class).getB());
```

To be able to 'record' evaluations, LazyModel uses proxies for each method invocation's result: cglib is used to generate a subclass of the method's return type.
For primitive or final return types this is not possible. To support this common usecase, LazyModel utilizes ThreadLocals:

```java
    model(from(a).getI()); // works although #getI() has a primitive type as return type
    model(from(a).getF()); // works although #getF() has final class F as return type
```

All this proxying comes at a performance cost: depending on your usage LazyModel is 2 to 5 times slower than PropertyModel! If you're concerned about this you might want to cache lazy evaluations:

```java
    private static final LazyModel<B> B = model(from(A.class).getB());

    IModel<A> a = ...;

    add(new TextField<B>("b", B.bind(a)));
```

Caching model instances will increase performance for LazyModel to be slightly faster than PropertyModel.

Inspired by:

- [Working with Wicket models: LambdaJ](https://cwiki.apache.org/confluence/display/WICKET/Working+with+Wicket+models)
- <https://github.com/duesenklipper/wicket-safemodel>
- <http://wicketeer.org/wicket-modelfactory>

Using:

- [cglib](https://github.com/cglib/cglib)
- [Objenesis](https://objenesis.org/)

Limitations:

- no lazy evaluations on final classes (`F` is final)

```java
    model(from(a).getF().getString());
```

- no lazy evaluations of final methods (`#getFinalB()` is final)

```java
    model(from(a).getFinalB().getCharacter());
```

Note that these unsupported cases will fail with a `NullPointerException`!

## Maven

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-lazymodel</artifactId>
</dependency>
```

Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

Source: [`wicketstuff-lazymodel`](https://github.com/wicketstuff/core/tree/master/wicketstuff-lazymodel)
