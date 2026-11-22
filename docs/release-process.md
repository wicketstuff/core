---
title: Release process
---

We try and release once per month and as soon as possible following each wicket release.

If there hasn't been a wicket release we will release a point release on the current stable branch.

Sometimes there is a divergence in the API between the current wicket snapshot and the stable release.  In this case we will try to revert commits to allow the build to work but if that doesn't work we will just wait until the next upstream release.

# Steps to create New Version

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

## First we set the start point on the current 10.x branch:

`git checkout master`

## Second we create a local branch to stage the stable release into.

`git checkout -b core-11.1.0 master`

## Third we edit the `pom.xml` and change **<wicket.version>** in the properties section to be **11.1.0**.

```xml
<wicket.version>11.1.0</wicket.version>
```

## Fourth we change the **scm** section url to point at the tag that will be created for this release.  e.g.

```xml
<scm>
<url>https://github.com/wicketstuff/core/tree/wicketstuff-core-11.1.0</url>
```

## Fifth we change all of the pom versions to be **11.1.0**:

```text
mvn versions:set -DgenerateBackupPoms=false -DnewVersion=11.1.0
```

## Sixth we check if there are any snapshot dependencies:

```text
find . -name pom.xml | while read POM; do grep -H SNAPSHOT $POM; done;
```

There will be some matches because there are projects that are currently disabled.  We are interested in modules that are reachable with SNAPSHOT artifacts present (they need to either be fixed if possible or commented out).

## Seventh we build the release artifacts and stage them through `central.sonatype.org`:

A) Build the core artifacts

```text
MAVEN_OPTS=-Xmx512m mvn -DperformRelease=true [-Dgpg.passphrase=yourGpgPassPhrase] clean deploy
```

## Eight we commit any changes to the local **core-11.1.0** stable branch and tag the release:

```text
git commit -a -m "wicketstuff-core-11.1.0 build was successful"
git tag -s wicketstuff-core-11.1.0 -m "wicketstuff-core-11.1.0 release tag"
```
Enter your GPG phass phrase to sign the tag using the same key used for the release

## Ninth we push the release tag into github

```text
git push origin wicketstuff-core-11.1.0
```
### Creating the release message.

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

### Getting the list of new modules

```
git log --name-status --diff-filter=A wicketstuff-core-11.0.0..wicketstuff-core-11.1.0
```
Then we look for new top level directories being created and add then to the release message if found.
