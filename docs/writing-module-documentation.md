---
title: Writing module documentation
---

Every module keeps its documentation in a `docs/` folder next to its `pom.xml`, for example `select2-parent/docs/`. The website, [wicketstuff.org](https://wicketstuff.org), is built from these folders on the LTS branch and the newest release branch (see [Release lines](release-lines.md)), and its module catalog is generated from the front matter of each module's `docs/index.md`. Project-wide pages, like this one, live in the `docs/` folder in the root of the repository.

## docs/index.md

Every published module needs a `docs/index.md`. It starts with YAML front matter:

```yaml
---
title: Select2
category: js
description: Searchable select boxes with tagging and Ajax-loaded options, based on Select2.
artifactId: wicketstuff-select2
tags: dropdown choice autocomplete
---
```

| Field | Required | Meaning |
|---|---|---|
| `title` | yes | Name of the module as shown in the catalog and as the page title. |
| `category` | yes | One of the catalog categories below. |
| `description` | yes | One or two sentences on what the module does. |
| `artifactId` | if published | The main artifact a user adds as a dependency. Write `groupId:artifactId` when the group isn't `org.wicketstuff`, for example `org.wicketstuff.foundation:wicket-foundation-core`. Leave it out for modules that aren't published as a dependency. |
| `tags` | no | Extra search words, separated by spaces. |

Quote a value when it contains `: ` or starts with a character YAML treats specially, such as `@`, `"` or `[`.

The categories are:

| `category` | Catalog section |
|---|---|
| `ui` | Components |
| `js` | JavaScript libraries |
| `data` | Data & reporting |
| `security` | Security |
| `web` | REST & web |
| `infra` | Runtime |
| `dev` | Developer tools |

## Body

The body is plain GitHub-flavored Markdown: no Jekyll layout, no Liquid tags. It should read well both on GitHub and on the website.

* Don't repeat the title as a heading; the title comes from the front matter. Start with a short introduction and use `##` for the sections below it.
* Show the Maven dependency without a `<version>`: users import `org.wicketstuff:wicketstuff-bom` in their `dependencyManagement` section, and the BOM manages the version.
* Don't write Wicket or Wicket Stuff version numbers into the text ("since 6.19.0", "tested with Wicket 7"). The documentation on a branch describes that branch.
* Use fenced code blocks with a language (` ```java `, ` ```xml `, ` ```html `).

A minimal page:

````markdown
---
title: My Module
category: ui
description: What it does, in one or two sentences.
artifactId: wicketstuff-my-module
---

What it does, in one or two sentences.

## Maven

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-my-module</artifactId>
</dependency>
```

Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

## Usage

...
````

## More pages, images and links

* Put further pages in the same `docs/` folder. They only need `title` in their front matter. Link to them by file name, for example `[Getting started](gettingstarted.md)`.
* Put images in the same `docs/` folder and reference them relatively: `![Screenshot](screenshot.png)`.
* Link to anything outside `docs/` (source code, the examples module, a README) with an absolute GitHub URL, such as `https://github.com/wicketstuff/core/tree/wicket-10.x/select2-parent/select2-examples`. Relative links out of `docs/` work on GitHub but not on the website.

## Removed modules

When a module is removed from a release line, the pull request that removes it also moves the module's `docs/` folder to `docs/removed/<name>/` in the root of the repository, on the same branch. `<name>` is the module's directory without `-parent`, for example `docs/removed/my-module/` for `my-module-parent`.

The `index.md` keeps its front matter and gets these fields:

| Field | Required | Meaning |
|---|---|---|
| `removed_in` | yes | The first Wicket Stuff version without the module, for example `12.0.0`. |
| `last_release` | yes | The last Wicket Stuff version that contained the module, for example `11.0.0`. |
| `replacement` | no | What to use instead, as text or a Markdown link. |

```yaml
---
title: My Module
category: ui
description: What it did, in one or two sentences.
artifactId: wicketstuff-my-module
removed_in: 12.0.0
last_release: 11.0.0
replacement: "[Other Module](https://wicketstuff.org/modules/other-module/)"
---
```

The website shows a removed module under its old URL with a "removed" banner, and leaves it out of the main catalog. A module is removed from `master` first, but stays on the LTS branch, which the website's main documentation comes from, until the LTS line switches. Keeping the page under `docs/removed/` on every branch that dropped the module means links to it keep working after the LTS line drops it too, and readers learn what happened to it and what to use instead.
