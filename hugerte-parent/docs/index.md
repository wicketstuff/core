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

Add a `HugeRteBehavior` to a `TextArea`. The textarea stays the form component: Wicket reads, converts and validates the submitted HTML as for any other `TextArea`.

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

## What the integration does

- **The textarea stays the form component.** Wicket reads, converts and validates the submitted HTML as for any other `TextArea`. Only `<textarea>` is supported: other tags fail with a `MarkupException`, and HugeRTE's inline mode isn't available.
- **The editor follows component state.** A component that is disabled in its hierarchy gets a read-only editor, also on phones and tablets. `setRequired(true)` is validated by Wicket on the server; the browser's own `required` check is suspended while the editor is active, because on the hidden textarea it would block the submit without telling the user why.
- **Ajax works with ordinary components.** Before every Wicket Ajax call the editors copy their content to their textareas, so a plain `AjaxButton` or `AjaxSubmitLink` submits the edited text. The editor's `change`, `undo`, `redo` and `blur` reach the textarea, so `AjaxFormComponentUpdatingBehavior` and `OnChangeAjaxBehavior` work. See [Save editor content with Ajax](ajax.md).
- **Re-rendering and hiding clean up.** When Wicket Ajax replaces or removes a component, its editor is removed; a re-rendered component gets a new editor with the model value.
- **Unsaved changes are tracked.** Copying content to the textarea doesn't mark the editor as saved, so `isDirty()` and the autosave and save plugins keep working. `HugeRteBehavior.markSaved` marks it as saved, but only for the content the current request sent: edits made while the request was underway, or content that never reached the model, stay unsaved.
- **The server can focus the editor.** `HugeRteBehavior.focus` puts the cursor in the editor; `AjaxRequestTarget.focusComponent` would focus the hidden textarea.
- **Wicket resources work as options.** A `ResourceReference`, such as a style sheet, a language pack, an upload endpoint or a plugin, is written as an absolute URL that includes the context path. See [Use a language pack](language-packs.md) and [Use JavaScript callbacks with RawJavaScript](javascript-callbacks.md).
- **The configuration is safe to render.** `HugeRteConfig` is an immutable, serializable record. Values are validated and escaped, so they can't break out of the page's script element; callbacks go through `RawJavaScript`.
- **It works under Wicket's strict Content Security Policy.** `HugeRteCsp.allow` adds only what HugeRTE's styles need and leaves `script-src` alone. See [Allow HugeRTE under Wicket's Content Security Policy](csp.md).
- **No extra setup.** The HugeRTE scripts are served by wicket-webjars, which the module installs when the application hasn't.

## Sanitize the submitted HTML

The editor submits HTML. HugeRTE cleans pasted content in the browser, but anyone can post arbitrary HTML to your form, so treat the model value as untrusted. Before you render it with `setEscapeModelStrings(false)` or store it for display elsewhere, clean it on the server with an allowlist sanitizer such as the OWASP Java HTML Sanitizer or jsoup's `Safelist`.

## Guides and reference

- [Allow HugeRTE under Wicket's Content Security Policy](csp.md)
- [Save editor content with Ajax](ajax.md)
- [Use JavaScript callbacks with RawJavaScript](javascript-callbacks.md)
- [Change the configuration per request](dynamic-config.md)
- [Use a language pack](language-packs.md)
- [Migrate from wicketstuff-tinymce6](migrating-from-tinymce6.md)
- [Reference](reference.md)

## Examples

The [`hugerte-examples`](https://github.com/wicketstuff/core/tree/wicket-11.x/hugerte-parent/hugerte-examples) module contains an example application. To run it, build it from the root of the repository and start Jetty in the module:

```sh
mvn -DskipTests install -pl hugerte-parent/hugerte-examples -am
cd hugerte-parent/hugerte-examples
mvn org.eclipse.jetty.ee11:jetty-ee11-maven-plugin:run -Djetty.http.port=8080
```

Then open <http://localhost:8080/>. The examples show the default editor, an editor with every plugin, and Ajax saving and re-rendering. The application keeps Wicket's strict Content Security Policy, with `HugeRteCsp` added.

Source: [`hugerte-parent`](https://github.com/wicketstuff/core/tree/wicket-11.x/hugerte-parent)
