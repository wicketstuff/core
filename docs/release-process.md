---
title: Release process
---

We try and release once per month and as soon as possible following each wicket release.

If there hasn't been a wicket release we will release a point release on the current stable branch.

Sometimes there is a divergence in the API between the current wicket snapshot and the stable release.  In this case we will try to revert commits to allow the build to work but if that doesn't work we will just wait until the next upstream release.

## Steps to create New Version

Preliminaries:

We are assuming that the `pom.xml` has a version of `11.1.0-SNAPSHOT` and we are going to be creating the `11.1.0` release.

Make sure that your ~/.m2/settings.xml contains your credentials to deploy to https://central.sonatype.com:

```xml
<server>
         <id>central-sonatype-repo</id>
         <username>YourUserName</username>
         <password>yourPasswd</password>
</server>
```

***
**IMPORTANT!** ensure `wicket-webjars`, `wicket-bootstrap-core` and `jquery-selectors` were released!

***

### First we set the start point on the branch of the release line, see [Release lines](release-lines.md):

`git checkout wicket-11.x`

### Second we create a local branch to stage the stable release into.

`git checkout -b core-11.1.0 wicket-11.x`

### Third we edit the `pom.xml` and change **<wicket.version>** in the properties section to be **11.1.0**.

```xml
<wicket.version>11.1.0</wicket.version>
```

### Fourth we change the **scm** section url to point at the tag that will be created for this release.  e.g.

```xml
<scm>
<url>https://github.com/wicketstuff/core/tree/wicketstuff-core-11.1.0</url>
```

### Fifth we change all of the pom versions to be **11.1.0**:

```text
mvn versions:set -DgenerateBackupPoms=false -DnewVersion=11.1.0
```

### Sixth we check if there are any snapshot dependencies:

```text
find . -name pom.xml | while read POM; do grep -H SNAPSHOT $POM; done;
```

There will be some matches because there are projects that are currently disabled.  We are interested in modules that are reachable with SNAPSHOT artifacts present (they need to either be fixed if possible or commented out).

### Seventh we build the release artifacts and stage them through `central.sonatype.org`:

A) Build the core artifacts

```text
MAVEN_OPTS=-Xmx512m mvn -DperformRelease=true [-Dgpg.passphrase=yourGpgPassPhrase] clean deploy
```

### Eight we commit any changes to the local **core-11.1.0** stable branch and tag the release:

```text
git commit -a -m "wicketstuff-core-11.1.0 build was successful"
git tag -s wicketstuff-core-11.1.0 -m "wicketstuff-core-11.1.0 release tag"
```
Enter your GPG phass phrase to sign the tag using the same key used for the release

### Ninth we push the release tag into github

```text
git push origin wicketstuff-core-11.1.0
```
#### Creating the release message.

We let **git shortlog** do the heavy lifting of what changed between the **new-release-tag** and the **old-release-tag**.

```text
git shortlog -n old-release-tag..new-release-tag
```

For example the changes between 11.0.0 and 11.1.0 can be extracted as:

```text
git fetch origin --tags
git shortlog wicketstuff-core-11.0.0..wicketstuff-core-11.1.0

Inaiat Henrique (2):
      [meiomask] mootools meiomask wicket 6.x
      [meiomask] added mootols meiomask module to pom

Isammoc (1):
      Merged pull request #25 from lambdadaku/core-6.x.

Michael O'Cleirigh (3):
      datatable-autocomplete: add styling and selection capabilities.
      datatable-autocomplete: add highlight selected row capability.
      commit wicketstuff-core 6.20.0 release pom's

Nick Wiedenbrueck (3):
      [console] backport to wicket-6.x
      [console] removed onInitilize for extension
      Merge branch 'wicket-6.x' into console-6.x

cretzel (3):
      [console] fixing build dependencies
      [console] fixing hibernate driver class
      [console] remove java 6 dependencies

lambdadaku (1):
      Added multiple controls and map types (adapted to latest google maps     2)     - controls: 3d Zoom Control, hierarchical, navlabel, and overview map     - map types: physical, aerial, and aerial 3d

seb (5):
      wicket-shiro, wicket-minis: (license header, serialVersionUID, javadoc)
      ServerHostNameAndTimeFilter backport
      Merge branch 'refs/heads/core-1.4.x' of ssh://git@github.com/wicketstuff/core.git into HEAD
      updated change log
      minis: code cleanu
```

