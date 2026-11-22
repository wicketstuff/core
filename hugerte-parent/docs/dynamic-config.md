---
title: Change the configuration per request
---

Pass an `IModel<HugeRteConfig>` to `HugeRteBehavior` when the editor configuration depends on request state, user permissions, session locale, or component state that changes over time.

`HugeRteBehavior` reads the model every time the component renders in `renderHead` and detaches it in `detach`. Because `HugeRteConfig` is immutable, each `with...` call returns a new instance without affecting any shared base configuration.

## Build a configuration from component state

The example below switches an editor between editable and read-only mode on an Ajax click:

```java
private boolean readOnly = true;

// Inside your page or panel constructor:
HugeRteConfig base = HugeRteConfig.defaults()
    .withPlugins(HugeRtePlugin.LISTS, HugeRtePlugin.LINK)
    .withToolbar("undo redo | bold italic | bullist numlist | link")
    .withMenubar(false);

IModel<HugeRteConfig> configModel = LoadableDetachableModel.of(
    () -> base.withReadonly(readOnly));

TextArea<String> editor = new TextArea<>("text", textModel);
editor.add(new HugeRteBehavior(configModel));
add(editor);

add(new AjaxLink<Void>("toggleEdit")
{
    @Override
    public void onClick(AjaxRequestTarget target)
    {
        readOnly = !readOnly;
        target.add(editor);
    }
});
```

When `toggleEdit` runs, Wicket re-renders `editor`. `hugerte-wicket.js` removes the previous editor instance, `HugeRteBehavior` evaluates `configModel` to obtain the updated `HugeRteConfig`, and `Wicket.HugeRte.init(...)` initializes the editor with the new `readonly` setting.

## Share one behavior across multiple editors

`HugeRteBehavior` holds no per-component state, so you can attach the same behavior instance — backed by a single `IModel<HugeRteConfig>` — to several `TextArea` components:

```java
HugeRteBehavior sharedBehavior = new HugeRteBehavior(configModel);
summaryField.add(sharedBehavior);
bodyField.add(sharedBehavior);
```

## Non-null requirement

The model must return a non-null `HugeRteConfig`. If `configModel.getObject()` returns `null`, `HugeRteBehavior.getConfig()` throws a `NullPointerException`. Return `HugeRteConfig.defaults()` when no custom options apply.
