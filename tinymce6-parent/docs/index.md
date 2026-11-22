---
title: TinyMCE 6
category: js
description: WYSIWYG rich-text editing with TinyMCE. Licensed under the LGPL.
artifactId: wicketstuff-tinymce6
tags: editor html wysiwyg
---

WYSIWYG rich-text editing with TinyMCE. Licensed under the LGPL.

## Maven

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-tinymce6</artifactId>
</dependency>
```

## TinyMCE version

This module uses TinyMCE 6.8.6, the last MIT-licensed TinyMCE release, and will not be upgraded. TinyMCE 7 and later are licensed GPL-2.0-or-later (or commercially) and require a license key; the build refuses a TinyMCE 7+ webjar. TinyMCE 6 no longer receives fixes from Tiny.

Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

The [`tinymce6-examples`](https://github.com/wicketstuff/core/tree/wicket-11.x/tinymce6-parent/tinymce6-examples) module contains a runnable example application.

Source: [`tinymce6-parent`](https://github.com/wicketstuff/core/tree/wicket-11.x/tinymce6-parent)
