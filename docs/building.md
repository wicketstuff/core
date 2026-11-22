---
title: Building Wicket Stuff
---

## Requirements

* Maven 3.5.0 or newer (enforced by the build).
* A JDK of at least the Java version in the `maven.compiler.release` property of the root `pom.xml` (currently 21). A newer JDK works too: the compiler builds with `--release`, so the classes target that version whatever JDK runs Maven. The build fails at once with a too old JDK.

The `wicket-10.x` branch builds with Java 17, while `master` and `wicket-11.x` use Java 21.

The build uses the JDK that runs Maven (the one in `JAVA_HOME`); no Maven toolchains or `toolchains.xml` are needed.

On JDK 25 and newer `AnnotationsShiroAuthorizationStrategyTest` in `wicketstuff-shiro` fails: Shiro 3 uses ScopedValues there and rejects the `SubjectThreadState` the test sets up. Run the full build with a JDK older than 25, or skip that test by listing `**/AnnotationsShiroAuthorizationStrategyTest.java` in a file and passing its absolute path as `-Dsurefire.excludesFile=/path/to/file`. Don't use `-Dtest` for this: it replaces the test includes and excludes of the poms, so other modules run classes that are not tests.

## Building

```text
mvn install
```

Add `-Djavadoc.disabled=true` to skip the javadoc generation, as the CI build does.

## Continuous integration

Every push to the branch and every pull request against it is built by the GitHub Actions workflow in [`.github/workflows/ci.yml`](https://github.com/wicketstuff/core/blob/master/.github/workflows/ci.yml). The results are in the [Actions tab](https://github.com/wicketstuff/core/actions) of the repository; no login is required.

The workflow builds with Java 21 (Temurin) and runs:

```text
mvn -s sonatype-settings.xml verify -Pdo-publish -Djavadoc.disabled=true -B -V
```

For a push (not for a pull request) it runs `deploy` instead of `verify`, which publishes the snapshot artifacts to the Sonatype Central snapshot repository.

Dependabot (configured in [`.github/dependabot.yml`](https://github.com/wicketstuff/core/blob/master/.github/dependabot.yml)) opens pull requests for dependency and GitHub Actions updates.
