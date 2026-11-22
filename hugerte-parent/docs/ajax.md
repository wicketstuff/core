---
title: Save editor content with Ajax
---

Submit a HugeRTE editor with Wicket's standard Ajax form components. Before every Wicket Ajax call, `hugerte-wicket.js` listens to Wicket's `/ajax/call/before` event and calls `hugerte.triggerSave()`, copying each editor's HTML back into its `<textarea>` before Wicket serializes the form.

## Submit with AjaxButton or AjaxSubmitLink

Add a plain `AjaxButton` or `AjaxSubmitLink` to the form; no HugeRTE-specific Ajax component or modifier is needed:

```java
IModel<String> text = Model.of("<p>Edit this text.</p>");

Form<Void> form = new Form<>("form");
add(form);

TextArea<String> editor = new TextArea<>("text", text);
editor.add(new HugeRteBehavior(HugeRteConfig.defaults()
    .withPlugins(HugeRtePlugin.LISTS, HugeRtePlugin.LINK)
    .withToolbar("undo redo | bold italic | bullist numlist | link")
    .withMenubar(false)));
form.add(editor);

form.add(new AjaxButton("save")
{
    @Override
    protected void onSubmit(AjaxRequestTarget target)
    {
        // text.getObject() already holds the edited HTML
        target.add(feedback);
    }
});
```

`AjaxSubmitLink` works the same way:

```java
form.add(new AjaxSubmitLink("saveLink")
{
    @Override
    protected void onSubmit(AjaxRequestTarget target)
    {
        target.add(feedback);
    }
});
```

## Update on blur or change with AjaxFormComponentUpdatingBehavior

Because HugeRTE hides the `<textarea>` and edits inside its own iframe, user edits do not fire native DOM `change` or `blur` events on the `<textarea>`. If you attach an `AjaxFormComponentUpdatingBehavior` to the `TextArea`, forward the editor's `blur` (or `change`) event to the underlying element in a `setup` callback:

```java
HugeRteConfig config = HugeRteConfig.defaults()
    .withMenubar(false)
    .with("setup", new RawJavaScript(
        "function (editor) { editor.on('blur', function () { "
            + "editor.getElement().dispatchEvent(new Event('change')); }); }"));

TextArea<String> editor = new TextArea<>("text", text);
editor.add(new HugeRteBehavior(config));
editor.add(new AjaxFormComponentUpdatingBehavior("change")
{
    @Override
    protected void onUpdate(AjaxRequestTarget target)
    {
        // text.getObject() contains the updated HTML
        target.add(preview);
    }
});
```

When `dispatchEvent` triggers Wicket's `change` handler, Wicket publishes `/ajax/call/before`, `hugerte-wicket.js` calls `hugerte.triggerSave()`, and Wicket sends the updated `<textarea>` value. See [Use JavaScript callbacks with RawJavaScript](javascript-callbacks.md) for details on `RawJavaScript`.

## Re-render and hide editors

HugeRTE renders its UI as sibling elements next to the `<textarea>`. When Wicket Ajax replaces or removes a component, `hugerte-wicket.js` receives Wicket's `/dom/node/removing` event and calls `editor.remove()` for every editor inside that DOM subtree so no orphaned editor UI stays behind.

To re-render an editor or its enclosing form, add it to the `AjaxRequestTarget`:

```java
target.add(form);
```

Wicket removes the old editor before replacing the markup, and the Ajax response runs `Wicket.HugeRte.init(...)` to create a fresh editor for the new `<textarea>`.

To hide and show an editor with Ajax, enable a placeholder tag on the toggled component or container:

```java
WebMarkupContainer toggled = new WebMarkupContainer("toggled");
toggled.setOutputMarkupPlaceholderTag(true);
toggled.add(new TextArea<>("second", Model.of("<p>A second editor.</p>"))
    .add(new HugeRteBehavior(HugeRteConfig.defaults().withMenubar(false))));
add(toggled);

add(new AjaxLink<Void>("toggle")
{
    @Override
    public void onClick(AjaxRequestTarget target)
    {
        toggled.setVisible(!toggled.isVisible());
        target.add(toggled);
    }
});
```

When `toggled` becomes invisible, Wicket replaces it with a placeholder tag and `/dom/node/removing` removes the editor cleanly. When it becomes visible again, `HugeRteBehavior` initializes a new editor.

See [`AjaxPage.java`](https://github.com/wicketstuff/core/tree/master/hugerte-parent/hugerte-examples/src/main/java/org/wicketstuff/hugerte/examples/AjaxPage.java) for a full example.
