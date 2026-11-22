---
title: Reference
---

API and JavaScript reference for `org.wicketstuff.hugerte`.

## HugeRteConfig

```java
public record HugeRteConfig(
    Set<HugeRtePlugin> plugins,
    List<String> toolbar,
    boolean toolbarHidden,
    Map<String, Object> options
) implements Serializable
```

Immutable editor configuration. The canonical constructor copies and normalizes all components:

- `plugins` becomes an unmodifiable `EnumSet` copy (or `Set.of()` when empty).
- `toolbar` becomes an unmodifiable copy via `List.copyOf(toolbar)`.
- `toolbarHidden` must be `false` when `toolbar` has rows; otherwise the constructor throws `IllegalArgumentException`.
- `options` becomes an unmodifiable `LinkedHashMap` copy preserving insertion order; every key is validated against the reserved option list and every value is validated and recursively copied into immutable, serializable values.

### Methods

| Method | Description |
|---|---|
| `static HugeRteConfig defaults()` | Returns a shared empty configuration (no plugins, no toolbar rows, no options). |
| `HugeRteConfig withPlugins(HugeRtePlugin... plugins)` | Returns a copy with the given plugins added to existing plugins. |
| `HugeRteConfig withToolbar(String... rows)` | Returns a copy with the toolbar replaced by `rows` (space-separated button names, `\|` group separators). Passing no rows clears the list, omitting `toolbar` so HugeRTE uses its default toolbar. Also makes a hidden toolbar visible again. |
| `HugeRteConfig withoutToolbar()` | Returns a copy that shows no toolbar: `toolbar: false`. |
| `HugeRteConfig with(String option, Object value)` | Returns a copy with `option` set to `value`, replacing any previous value. |
| `HugeRteConfig without(String option)` | Returns a copy with `option` removed (no-op if absent). |
| `HugeRteConfig withMenubar(boolean menubar)` | Shorthand for `with("menubar", menubar)`. |
| `HugeRteConfig withStatusbar(boolean statusbar)` | Shorthand for `with("statusbar", statusbar)`. |
| `HugeRteConfig withReadonly(boolean readonly)` | Shorthand for `with("readonly", readonly)`. |
| `HugeRteConfig withContentCss(String contentCss)` | Shorthand for `with("content_css", contentCss)`. Built-in styles in the webjar: `default`, `dark`, `document`, `writer`, `hugerte-5`, `hugerte-5-dark`, or pass a style sheet URL. For several style sheets or `false`, use `with("content_css", ...)`. |
| `String toJavaScript()` | Serializes the configuration as a JavaScript object literal without `target` or `selector`. Emits `plugins` as an array of plugin ids when non-empty (in enum declaration order), `toolbar` as `false` when hidden, a string for one row or an array of strings for multiple rows, then `options` in insertion order. |

### Supported option value types

`HugeRteConfig` normalizes option values on construction and serializes them with `JavaScriptWriter`:

| Java type | Normalization | JavaScript output |
|---|---|---|
| `CharSequence` | Converted with `toString()` | Double-quoted escaped string |
| `Boolean` | Kept as is | `true` or `false` |
| `Integer`, `Long`, `Short`, `Byte`, `BigInteger`, `BigDecimal` | Kept as is | Unquoted numeric literal |
| `Double`, `Float` | Must satisfy `isFinite(...)` | Unquoted numeric literal |
| `RawJavaScript` | Kept as is | `code()` written verbatim, unescaped |
| `Collection<?>` | Recursively normalized into an unmodifiable `List` | JavaScript array `[...]` |
| `Map<?, ?>` | Keys must be `String`; values recursively normalized into an unmodifiable `LinkedHashMap` | JavaScript object `{...}` with escaped string keys |

