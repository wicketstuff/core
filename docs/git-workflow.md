---
title: Git workflow
---

The main git documentation is at <https://git-scm.com/doc>.

## Branches

`master` builds against the next major version of Wicket. Each Wicket release line has its own `wicket-N.x` branch (for example `wicket-11.x`), from which the releases for that line are made. Make your change on the branch of the release line it is meant for.

## Pull requests

You can **fork** the project on GitHub, make your changes and then create a pull request from your fork. Committers can also push a branch to this repository and open the pull request from there. Every pull request is built by the [CI workflow](building.md#continuous-integration).

## Shared history

There is a central repository (GitHub), and each developer has a local clone where they make their changes. In git each clone is a full copy of the remote repository, with all of the commit, branch and tag history.

```text
Developer Git:                               GitHub

Local Branch
(wicket-11.x)
       |
      \ /
Remote Branch                    -->          Branch
(remotes/origin/wicket-11.x)                  (wicket-11.x)
```

From your local repository you can 'pull' changes from the GitHub branch into your remote branch and then merge those changes into your local branch.

If you are a committer you can 'push' the changes (possibly several commits) you have made on your local branch to GitHub, which updates both your remote branch (a copy of the GitHub branch) and the GitHub branch.

**Important:** Git has many tools for altering the commit history of a branch. While these are useful for local work they should not be used on the published branches on GitHub. If you change the commit history of a branch that has been shared with others, it will break all of those users the next time they pull down new changes.
