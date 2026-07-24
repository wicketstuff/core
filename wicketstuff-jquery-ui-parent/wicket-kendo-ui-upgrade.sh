#!/bin/bash
set -euo pipefail

##############################################################################
# Upgrade the embedded Kendo UI assets bundled by wicketstuff-kendo-ui.
#
# This script fetches, builds and copies the assets end-to-end. It sources
# from TWO independent, upstream projects:
#
#   A) Core JS + cultures + messages
#      -> telerik/kendo-ui-core (open source, ASFv2). Downloaded by tag and
#         built from source (see section A for why NOT via `npm run build`);
#         output lands in dist/.
#
#   B) Theme CSS (<theme>-main.css / <theme>-main-dark.css / utils all.css)
#      -> the @progress/kendo-theme-* npm packages, which SHIP the compiled
#         CSS pre-built in their dist/ folder (no SASS build needed here).
#         Modern Kendo (>= ~2023) no longer ships per-theme fonts/textures or
#         the old kendo.common*.min.css / *.mobile.min.css files.
#
# All downloads/builds happen under target/ (git-ignored, removed by
# `mvn clean`). Only the final assets are written into the module resource
# trees.
#
# Prerequisites: bash, curl, unzip, node, npm.
#
# Usage:
#   cd wicketstuff-jquery-ui-parent
#   ./wicket-kendo-ui-upgrade.sh
##############################################################################

# ---------------------------------------------------------------------------
# VERSIONS — edit these to upgrade.
#
# KENDO_VERSION       : the kendo-ui-core git tag (see https://github.com/telerik/kendo-ui-core/tags)
# KENDO_THEME_VERSION : the @progress/kendo-theme-* npm version that pairs with
#                       that core release. These version independently from the
#                       core; the matching theme version is listed under
#                       "Supported themes:" in the GitHub release body for the
#                       KENDO_VERSION tag, e.g.:
#                         gh api repos/telerik/kendo-ui-core/releases/tags/$KENDO_VERSION --jq .body
#                       Keep the four theme packages on the SAME version.
# ---------------------------------------------------------------------------
KENDO_VERSION="2025.3.1002"
KENDO_THEME_VERSION="12.0.1"   # per the "Supported themes" list in the 2025.3.1002 release notes

# KENDO_DRAWING_VERSION : @progress/kendo-drawing is imported by src/kendo.color.js
#   but is NOT declared in kendo-ui-core's package.json, so a stock build cannot
#   resolve it and rollup silently externalises it (the resulting core then throws
#   at load unless a separate kendo-drawing is present). We install it explicitly
#   below so the build INLINES it, producing a self-contained core (matching the
#   historically bundled file). Upstream pins no version; pick the latest 1.x
#   released at/just before KENDO_VERSION's date (the color API is stable across 1.x).
KENDO_DRAWING_VERSION="1.22.1"

# Themes that are actual Maven modules (see wicketstuff-kendo-ui-themes/pom.xml).
THEMES=(bootstrap default material)

# ---------------------------------------------------------------------------
# Layout
# ---------------------------------------------------------------------------
repo="$(cd "$(dirname "$0")" && pwd)"   # wicketstuff-jquery-ui-parent
work="$repo/target/kendo-upgrade"
core_src="$work/kendo-ui-core-$KENDO_VERSION"
core_dist="$core_src/dist"
themes_dl="$work/themes"

res=src/main/resources/org/wicketstuff/kendo/ui
repo_js="$repo/wicketstuff-kendo-ui/$res/resource"
repo_cultures="$repo/wicketstuff-kendo-ui-culture/$res/resource/cultures"
repo_messages="$repo/wicketstuff-kendo-ui-culture/$res/resource/messages"
repo_themes_root="$repo/wicketstuff-kendo-ui-themes"
repo_theme_res="$res/theme"

# ---------------------------------------------------------------------------
# Prerequisite check
# ---------------------------------------------------------------------------
for tool in curl unzip node npm; do
  command -v "$tool" >/dev/null 2>&1 || { echo "ERROR: '$tool' is required but not on PATH." >&2; exit 1; }
done

mkdir -p "$work"

