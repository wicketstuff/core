---
title: Save editor content with Ajax
---

Submit a HugeRTE editor with Wicket's standard Ajax form components. Before every Wicket Ajax call, `hugerte-wicket.js` listens to Wicket's `/ajax/call/before` event and calls `hugerte.triggerSave()`, copying each editor's HTML back into its `<textarea>` before Wicket serializes the form.

## Submit with AjaxButton or AjaxSubmitLink

Add a plain `AjaxButton` or `AjaxSubmitLink` to the form; no HugeRTE-specific Ajax component or modifier is needed:

```java
IModel<String> text = Model.of("<p>Edit this text.</p>");
FeedbackPanel feedback = new FeedbackPanel("feedback");
feedback.setOutputMarkupId(true);
add(feedback);

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

## Update on change or blur with AjaxFormComponentUpdatingBehavior

HugeRTE hides the `<textarea>` and edits in its own iframe, so the textarea never fires `change` or `blur` itself. `hugerte-wicket.js` forwards them: when the editor fires `change` (after an edit, typically when an undo level is added or the editor loses focus) or `blur`, it writes the content back to the textarea and dispatches the same event on it. An `AjaxFormComponentUpdatingBehavior` therefore works without extra code:

```java
TextArea<String> editor = new TextArea<>("text", text);
editor.add(new HugeRteBehavior(HugeRteConfig.defaults().withMenubar(false)));
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

Use `"blur"` instead of `"change"` to send the content only when the editor loses focus. The forwarding is installed in addition to any `setup` callback you configure.

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

See [`AjaxPage.java`](https://github.com/wicketstuff/core/tree/wicket-11.x/hugerte-parent/hugerte-examples/src/main/java/org/wicketstuff/hugerte/examples/AjaxPage.java) for a full example.
