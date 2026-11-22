---
title: Migrate from wicketstuff-tinymce6
---

`wicketstuff-hugerte` is a new integration for HugeRTE rather than a renamed copy of `wicketstuff-tinymce6`. Follow the steps below to move an existing application from `wicketstuff-tinymce6` to `wicketstuff-hugerte`.

## 1. Replace the Maven dependency and imports

Replace `wicketstuff-tinymce6` with `wicketstuff-hugerte` in your `pom.xml`:

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-hugerte</artifactId>
</dependency>
```

Both modules load the editor from a webjar. `wicketstuff-hugerte` installs `wicket-webjars` itself when your application doesn't, so `WicketWebjars.install(this)` in `init()` is optional. Update imports from `org.wicketstuff.tinymce6` (and its `.ajax` and `.settings` subpackages) to `org.wicketstuff.hugerte`.

`wicketstuff-hugerte` has no `TinyMceInitializer` or `TinyMceRequestMapper`; all editor assets are served through `wicket-webjars`.

## 2. Replace TinyMceBehavior and TinyMCESettings

Replace `TinyMceBehavior` with `HugeRteBehavior`, and replace the mutable `TinyMCESettings` class with the immutable `HugeRteConfig` record. Because every `with...` method on `HugeRteConfig` returns a new instance, chain the calls or assign each return value.

| wicketstuff-tinymce6 | wicketstuff-hugerte |
|---|---|
| `TinyMceBehavior` | `HugeRteBehavior` |
| `TinyMCESettings` | `HugeRteConfig.defaults()` |
| `TinyMCE6Plugin.lists` (lowercase) | `HugeRtePlugin.LISTS` (uppercase) |
| `settings.addPlugins(...)` | `config.withPlugins(...)` |
| `new Toolbar("toolbar1", ...)` and `Button` | `config.withToolbar("row 1", "row 2")` |
| `settings.addCustomSetting("name: value")` | `config.with("name", value)` |
| `TinyMceAjaxButton`, `TinyMceAjaxSubmitLink`, `TinyMceAjaxSubmitModifier` | plain `AjaxButton` or `AjaxSubmitLink` |
| `TinyMCESettings.Language` | `config.with("language", ...).with("language_url", ...)` |

Unlike `TinyMceBehavior`, a single `HugeRteBehavior` instance can be attached to more than one component, and it accepts an `IModel<HugeRteConfig>` for per-request configuration.

### Option method mapping

| TinyMCESettings method | HugeRteConfig equivalent |
|---|---|
| `setMenuBar(boolean)` | `withMenubar(boolean)` |
| `setStatusBar(boolean)` | `withStatusbar(boolean)` |
| `setReadOnly(boolean)` | `withReadonly(boolean)`, or disable the component |
| `setContentCss(String)` | `withContentCss(String)` |
| `setInLine(Boolean)` | Not supported: `HugeRteBehavior` edits textareas only |
| `setConvertUrls(boolean)` | `with("convert_urls", boolean)` |
| `setRelativeUrls(Boolean)` | `with("relative_urls", boolean)` |
| `setRemoveScriptHost(Boolean)` | `with("remove_script_host", boolean)` |
| `setBrowserSpellcheck(boolean)` | `with("browser_spellcheck", boolean)` |
| `setDocumentBaseUrl(String)` | `with("document_base_url", String)` |
| `setTypeaheadUrls(boolean)` | `with("typeahead_urls", boolean)` |
| `setResizing(Boolean)` | `with("resize", boolean)` |
| `setBlockFormats(String)` | `with("block_formats", String)` |

Check two default differences when migrating:

- `TinyMCESettings` emitted `statusbar: false` and `typeahead_urls: false` by default. `HugeRteConfig.defaults()` emits `{}` so HugeRTE's own defaults apply (`statusbar` defaults to `true`). Call `.withStatusbar(false)` if you want the status bar hidden.
- `TinyMCESettings` emitted `promotion: false`; HugeRTE has no upgrade promotion button. HugeRTE does show a "Powered by HugeRTE" link in the status bar by default; hide it with `.with("branding", false)`.

## 3. Convert plugins, toolbars, and custom settings

Update `TinyMCE6Plugin` constants to uppercase `HugeRtePlugin` constants (both enums list the same 30 plugins). Replace `Toolbar` objects with toolbar strings passed to `withToolbar(String...)`. Replace raw `addCustomSetting` strings with typed Java values (`String`, `Boolean`, numbers, `List`, `Map`), or `RawJavaScript` for JavaScript callbacks.

Before (`wicketstuff-tinymce6`):

```java
TinyMCESettings settings = new TinyMCESettings();
settings.addPlugins(TinyMCE6Plugin.lists, TinyMCE6Plugin.link);
settings.addToolbar(new Toolbar("toolbar1", "undo redo | bold italic"));
settings.addToolbar(new Toolbar("toolbar2", "bullist numlist | link"));
settings.setMenuBar(false);
settings.addCustomSetting("style_formats: [{ title: 'Lead', block: 'p', classes: 'lead' }]");

