---
title: Save editor content with Ajax
---

Submit a HugeRTE editor with Wicket's standard Ajax form components. Before every Wicket Ajax call, `hugerte-wicket.js` listens to Wicket's `/ajax/call/before` event and copies each editor's HTML back into its `<textarea>` with `editor.save({ set_dirty: false })`, before Wicket serializes the form. Don't call `hugerte.triggerSave()` yourself for this: it also marks every editor as saved.

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

HugeRTE fires `change` when it records an undo level, for example after formatting, pressing Enter or leaving the editor, so the update doesn't run on every keystroke. Undo and redo are forwarded as `change` as well. `OnChangeAjaxBehavior` therefore also works, at the same granularity. Use `"blur"` instead of `"change"` to send the content only when the editor loses focus. The forwarding is installed in addition to any `setup` callback you configure.

Copying the content to the textarea doesn't mark the editor as saved: `editor.isDirty()` stays `true`, so the autosave and save plugins keep working.

## Mark the editor as saved

After you have stored the content, tell the editor, so `isDirty()` becomes `false`, the autosave plugin discards its draft and stops warning when the user leaves the page:

```java
@Override
protected void onSubmit(AjaxRequestTarget target)
{
    service.save(text.getObject());
    HugeRteBehavior.markSaved(target, editor);
}
```

Call `markSaved` in the request that sent the editor: `onSubmit` of an `AjaxButton` or `AjaxSubmitLink`, or `onUpdate` of an `AjaxFormComponentUpdatingBehavior`. It acknowledges the content that request sent. If the user kept typing while the request was underway, the editor no longer holds that content and stays dirty. In a request that didn't send the editor, such as an `AjaxLink`, it does nothing, because the content may never have reached the model, for example after failed validation; the editor stays dirty and keeps warning. When requests that sent the editor overlap, which only happens with custom Ajax channels, it does nothing until a request sends the editor on its own. `markSaved` runs before Wicket replaces components, so it also works when the same response re-renders the editor.

An `AjaxButton` or `AjaxSubmitLink` that returns `true` from `shouldTriggerJavaScriptSubmitEvent()` fires the form's `submit` event, and HugeRTE marks the editor as saved on that event, before the server has answered.

## Focus the editor from the server

`AjaxRequestTarget.focusComponent(textArea)` focuses the hidden textarea, which does nothing. Use `HugeRteBehavior.focus` instead; it focuses the editor, waiting until it is initialized if needed:

```java
HugeRteBehavior.focus(target, editor);
```

## Limitations

HugeRTE shows its menus and dialogs in a container at the end of the page. Wicket's `ModalDialog` with `trapFocus()` moves the focus back into the dialog whenever it leaves, so an editor inside such a dialog can't use its own dialogs, such as the link dialog. Don't combine `trapFocus()` with an editor.

## Re-render and hide editors

HugeRTE renders its UI as sibling elements next to the `<textarea>`. When Wicket Ajax replaces or removes a component, `hugerte-wicket.js` receives Wicket's `/dom/node/removing` event and calls `editor.remove()` for every editor inside that DOM subtree so no orphaned editor UI stays behind.

To re-render an editor or its enclosing form, add it to the `AjaxRequestTarget`:

```java
target.add(form);
```

Wicket removes the old editor before replacing the markup, and the Ajax response runs `Wicket.HugeRte.init(...)` to create a fresh editor for the new `<textarea>`. Re-rendering is also how you change the content from the server: update the model and add the component to the target. The new editor shows the model value, so edits the user hasn't submitted are lost, along with the undo history.

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