String escaping converts `"` to `\"`, `\` to `\\`, and `\n`, `\r`, `\t`, `\b`, `\f` to their standard escapes. The characters `<`, `>`, `&`, `'`, `\u2028`, `\u2029`, and control characters below `0x20` are written as `\uXXXX` so strings cannot close or alter an enclosing `<script>` element (`</script>`, `<!--`, `]]>`).

### Reserved options and validation errors

Four option names are reserved and cannot be passed to `with(String, Object)` or placed in `options`:

- `selector` (`HugeRteBehavior` attaches the editor through `target` instead)
- `target` (set by `Wicket.HugeRte.init`)
- `plugins` (use `withPlugins(...)`)
- `toolbar` (use `withToolbar(...)`)

Errors thrown by `HugeRteConfig` construction and `with(...)`:

| Condition | Exception |
|---|---|
| `option` name is `null` | `NullPointerException` |
| `option` is `selector`, `target`, `plugins`, or `toolbar` | `IllegalArgumentException` |
| Value (top-level or nested inside a `Collection` or `Map`) is `null` | `IllegalArgumentException` |
| `Double` or `Float` is `NaN`, `+Infinity`, or `-Infinity` | `IllegalArgumentException` |
| `Map` contains a key that is not a `String` | `IllegalArgumentException` |
| Value is any other type | `IllegalArgumentException` |

## HugeRtePlugin

`public enum HugeRtePlugin` enumerates the 30 plugins bundled in the HugeRTE webjar. `id()` returns `name().toLowerCase(Locale.ROOT)`.

| Constant | `id()` | Constant | `id()` | Constant | `id()` |
|---|---|---|---|---|---|
| `ACCORDION` | `accordion` | `EMOTICONS` | `emoticons` | `PAGEBREAK` | `pagebreak` |
| `ADVLIST` | `advlist` | `FULLSCREEN` | `fullscreen` | `PREVIEW` | `preview` |
| `ANCHOR` | `anchor` | `HELP` | `help` | `QUICKBARS` | `quickbars` |
| `AUTOLINK` | `autolink` | `IMAGE` | `image` | `SAVE` | `save` |
| `AUTORESIZE` | `autoresize` | `IMPORTCSS` | `importcss` | `SEARCHREPLACE` | `searchreplace` |
| `AUTOSAVE` | `autosave` | `INSERTDATETIME` | `insertdatetime` | `TABLE` | `table` |
| `CHARMAP` | `charmap` | `LINK` | `link` | `TEMPLATE` | `template` |
| `CODE` | `code` | `LISTS` | `lists` | `VISUALBLOCKS` | `visualblocks` |
| `CODESAMPLE` | `codesample` | `MEDIA` | `media` | `VISUALCHARS` | `visualchars` |
| `DIRECTIONALITY` | `directionality` | `NONBREAKING` | `nonbreaking` | `WORDCOUNT` | `wordcount` |

## HugeRteBehavior

`public class HugeRteBehavior extends Behavior` attaches HugeRTE to a `<textarea>` (or, with `with("inline", true)`, another HTML element). A single instance holds no per-component state and can be added to multiple components.

### Constructors

| Constructor | Description |
|---|---|
| `public HugeRteBehavior()` | Uses `HugeRteConfig.defaults()`. |
| `public HugeRteBehavior(HugeRteConfig config)` | Wraps non-null `config` in `Model.of(config)`. Throws `NullPointerException` if `config` is `null`. |
| `public HugeRteBehavior(IModel<HugeRteConfig> config)` | Reads `config` on every render. Throws `NullPointerException` if `config` is `null`. |

### Methods

| Signature | Description |
|---|---|
| `public void bind(Component component)` | Calls `component.setOutputMarkupId(true)`. |
| `public void renderHead(Component component, IHeaderResponse response)` | Contributes `HugeRteJavaScriptReference.get()` via `JavaScriptHeaderItem` and `getInitScript(component)` via `OnDomReadyHeaderItem`. |
| `protected String getInitScript(Component component)` | Returns `Wicket.HugeRte.init("<escapedMarkupId>",<configJs>);`. |
| `public HugeRteConfig getConfig()` | Returns `config.getObject()`. Throws `NullPointerException` if the model returns `null`. |
| `public void detach(Component component)` | Calls `config.detach()` and `super.detach(component)`. |