# ---------------------------------------------------------------------------
# License header
#
# The upstream kendo-ui-core build output and the @progress/kendo-theme-* dist
# CSS do NOT carry a license header. Kendo UI is (re)distributed here under the
# Apache License 2.0, so we prepend this header to every bundled JS/CSS file
# (matching the existing bundle).
# ---------------------------------------------------------------------------
license_header() {
cat <<'EOF'
/**
 * Copyright 2025 Progress Software Corporation and/or one of its subsidiaries or affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
EOF
}

# Number of lines the license header occupies (computed, so it stays correct if
# the header text is ever edited).
HEADER_LINES="$(license_header | wc -l | tr -d ' ')"

# Copy $1 -> $2 with the license header prepended.
copy_with_header() { { license_header; cat "$1"; } > "$2"; }

# Copy a source map $1 -> $2, shifting its mappings down by HEADER_LINES so it
# still lines up with the .js AFTER we prepend the license header. A source-map
# "mappings" string encodes one line per ';'-separated segment, so prepending
# HEADER_LINES semicolons offsets every mapping by that many lines. The build
# emits maps against the header-less JS; without this shift every mapped
# position would be wrong by the header height.
copy_map_with_shift() {
  local src="$1" dst="$2"
  KENDO_MAP_SHIFT="$(printf ';%.0s' $(seq 1 "$HEADER_LINES"))" \
  node -e '
    const fs = require("fs");
    const m = JSON.parse(fs.readFileSync(process.argv[1], "utf8"));
    m.mappings = process.env.KENDO_MAP_SHIFT + (m.mappings || "");
    fs.writeFileSync(process.argv[2], JSON.stringify(m));
  ' "$src" "$dst"
}

##############################################################################
# A) Fetch + build kendo-ui-core (JS + cultures + messages)
#
# We deliberately do NOT run `npm run build` (which is `npm ci && gulp build`):
# its inner `npm ci` wipes node_modules and reinstalls strictly from the
# lockfile, which does not include @progress/kendo-drawing. Instead we:
#   1. npm ci
#   2. npm install @progress/kendo-drawing (so rollup can resolve + INLINE it)
#   3. run rollup directly, then the gulp minify tasks
# This yields a SELF-CONTAINED core (drawing inlined) that loads without a
# separate kendo-drawing script. Running the stock `npm run build` instead
# would externalise drawing and the core would throw at load.
##############################################################################
echo ">> [A] kendo-ui-core $KENDO_VERSION"

# Cache only the downloaded zip; always extract a FRESH source tree so a stale
# dist/ or node_modules from a previous run can never be silently reused (that
# would defeat the drawing-inlining step below).
core_zip="$work/kendo-ui-core-$KENDO_VERSION.zip"
if [ ! -f "$core_zip" ]; then
  echo "   downloading source tag ..."
  curl -fL -o "$core_zip" \
    "https://github.com/telerik/kendo-ui-core/archive/refs/tags/${KENDO_VERSION}.zip"
fi
echo "   extracting fresh source tree ..."
rm -rf "$core_src"
unzip -q -o "$core_zip" -d "$work"

echo "   installing dependencies (npm ci) ..."
( cd "$core_src" && npm ci )
echo "   adding @progress/kendo-drawing@$KENDO_DRAWING_VERSION so it gets inlined ..."
( cd "$core_src" && npm install --no-audit --no-fund "@progress/kendo-drawing@$KENDO_DRAWING_VERSION" )
echo "   compiling (rollup) — this can take a while ..."
# rollup -c => dist/raw-js ; then gulp 'scripts' minifies raw-js => dist/js.
# NB: invoke gulp's 'scripts' task, NOT 'build'/'ci' (those re-run npm ci and
# would wipe the kendo-drawing we just installed).
( cd "$core_src" \
    && node --max-old-space-size=8192 ./node_modules/rollup/dist/bin/rollup -c \
    && npx gulp scripts )

# sanity: the build must have produced what we copy ...
for p in js/kendo.ui.core.min.js js/cultures js/messages; do
  [ -e "$core_dist/$p" ] || { echo "ERROR: expected build output missing: dist/$p" >&2; exit 1; }
done
# ... and the core must be self-contained (drawing inlined, not externalised).
if grep -q 'require("@progress/kendo-drawing")' "$core_dist/js/kendo.ui.core.min.js"; then
  echo "ERROR: built core still externalises @progress/kendo-drawing — it was not inlined." >&2
  echo "       Check that 'npm install @progress/kendo-drawing' succeeded before the rollup step." >&2
  exit 1
fi

##############################################################################
# B) Download the theme packages (dist CSS is pre-built inside them)
##############################################################################
echo ">> [B] @progress/kendo-theme-* $KENDO_THEME_VERSION"

rm -rf "$themes_dl"; mkdir -p "$themes_dl"
for pkg in "${THEMES[@]}" utils; do
  echo "   fetching @progress/kendo-theme-$pkg@$KENDO_THEME_VERSION ..."
  ( cd "$themes_dl" && npm pack "@progress/kendo-theme-$pkg@$KENDO_THEME_VERSION" >/dev/null )
  tgz=$(ls "$themes_dl"/progress-kendo-theme-"$pkg"-*.tgz)
  mkdir -p "$themes_dl/$pkg"
  tar -xzf "$tgz" -C "$themes_dl/$pkg"   # extracts into $themes_dl/$pkg/package/dist/...
done
themes_dist() { echo "$themes_dl/$1/package/dist"; }

##############################################################################
# Copy CORE js + cultures + messages
##############################################################################
echo ">> copying core assets"

# js (+ license header) and its source map
#
# NB: the vanilla `dist/js/kendo.ui.core.min.js` produced above is a UMD bundle
# that treats jquery and @progress/kendo-drawing as EXTERNAL requires. The
# bundle historically committed here is a different, larger variant (drawing
# neither required externally nor inlined). If byte-parity with the previous
# bundle matters, verify the produced kendo.ui.core.min.js loads correctly in
# the samples; the exact upstream bundle recipe is not reproduced by the
# default build target.
copy_with_header  "$core_dist/js/kendo.ui.core.min.js"     "$repo_js/kendo.ui.core.min.js"
copy_map_with_shift "$core_dist/js/kendo.ui.core.min.js.map" "$repo_js/kendo.ui.core.min.js.map"

# cultures — prepend the license header to the JS and shift each map to match.
for f in "$core_dist"/js/cultures/*.min.js; do
  base="$(basename "$f")"
  copy_with_header    "$f"       "$repo_cultures/$base"
  copy_map_with_shift "$f.map"   "$repo_cultures/$base.map"
done
echo '   check if there are new cultures, and create enums if needed'

# messages — same: header on the JS, shifted map alongside.
for f in "$core_dist"/js/messages/*.min.js; do
  base="$(basename "$f")"
  copy_with_header    "$f"       "$repo_messages/$base"
  copy_map_with_shift "$f.map"   "$repo_messages/$base.map"
done
echo '   check if there are new messages, and create enums if needed'

##############################################################################
# Copy THEME CSS  (license header prepended — see copy_with_header above)
#   bootstrap/default/material -> <theme>-main.css + <theme>-main-dark.css
#   utils                      -> all.css  renamed to  kendo-theme-utils.css
##############################################################################
echo ">> copying theme CSS"

for theme in "${THEMES[@]}"; do
  dest="$repo_themes_root/wicketstuff-kendo-ui-theme-$theme/$repo_theme_res"
  src="$(themes_dist "$theme")"
  copy_with_header "$src/$theme-main.css"       "$dest/$theme-main.css"
  copy_with_header "$src/$theme-main-dark.css"  "$dest/$theme-main-dark.css"
done

# theme-utils: upstream ships this as dist/all.css; the module keeps it as
# kendo-theme-utils.css.
copy_with_header "$(themes_dist utils)/all.css" \
   "$repo_themes_root/wicketstuff-kendo-ui-theme-utils/$repo_theme_res/utils/kendo-theme-utils.css"

echo
echo "Done ($KENDO_VERSION / themes $KENDO_THEME_VERSION / drawing $KENDO_DRAWING_VERSION). Remember to:"
echo " - update the Kendo UI version in README.md (and the versions at the top of this script)"
echo " - verify each theme Initializer still references the correct <theme>-main.css"
echo " - build the reactor and smoke-test the samples application"
