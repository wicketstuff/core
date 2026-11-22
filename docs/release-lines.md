---
title: Release lines
---

Wicket Stuff follows the release lines of Apache Wicket: each Wicket major has a matching Wicket Stuff major, and Wicket Stuff maintains the lines that Wicket maintains.

## Wicket's release policy

Since Wicket 11.0.0 a new Wicket major ships every three months, and every fourth major is a long-term support (LTS) release. Wicket maintains two lines at a time:

* The current LTS line gets security fixes and applicable bug fixes until the next LTS release.
* The current quarterly line gets fixes until the next quarterly release replaces it.

Wicket 10 is the current LTS line and Wicket 14 will be the next. The [Wicket versions page](https://wicket.apache.org/versions/) has the support dates of every line.

## Branches

* `master` is always the next major, for example `12.0.0-SNAPSHOT` while Wicket 11 is the current quarterly line.
* A `wicket-N.x` branch exists for every released line, for example `wicket-11.x`. The releases of that line are made from it.
* Besides `master` only two branches are live: the branch of the LTS line and the branch of the newest quarterly line. The other `wicket-N.x` branches are end of life.

The website, [wicketstuff.org](https://wicketstuff.org), builds its main documentation from the LTS branch, and a second copy from the newest `wicket-N.x` branch.

## Where changes go

Features and dependency upgrades go to `master` only. They reach users with the next major. The exception is Dependabot, which also opens minor and patch updates against the live branches (configured in `.github/dependabot.yml` on `master`).

Fixes go to `master` first, and are then backported to the live quarterly branch, and to the LTS branch when they apply there. A fix for code that only exists on a release branch goes to that branch directly.

## Backporting a fix

Once the fix is merged on `master`, backport it with one pull request per branch:

1. Create a branch from the release branch, named after the master branch with the line as suffix, for example `wicket15-tree-csp-11.x` from `wicket-11.x`.
2. Cherry-pick the commit from `master` onto it. When it doesn't apply cleanly, resolve the conflicts and say in the pull request what had to change.
3. Open a pull request against the release branch. Prefix its title with the line, for example `[11.x]`, start the description with "Backport of #NNNN" and give it the same label as the original.
4. Build and test it on the release branch; the Java version can differ between branches, see [Building Wicket Stuff](building.md).
5. Merge it with a merge commit, so the cherry-picked commit lands unchanged.

For example, [#1722](https://github.com/wicketstuff/core/pull/1722) fixed `wicket15-tree` on `master`, and [#1723](https://github.com/wicketstuff/core/pull/1723) and [#1724](https://github.com/wicketstuff/core/pull/1724) backported it to `wicket-11.x` and `wicket-10.x`.

## A new major

When Wicket releases a new major, `master` becomes that line's `wicket-N.x` branch and moves on to the major after it. The previous quarterly line then reaches its end of life, unless it is the LTS line. The steps are in [Release process](release-process.md#new-major-version).

## End of life

When Wicket stops maintaining a line, Wicket Stuff stops maintaining it too. Its branch gets a note at the top of its `README.md` saying that the branch is no longer maintained and which branch to use instead, as [#1711](https://github.com/wicketstuff/core/pull/1711) did for `wicket-9.x`. After that the branch gets no more changes and no more releases, and its Dependabot entry is removed from `.github/dependabot.yml` on `master`.

## Switching LTS lines

The LTS line switches when the next LTS major is released. That release ends both the old LTS line and the previous quarterly line, so for a while the new LTS branch is the only live branch besides `master`, until the next quarterly major adds a branch again. The website then shows only the documentation of the new LTS branch.
