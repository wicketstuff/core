---
title: Use a language pack
---

Set both `language` and `language_url` in `HugeRteConfig` to localize the editor UI. The HugeRTE webjar (`org.webjars.npm:hugerte`) includes English (`en`) built in and ships no language packs.

If you set `language` to a non-English code without `language_url`, HugeRTE requests `langs/<code>.js` from the webjar, which doesn't contain it, so the editor stays in English.

## Serve a language pack from your application

Place the HugeRTE language pack file (for example `nl.js`) on your classpath next to a class in your application and pass it as a `JavaScriptResourceReference`. `HugeRteConfig` accepts resource references as values and writes their URL, with your context path and Wicket's resource versioning, when the editor renders:

```java
private static final ResourceReference NL_LANG =
    new JavaScriptResourceReference(MyPage.class, "langs/nl.js");

TextArea<String> editor = new TextArea<>("text", textModel);
editor.add(new HugeRteBehavior(HugeRteConfig.defaults()
    .with("language", "nl")
    .with("language_url", NL_LANG)));
```

A HugeRTE language file registers its translations on the `hugerte` global using `hugerte.addI18n`:

```js
hugerte.addI18n('nl', {
    'Bold': 'Vet',
    'Italic': 'Cursief'
});
```

If you reuse a TinyMCE 6 language pack file, replace `tinymce.addI18n(` with `hugerte.addI18n(` at the top of the file.

## Match the Wicket session locale

Because `HugeRteBehavior` accepts an `IModel<HugeRteConfig>`, you can select the language pack from `Session.get().getLocale()` on each request. Return the base configuration unchanged for English so HugeRTE does not request an external file:

```java
private static final Map<String, ResourceReference> LANGUAGE_PACKS = Map.of(
    "nl", new JavaScriptResourceReference(MyPage.class, "langs/nl.js"),
    "de", new JavaScriptResourceReference(MyPage.class, "langs/de.js")
);

IModel<HugeRteConfig> configModel = () -> {
    HugeRteConfig base = HugeRteConfig.defaults().withMenubar(false);
    String lang = Session.get().getLocale().getLanguage();
    ResourceReference pack = LANGUAGE_PACKS.get(lang);
    if (pack == null)
    {
        return base;
    }
    return base
        .with("language", lang)
        .with("language_url", pack);
};
```

See [Change the configuration per request](dynamic-config.md) for more on `IModel<HugeRteConfig>`.

## Right-to-left languages

For right-to-left locales, also set `directionality` to `"rtl"` (and optionally add `HugeRtePlugin.DIRECTIONALITY` with `ltr rtl` toolbar buttons):

```java
config.with("language", "ar")
    .with("language_url", NL_LANG)
    .with("directionality", "rtl");
```

## Content Security Policy

HugeRTE loads the `language_url` script through its internal script loader. Under Wicket's strict Content Security Policy (`'strict-dynamic'`), scripts loaded by HugeRTE are already trusted, so no additional `script-src` rule is needed.
