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
| `HugeRteConfig withContentCss(ResourceReference contentCss)` | Shorthand for `with("content_css", contentCss)` with a style sheet served by Wicket, such as a `CssResourceReference`. |
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
| `ResourceReference` | Kept as is | The reference's URL as an absolute path including the context path (or a full URL when it maps to another host), as an escaped string. Needs an active request cycle; without one `toJavaScript()` throws `IllegalStateException`. |
| `Collection<?>` | Recursively normalized into an unmodifiable `List` | JavaScript array `[...]` |
| `Map<?, ?>` | Keys must be `String`; values recursively normalized into an unmodifiable `LinkedHashMap` | JavaScript object `{...}` with escaped string keys |

String escaping converts `"` to `\"`, `\` to `\\`, and `\n`, `\r`, `\t`, `\b`, `\f` to their standard escapes. The characters `<`, `>`, `&`, `'`, `\u2028`, `\u2029`, and control characters below `0x20` are written as `\uXXXX` so strings cannot close or alter an enclosing `<script>` element (`</script>`, `<!--`, `]]>`).

### Reserved options and validation errors

Five option names are reserved and cannot be passed to `with(String, Object)` or placed in `options`:

- `selector` (`HugeRteBehavior` attaches the editor through `target` instead)
- `target` (set by `Wicket.HugeRte.init`)
- `plugins` (use `withPlugins(...)`)
- `toolbar` (use `withToolbar(...)` or `withoutToolbar()`)
- `inline` (inline mode is not supported; HugeRTE doesn't allow it on a textarea)

Errors thrown by `HugeRteConfig` construction and `with(...)`:

| Condition | Exception |
|---|---|
| `option` name is `null` | `NullPointerException` |
| `option` is `selector`, `target`, `plugins`, `toolbar`, or `inline`, or the `mobile` map contains `inline` | `IllegalArgumentException` |
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

`public class HugeRteBehavior extends Behavior` attaches HugeRTE to a `<textarea>`; the textarea stays the form component. HugeRTE's inline mode is not supported. A single instance holds no per-component state and can be added to multiple components.

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
| `protected String getInitScript(Component component)` | Returns `Wicket.HugeRte.init("<escapedMarkupId>",<configJs>);` with the configuration from `getConfig(component)`. |
| `protected HugeRteConfig getConfig(Component component)` | Returns `getConfig()`, with `withReadonly(true)` applied when `component.isEnabledInHierarchy()` is `false`. Override it to vary the configuration per component. |
| `public HugeRteConfig getConfig()` | Returns `config.getObject()`. Throws `NullPointerException` if the model returns `null`. |
| `public void detach(Component component)` | Calls `config.detach()` and `super.detach(component)`. |
| `public static void markSaved(AjaxRequestTarget target, Component component)` | Prepends `Wicket.HugeRte.markSaved("<markupId>");`, which runs before components are replaced: the editor is no longer dirty and the autosave plugin's draft is discarded, unless the editor changed after this Ajax call was sent. Call it after storing the content. |
| `public void onComponentTag(Component component, ComponentTag tag)` | Throws `MarkupException` when the tag is not a `<textarea>`. |
| `public static void focus(AjaxRequestTarget target, Component component)` | Appends `Wicket.HugeRte.focus("<markupId>");`, which focuses the editor once it is initialized. Use it instead of `target.focusComponent(...)`, which focuses the hidden textarea. |

## RawJavaScript

```java
public record RawJavaScript(String code) implements Serializable
```

Wraps a JavaScript expression to be emitted verbatim inside `HugeRteConfig.toJavaScript()`. The canonical constructor rejects a `null` `code` with `NullPointerException` and blank code with `IllegalArgumentException`. Because no escaping is performed, never construct `RawJavaScript` from user input.

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

`public final class HugeRteJavaScriptReference extends JavaScriptResourceReference` references the package resource `hugerte-wicket.js`. `get()` can be called from `WebApplication.init()`: the webjar references are only created on first use. `editor()` needs wicket-webjars to be installed; during `init()`, call `WicketWebjars.install(this)` before using it.

| Member | Description |
|---|---|
| `public static HugeRteJavaScriptReference get()` | Returns the singleton instance. |
| `public static ResourceReference editor()` | Returns the HugeRTE webjar script: `hugerte.js` in development mode, `hugerte.min.js` otherwise. The path uses the version placeholder from the installed `WebjarsSettings`. Created on first use and cached. |
| `public List<HeaderItem> getDependencies()` | Returns Wicket's Ajax script (`getJavaScriptLibrarySettings().getWicketAjaxReference()`) followed by `editor()`. |

## HugeRteInitializer

`public class HugeRteInitializer implements IInitializer`, registered in `META-INF/services/org.apache.wicket.IInitializer`. It adds an `IApplicationListener` whose `onAfterInitialized` calls `WicketWebjars.install(application)` when `WicketWebjars.isInstalled(application)` is `false`. Because it runs after the application's `init()`, settings passed to `WicketWebjars.install(this, settings)` in `init()` are kept.

## Client-side JavaScript (`hugerte-wicket.js`)

[`hugerte-wicket.js`](https://github.com/wicketstuff/core/tree/master/hugerte-parent/hugerte/src/main/java/org/wicketstuff/hugerte/hugerte-wicket.js) initializes once (a second load exits immediately if `Wicket.HugeRte` is already defined) and exposes three functions plus two Wicket event subscriptions:

- `Wicket.HugeRte.init(id, config)` — looks up `document.getElementById(id)` (returns if not found), removes any existing editor returned by `hugerte.get(id)`, and calls `hugerte.init` with a copy of `config` whose `target` is the element and whose `setup` first installs the integration and then calls your own `setup`. When the configuration has `mobile` options, which HugeRTE applies over the others on phones and tablets, their `setup` is wrapped the same way, and a read-only editor stays read-only there. The integration:
  - removes the textarea's `required` attribute while the editor is active (a hidden required field blocks the browser's form submit; Wicket validates on the server) and restores it on the editor's `detach` event, after HugeRTE has restored the textarea's other attributes;
  - forwards the editor's `change`, `undo` and `redo` events as `change`, and `blur` as `blur`, to the textarea after copying the content to it;
  - removes the editor, just after it finishes initializing, when its textarea was already removed;
  - focuses the editor if `Wicket.HugeRte.focus` was called before it was ready.
- `Wicket.HugeRte.focus(id)` — when an editor with that id exists, focuses it (or does so once it is initialized) and stops Wicket from moving the focus back to the element that triggered the Ajax call.
- `Wicket.HugeRte.markSaved(id)` — for an initialized editor whose `getContent()` still equals the content it had when the last Wicket Ajax call was sent: calls `editor.setDirty(false)`, discards the autosave plugin's draft with `removeDraft()`, and calls `editor.nodeChanged()` so the save and restore buttons update. This relies on Wicket queueing Ajax calls on the same channel, so the last call sent is the one being answered.
- `Wicket.Event.Topic.AJAX_CALL_BEFORE` (`'/ajax/call/before'`) — copies every initialized editor's content to its textarea with `editor.save({ set_dirty: false })`, so Wicket serializes current values and `editor.isDirty()` is left unchanged, and remembers each editor's `getContent()` for `markSaved`.
- `Wicket.Event.Topic.DOM_NODE_REMOVING` (`'/dom/node/removing'`) — iterates over `hugerte.get()` and calls `editor.remove()` for every editor whose target element is `node` or a descendant of `node`.