TextArea<String> text = new TextArea<>("text", model);
text.add(new TinyMceBehavior(settings));
form.add(new TinyMceAjaxButton("save") { /* ... */ });
```

After (`wicketstuff-hugerte`):

```java
HugeRteConfig config = HugeRteConfig.defaults()
    .withPlugins(HugeRtePlugin.LISTS, HugeRtePlugin.LINK)
    .withToolbar(
        "undo redo | bold italic",
        "bullist numlist | link")
    .withMenubar(false)
    .withStatusbar(false)
    .with("style_formats", List.of(
        Map.of("title", "Lead", "block", "p", "classes", "lead")));

TextArea<String> text = new TextArea<>("text", model);
text.add(new HugeRteBehavior(config));
form.add(new AjaxButton("save") { /* ... */ });
```

## 4. Simplify Ajax submits and component toggling

Remove `TinyMceAjaxButton`, `TinyMceAjaxSubmitLink`, and `TinyMceAjaxSubmitModifier`, and use standard `AjaxButton` and `AjaxSubmitLink`. `hugerte-wicket.js` triggers `hugerte.triggerSave()` before every Wicket Ajax call and removes editors automatically when Wicket Ajax replaces or hides their components. See [Save editor content with Ajax](ajax.md).

## 5. Migrate language packs

`wicketstuff-hugerte` has no `TinyMCESettings.Language` enum, and the HugeRTE webjar includes no language packs. Serve your language `.js` files and configure `language` and `language_url` as shown in [Use a language pack](language-packs.md).

## 6. Re-enable Content Security Policy

If your application disabled Wicket's Content Security Policy for `wicketstuff-tinymce6` (for example with `getCspSettings().blocking().clear()`), remove that call and add `HugeRteCsp.allow(getCspSettings().blocking())` in `WebApplication.init()`. See [Allow HugeRTE under Wicket's Content Security Policy](csp.md).

## 7. Update custom JavaScript and check HugeRTE core defaults

HugeRTE renamed the browser global from `tinymce` (and `tinyMCE`) to `hugerte`. Update any custom JavaScript in your application (for example `tinymce.get(...)`, `tinymce.triggerSave()`, or `tinymce.addI18n(...)`) to use `hugerte.*`.

HugeRTE also includes the TinyMCE 7.0 core security and accessibility defaults:

- `sandbox_iframes` defaults to `true` (embedded iframes inside content are sandboxed).
- `convert_unsafe_embeds` defaults to `true` (`<object>` and `<embed>` elements are converted to safer alternatives).
- `highlight_on_focus` defaults to `true` (focused editors display a focus outline).

If your content relies on the earlier behavior, override those options with `HugeRteConfig.with(...)`.
