---
title: Change the configuration per request
---

Pass an `IModel<HugeRteConfig>` to `HugeRteBehavior` when the editor configuration depends on request state, user permissions, session locale, or component state that changes over time.

`HugeRteBehavior` reads the model every time the component renders in `renderHead` and detaches it in `detach`. Because `HugeRteConfig` is immutable, each `with...` call returns a new instance without affecting any shared base configuration.

## Build a configuration from request state

The example below gives administrators more plugins than other users. The model is read on every render, so the configuration follows the current session:

```java
HugeRteConfig base = HugeRteConfig.defaults()
    .withPlugins(HugeRtePlugin.LISTS, HugeRtePlugin.LINK)
    .withToolbar("undo redo | bold italic | bullist numlist | link")
    .withMenubar(false);

IModel<HugeRteConfig> configModel = () -> MySession.get().isAdmin()
    ? base.withPlugins(HugeRtePlugin.IMAGE, HugeRtePlugin.CODE)
        .withToolbar("undo redo | bold italic | bullist numlist | link image | code")
    : base;

TextArea<String> editor = new TextArea<>("text", textModel);
editor.add(new HugeRteBehavior(configModel));
add(editor);
```

You don't need a model for read-only mode: when the component, or a parent such as the form, is disabled, `HugeRteBehavior` makes the editor read-only. Disable or enable it with Ajax by changing the component and adding it, or its form, to the target. Re-rendering replaces the editor with the model value, so submit the form first if the user's edits must be kept.

Subclasses can also override `getConfig(Component)` to vary the configuration per component when one behavior is shared.

## Share one behavior across multiple editors

`HugeRteBehavior` holds no per-component state, so you can attach the same behavior instance — backed by a single `IModel<HugeRteConfig>` — to several `TextArea` components:

```java
HugeRteBehavior sharedBehavior = new HugeRteBehavior(configModel);
summaryField.add(sharedBehavior);
bodyField.add(sharedBehavior);
```

## Non-null requirement

The model must return a non-null `HugeRteConfig`. If `configModel.getObject()` returns `null`, `HugeRteBehavior.getConfig()` throws a `NullPointerException`. Return `HugeRteConfig.defaults()` when no custom options apply.