## RawJavaScript

```java
public record RawJavaScript(String code) implements Serializable
```

Wraps a JavaScript expression to be emitted verbatim inside `HugeRteConfig.toJavaScript()`. The canonical constructor rejects a `null` `code` with `NullPointerException`. Because no escaping is performed, never construct `RawJavaScript` from user input.

## HugeRteCsp

`public final class HugeRteCsp` provides one static method:

```java
public static CSPHeaderConfiguration allow(CSPHeaderConfiguration csp)
```

Adds the following directives to `csp` and returns `csp` for chaining:

| Directive (`CSPDirective`) | Added values |
|---|---|
| `STYLE_SRC` | `CSPDirectiveSrcValue.SELF` (`'self'`) |
| `STYLE_SRC_ATTR` | `CSPDirectiveSrcValue.UNSAFE_INLINE` (`'unsafe-inline'`) |
| `IMG_SRC` | `"data:"`, `"blob:"` |

## HugeRteJavaScriptReference

`public final class HugeRteJavaScriptReference extends JavaScriptResourceReference` references the package resource `hugerte-wicket.js`. Its dependencies are served by `wicket-webjars`, which `HugeRteInitializer` installs when the application hasn't.

| Member | Description |
|---|---|
| `public static final ResourceReference HUGERTE` | `WebjarsJavaScriptResourceReference("hugerte/current/hugerte.js")` |
| `public static final ResourceReference HUGERTE_MIN` | `WebjarsJavaScriptResourceReference("hugerte/current/hugerte.min.js")` |
| `public static HugeRteJavaScriptReference get()` | Returns the singleton instance. |
| `public List<HeaderItem> getDependencies()` | Returns two `JavaScriptHeaderItem` entries: `Application.get().getJavaScriptLibrarySettings().getWicketAjaxReference()`, followed by `HUGERTE` when `Application.get().getConfigurationType() == RuntimeConfigurationType.DEVELOPMENT` or `HUGERTE_MIN` otherwise. |

## HugeRteInitializer

`public class HugeRteInitializer implements IInitializer`, registered in `META-INF/services/org.apache.wicket.IInitializer`. It adds an `IApplicationListener` whose `onAfterInitialized` calls `WicketWebjars.install(application)` when `WicketWebjars.isInstalled(application)` is `false`. Because it runs after the application's `init()`, settings passed to `WicketWebjars.install(this, settings)` in `init()` are kept.

## Client-side JavaScript (`hugerte-wicket.js`)

[`hugerte-wicket.js`](https://github.com/wicketstuff/core/tree/master/hugerte-parent/hugerte/src/main/java/org/wicketstuff/hugerte/hugerte-wicket.js) initializes once (a second load exits immediately if `Wicket.HugeRte` is already defined) and exposes one function plus two Wicket event subscriptions:

- `Wicket.HugeRte.init(id, config)` — looks up `document.getElementById(id)` (returns if not found), removes any existing editor returned by `hugerte.get(id)`, assigns `config.target = element`, wraps `config.setup` so that the editor's `change` and `blur` events are forwarded to the textarea (after `editor.save()`) before any `setup` callback of your own runs, and calls `hugerte.init(config)`.
- `Wicket.Event.Topic.AJAX_CALL_BEFORE` (`'/ajax/call/before'`) — calls `hugerte.triggerSave()` so all active editors write their HTML back to their textareas before Wicket serializes form fields.
- `Wicket.Event.Topic.DOM_NODE_REMOVING` (`'/dom/node/removing'`) — iterates over `hugerte.get()` and calls `editor.remove()` for every editor whose target element is `node` or a descendant of `node`.
