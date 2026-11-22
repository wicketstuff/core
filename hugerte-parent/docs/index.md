---
title: HugeRTE
category: js
description: WYSIWYG rich-text editing with HugeRTE, the MIT-licensed fork of TinyMCE 6. Licensed under the LGPL.
artifactId: wicketstuff-hugerte
tags: editor html wysiwyg tinymce
---

WYSIWYG rich-text editing with [HugeRTE](https://hugerte.org), the MIT-licensed fork of TinyMCE 6. Licensed under the LGPL.

## Maven

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-hugerte</artifactId>
</dependency>
```

Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

## Usage

```java
HugeRteSettings settings = new HugeRteSettings();
settings.addPlugins(HugeRtePlugin.lists, HugeRtePlugin.link, HugeRtePlugin.table);

TextArea<String> textArea = new TextArea<>("text", model);
textArea.add(new HugeRteBehavior(settings));
```

## Migrating from wicketstuff-tinymce6

The API is the same as `wicketstuff-tinymce6` with new names:

| wicketstuff-tinymce6 | wicketstuff-hugerte |
|---|---|
| `org.wicketstuff.tinymce6` | `org.wicketstuff.hugerte` |
| `TinyMceBehavior` | `HugeRteBehavior` |
| `TinyMCESettings` | `HugeRteSettings` |
| `TinyMCE6Plugin` | `HugeRtePlugin` |
| `TinyMceAjaxButton`, `TinyMceAjaxSubmitLink`, `TinyMceAjaxSubmitModifier` | `HugeRteAjaxButton`, `HugeRteAjaxSubmitLink`, `HugeRteAjaxSubmitModifier` |

HugeRTE renamed the JavaScript global from `tinymce` to `hugerte`. JavaScript of your own that calls `tinymce.*`, including code passed in with `addCustomSetting`, must use `hugerte.*`.

HugeRTE also includes the TinyMCE 7.0 core changes: `sandbox_iframes`, `convert_unsafe_embeds` and `highlight_on_focus` default to `true`, and the `promotion` option no longer exists.

The [`hugerte-examples`](https://github.com/wicketstuff/core/tree/master/hugerte-parent/hugerte-examples) module contains a runnable example application.

Source: [`hugerte-parent`](https://github.com/wicketstuff/core/tree/master/hugerte-parent)
