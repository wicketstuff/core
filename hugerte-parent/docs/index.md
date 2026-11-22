---
title: HugeRTE
category: js
description: WYSIWYG rich-text editing with HugeRTE, the MIT-licensed fork of TinyMCE 6.
artifactId: wicketstuff-hugerte
tags: editor html wysiwyg tinymce
---

WYSIWYG rich-text editing with [HugeRTE](https://hugerte.org), the MIT-licensed fork of TinyMCE 6. The integration is licensed under the Apache License 2.0; HugeRTE itself is MIT.

## Maven

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-hugerte</artifactId>
</dependency>
```

Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

## Usage

Add a `HugeRteBehavior` to a `TextArea`:

```java
TextArea<String> text = new TextArea<>("text", model);
text.add(new HugeRteBehavior(HugeRteConfig.defaults()
    .withPlugins(HugeRtePlugin.LISTS, HugeRtePlugin.LINK, HugeRtePlugin.TABLE)
    .withToolbar("undo redo | bold italic | bullist numlist | link table")
    .withMenubar(false)
    .with("height", 400)));
```

`HugeRteConfig` is an immutable record. Plugins and toolbar rows have their own methods; every other [HugeRTE option](https://hugerte.org) is set with `with(name, value)`. Values can be strings, booleans, numbers, lists and maps of those, and `RawJavaScript` for options that take a function:

```java
config.with("setup", new RawJavaScript("function (editor) { editor.on('init', () => console.log('ready')); }"));
```

Strings are escaped; `RawJavaScript` is written as is, so never build it from user input. To change the configuration per request, pass an `IModel<HugeRteConfig>` to the behavior.

## Ajax

No special buttons or links are needed:

- Before every Wicket Ajax call the editors write their content back to their textareas, so an ordinary `AjaxButton` or `AjaxFormComponentUpdatingBehavior` submits the edited text.
- When Wicket Ajax replaces or removes a component, its editor is removed; a re-rendered component gets a new editor.

## Content Security Policy

HugeRTE's scripts load under Wicket's strict Content Security Policy, but its style sheets and style attributes do not. Add what it needs with:

```java
@Override
protected void init()
{
    super.init();
    HugeRteCsp.allow(getCspSettings().blocking());
}
```

This adds `style-src 'self'` (the skin style sheets), `style-src-attr 'unsafe-inline'` (style attributes in the editor and its content) and `img-src data: blob:` (pasted images). Script sources are not changed.

## Migrating from wicketstuff-tinymce6

This is a new integration, not a renamed copy of `wicketstuff-tinymce6`:

| wicketstuff-tinymce6 | wicketstuff-hugerte |
|---|---|
| `TinyMceBehavior` | `HugeRteBehavior` |
| `TinyMCESettings` with setters | `HugeRteConfig` record with `with...` methods |
| `TinyMCE6Plugin.lists` | `HugeRtePlugin.LISTS` |
| `Toolbar` and the `Button` enum | `withToolbar("bold italic | ...")` |
| `addCustomSetting("name: value")` | `with("name", value)` |
| `TinyMceAjaxButton`, `TinyMceAjaxSubmitLink` | a plain `AjaxButton` or `AjaxSubmitLink` |
| `TinyMCESettings.Language` | `with("language", "nl").with("language_url", url)` |

HugeRTE renamed the JavaScript global from `tinymce` to `hugerte`, so your own JavaScript must use `hugerte.*`. HugeRTE also includes the TinyMCE 7.0 core changes: `sandbox_iframes`, `convert_unsafe_embeds` and `highlight_on_focus` default to `true`.

The [`hugerte-examples`](https://github.com/wicketstuff/core/tree/master/hugerte-parent/hugerte-examples) module contains a runnable example application.

Source: [`hugerte-parent`](https://github.com/wicketstuff/core/tree/master/hugerte-parent)