#### Getting the list of new modules

```
git log --name-status --diff-filter=A wicketstuff-core-11.0.0..wicketstuff-core-11.1.0
```
Then we look for new top level directories being created and add then to the release message if found.

### After the release

* Update the version of `wicketstuff-bom` in the `README.md` of the release branch.
* Update the `version` of the line in `releases` in the website's [`_config.yml`](https://github.com/wicketstuff/wicketstuff.github.com/blob/master/_config.yml) (wicketstuff/wicketstuff.github.com).

## New major version

Every new Wicket major starts a new release line, see [Release lines](release-lines.md). In the steps below Wicket 12.0.0 has just been released, `master` is at `12.0.0-SNAPSHOT`, Wicket 11 was the previous quarterly line and Wicket 10 is the LTS line.

### Cut the release branch

Create `wicket-12.x` from `master` and push it:

```text
git checkout -b wicket-12.x origin/master
git push origin wicket-12.x
```

Then make the branch build and link to itself, in a pull request against `wicket-12.x`:

* In `.github/workflows/ci.yml`, change the `push` and `pull_request` branches from `master` to `wicket-12.x`.
* Point the GitHub links in the documentation at the branch: replace `github.com/wicketstuff/core/tree/master` and `github.com/wicketstuff/core/blob/master` with `tree/wicket-12.x` and `blob/wicket-12.x` in `docs/` and in the `docs/` folder of every module, and use `wicket-12.x` in the example link in [Writing module documentation](writing-module-documentation.md).

```text
grep -rlE 'github.com/wicketstuff/core/(tree|blob)/master' --include='*.md' . \
  | xargs sed -i -E 's#(github.com/wicketstuff/core/(tree|blob))/master#\1/wicket-12.x#g'
```

Release 12.0.0 from `wicket-12.x` with the steps above.

### Move master to the next major

In a pull request against `master`:

* Set the version of all poms to the next major's snapshot, and `<wicket.version>` in the root `pom.xml` to the same snapshot:

  ```text
  mvn versions:set -DgenerateBackupPoms=false -DnewVersion=13.0.0-SNAPSHOT
  ```

  ```xml
  <wicket.version>13.0.0-SNAPSHOT</wicket.version>
  ```

* In `.github/dependabot.yml`, add a `maven` entry with `target-branch: "wicket-12.x"`, and remove the entry of the branch that reaches its end of life. Dependabot only reads this file on `master`.

### Check the Java baseline

Check the Java version that Wicket 13 requires. When it differs from `maven.compiler.release` in the root `pom.xml` on `master`, update that property, the Java version in the matrix of `.github/workflows/ci.yml` and the Java version in [Building Wicket Stuff](building.md) on `master`.

[Building Wicket Stuff](building.md) also says which Java version each live branch uses. Update that sentence on `master` and on every live branch.

### End the previous quarterly line

Unless the previous quarterly line is the LTS line, it reaches its end of life with the new major. Add a note at the top of the `README.md` of `wicket-11.x` that the branch is no longer maintained and which branch to use instead, as [#1711](https://github.com/wicketstuff/core/pull/1711) did for `wicket-9.x`. When the new major is an LTS release, the old LTS line ends as well and its branch gets the same note.

### Update the website

In `_config.yml` of the website ([wicketstuff/wicketstuff.github.com](https://github.com/wicketstuff/wicketstuff.github.com)), update `releases`:

* `current`: the new line, with its `version`, `wicket`, `java`, `servlet` and `branch`.
* `lts`: only when the new major starts a new LTS line. Set it to the new line as well, with `supported_until` from the [Wicket versions page](https://wicket.apache.org/versions/). The site's main documentation then comes from the new LTS branch.
