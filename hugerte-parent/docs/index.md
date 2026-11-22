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

## Application setup

Add HugeRTE's Content Security Policy rules in your `WebApplication.init()` method:

```java
@Override
protected void init()
{
    super.init();
    HugeRteCsp.allow(getCspSettings().blocking());
}
```

The module installs `wicket-webjars`, which serves the HugeRTE scripts, after your `init()` when the application hasn't installed it itself. Call `WicketWebjars.install(this, settings)` in `init()` if you need your own webjars settings.

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

`HugeRteConfig` is an immutable record. Plugins and toolbar rows have typed methods; every other [HugeRTE option](https://hugerte.org) is set with `with(name, value)`.

HugeRTE shows a "Powered by HugeRTE" link in the status bar by default. Its MIT licence does not require the link; hide it with `with("branding", false)`.

## Guides and reference

- [Allow HugeRTE under Wicket's Content Security Policy](csp.md)
- [Save editor content with Ajax](ajax.md)
- [Use JavaScript callbacks with RawJavaScript](javascript-callbacks.md)
- [Change the configuration per request](dynamic-config.md)
- [Use a language pack](language-packs.md)
- [Migrate from wicketstuff-tinymce6](migrating-from-tinymce6.md)
- [Reference](reference.md)

## Examples

The [`hugerte-examples`](https://github.com/wicketstuff/core/tree/wicket-10.x/hugerte-parent/hugerte-examples) module contains an example application. To run it, build it from the root of the repository and start Jetty in the module:

```sh
mvn -DskipTests install -pl hugerte-parent/hugerte-examples -am
cd hugerte-parent/hugerte-examples
mvn org.eclipse.jetty.ee10:jetty-ee10-maven-plugin:run -Djetty.http.port=8080
```

Then open <http://localhost:8080/>. The examples show the default editor, an editor with every plugin, and Ajax saving and re-rendering. The application keeps Wicket's strict Content Security Policy, with `HugeRteCsp` added.

Source: [`hugerte-parent`](https://github.com/wicketstuff/core/tree/wicket-10.x/hugerte-parent)
