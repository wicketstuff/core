---
title: Contributing a new project
---

The project acceptance criteria are very broad: as long as it is Wicket related, you are welcome to add your module.

## Project layout

Create a new subdirectory in the root of the repository to contain your project artifacts. The suggested structure is:

* `yourproject-parent` with **pom** packaging.
* `yourproject-parent/yourproject` with **jar** packaging.
* `yourproject-parent/yourproject-examples` with **war** packaging: a web application that demonstrates your project.
* `yourproject-parent/docs/index.md`: the documentation of your project, see [Writing module documentation](writing-module-documentation.md).

The **preferred** naming convention for folders is to not include _wicket_ or _wicketstuff_ in the name, just a descriptive name. **But** remember to **prefix** the **artifactId** of your JAR module with _wicketstuff-_. If you want to prefix all of your artifacts, please do so.

For example, the folder is `datatable-autocomplete`, not `wicketstuff-datatable-autocomplete`, and the artifact is:

```xml
<artifactId>wicketstuff-datatable-autocomplete</artifactId>
```

Then add the top level directory of your project to the `<modules>` section of the root `pom.xml`. This tells the build to also process your module, and gets your project included in the snapshot and release versions of Wicket Stuff. The `wicketstuff-bom` module must stay the last module in that list: the BOM is generated from all other modules in the build.

Example and test-only modules are not deployed to Maven Central. List them in the `excludeArtifacts` of the `central-publishing-maven-plugin` in the root `pom.xml` and in the `dependencyExclusions` of `wicketstuff-bom/pom.xml`.

## Java version

The Java version is set by the `maven.compiler.release` property in the root `pom.xml`. If your project needs a different version, override this property in your project's pom, for example:

```xml
<properties>
    <maven.compiler.release>17</maven.compiler.release>
</properties>
```

The build compiles with `javac --release` for that version, using the JDK that runs Maven, see [Building Wicket Stuff](building.md).

## Commit messages and pull requests

Make the first line of a commit message informative: name the module it affects and give a brief note about the change. Readers can look up the full commit message for the details.

GitHub generates the release notes from the merged pull requests and groups them by label (configured in `.github/release.yml` on `master`): `breaking-change`, `improvement`, `bug` and `dependencies`. Pull requests labeled `ignore-for-release` are left out. Give your pull request the label that fits.
