---
title: Allow HugeRTE under Wicket's Content Security Policy
---

Call `HugeRteCsp.allow` in your application's `init()` method to keep Wicket's strict Content Security Policy enabled while allowing HugeRTE to render its UI and handle pasted images.

```java
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.hugerte.HugeRteCsp;

public class MyApplication extends WebApplication
{
    @Override
    protected void init()
    {
        super.init();
        HugeRteCsp.allow(getCspSettings().blocking());
    }
}
```

If you run CSP in report-only mode, pass `getCspSettings().reporting()` instead. `HugeRteCsp.allow` returns the `CSPHeaderConfiguration` it modified so you can chain further rules onto it:

```java
HugeRteCsp.allow(getCspSettings().blocking())
    .add(CSPDirective.IMG_SRC, "https://images.example.com");
```

## What HugeRteCsp adds

`HugeRteCsp.allow` adds three rules to the policy:

| Directive | Added source | Reason |
|---|---|---|
| `style-src` | `'self'` | HugeRTE injects `<link>` elements for its skin and content style sheets without a CSP nonce. |
| `style-src-attr` | `'unsafe-inline'` | Both the editor chrome and edited rich text use inline `style` attributes. |
| `img-src` | `data:` `blob:` | Pasted and locally uploaded images are rendered as data and blob URLs before saving. |

## What is not loosened

`script-src` is left untouched. Wicket's strict policy sets `script-src 'strict-dynamic' 'nonce-...'`, and Wicket adds the nonce to the `<script>` elements for HugeRTE and `hugerte-wicket.js`. Because `'strict-dynamic'` trusts scripts loaded by a nonced script, HugeRTE can load its theme, icon, plugin, and language scripts without relaxing `script-src`.

`style-src` does not receive `'unsafe-inline'`. Allowing `'unsafe-inline'` only on `style-src-attr` permits `style="..."` attributes while keeping un-nonced `<style>` elements blocked.

All other directives in Wicket's strict policy (`default-src`, `connect-src`, `font-src`, `manifest-src`, `child-src`, `base-uri`) remain unchanged.

## External resources

If your editor loads style sheets (`content_css`) or images from another origin, add that origin to `style-src` or `img-src` in your application's CSP configuration.

See [`ExampleApplication.java`](https://github.com/wicketstuff/core/tree/wicket-11.x/hugerte-parent/hugerte-examples/src/main/java/org/wicketstuff/hugerte/examples/ExampleApplication.java) for a working setup.
