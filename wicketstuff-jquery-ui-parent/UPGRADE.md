# Upgrading the bundled Kendo UI libraries

wicketstuff-kendo-ui bundles the open-source Kendo UI **core** assets (JS,
cultures, messages) plus a handful of theme stylesheets. These come from two
independent upstream projects:

- **Core JS + cultures + messages** — built from
  [telerik/kendo-ui-core](https://github.com/telerik/kendo-ui-core) (ASFv2).
- **Theme CSS** (`<theme>-main.css` / `<theme>-main-dark.css`, and the shared
  `utils` stylesheet) — taken from the pre-built `dist/` of the
  `@progress/kendo-theme-*` npm packages. Modern Kendo no longer ships per-theme
  fonts/textures or the old `kendo.common*.min.css` / `.mobile.min.css` files.

Only the themes that are actual Maven modules are bundled: `bootstrap`,
`default`, `material`, `meridian`, and the shared `utils` stylesheet.

The [`wicket-kendo-ui-upgrade.sh`](wicket-kendo-ui-upgrade.sh) script does the
whole upgrade — download, build, and copy into the module resource trees. You
do **not** build anything by hand; you only set three version numbers and run it.

## Steps

1. **Pick the versions** and set them at the top of `wicket-kendo-ui-upgrade.sh`:

   - `KENDO_VERSION` — the `kendo-ui-core` git tag
     (see <https://github.com/telerik/kendo-ui-core/tags>).
   - `KENDO_THEME_VERSION` — the `@progress/kendo-theme-*` version that pairs
     with that core release. It is listed under **"Supported themes:"** in the
     GitHub release body:
     ```bash
     gh api repos/telerik/kendo-ui-core/releases/tags/$KENDO_VERSION --jq .body
     ```
   - `KENDO_DRAWING_VERSION` — `@progress/kendo-drawing` is imported by
     `kendo.color.js` but is **not** declared in kendo-ui-core's `package.json`,
     so the script installs it explicitly to get a self-contained core (see the
     note below). Upstream pins no version; use the latest `1.x` released at or
     just before `KENDO_VERSION`'s date (the color API is stable across `1.x`).

2. **Run the script** (needs `bash`, `curl`, `unzip`, `node`, `npm`):

   ```bash
   cd wicketstuff-jquery-ui-parent
   ./wicket-kendo-ui-upgrade.sh
   ```

   It downloads and builds the core, fetches the theme packages, and copies
   everything into the resource trees. The 2026.x kendo-ui-core build (Rolldown)
   applies the Apache license banner itself and emits source maps aligned to it,
   so the core/cultures/messages JS are copied verbatim; only the theme CSS
   (which ships without a banner) gets the header prepended by the script. Its
   work happens under `target/` (removed by `mvn clean`).

3. **Post-upgrade checks** (the script also prints these):
   - New cultures/messages — create the corresponding enums if needed.
   - Each theme `Initializer` still references the correct `<theme>-main.css`.
   - Update the Kendo UI version in [`README.md`](README.md) (and the versions at
     the top of the script).
   - Build the reactor and smoke-test the samples application.

## Why the script installs kendo-drawing (self-contained core)

`src/kendo.color.js` imports `Color` / `parseColor` / `namedColors` from
`@progress/kendo-drawing`, but that package is **not** listed as a dependency of
kendo-ui-core. A stock `npm run build` therefore cannot resolve it, and rollup
silently leaves it as an external `require("@progress/kendo-drawing")`. A core
built that way **throws at load** unless a separate kendo-drawing script is also
present, because the color utilities are dereferenced during core init.

To avoid shipping a second dependency, the script instead builds a
**self-contained** core: it `npm install`s `@progress/kendo-drawing` and then
runs the build directly via `npm run scripts:min` (not `npm run build`, whose
inner `npm ci` would wipe the just-installed package). Because
`kendo.ui.core.js` carries a `'bundle all';` marker, Rolldown treats it as
`external: []` and **inlines** all imports, including kendo-drawing. The
resulting `kendo.ui.core.min.js` loads with no external kendo-drawing, matching
the historically bundled file. The script also asserts the built core no longer
contains `require("@progress/kendo-drawing")` and aborts if it does.

## Widgets no longer in Kendo UI Core (pro-only)

Some widgets that this module wraps have been **removed from the open-source
kendo-ui-core** and are now only available in the commercial *Kendo UI for
jQuery* distribution — currently **TreeView** and **Editor** (dropped after the
2022.x line). A from-source build of kendo-ui-core therefore cannot include
them, and their sample pages render an empty/unstyled element against the
bundled core. The Java wrappers are kept so they work when a user swaps in a
commercial `kendo.all.min.js`. When upgrading, don't treat these as a build
regression — verify against the upstream `src/kendo.ui.core.js` import list
whether a widget is still part of core:

```bash
grep -c 'kendo.treeview.js\|kendo.editor.js' \
  target/kendo-upgrade/kendo-ui-core-$KENDO_VERSION/src/kendo.ui.core.js
```
