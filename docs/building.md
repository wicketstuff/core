---
title: Building Wicket Stuff
---

## Requirements

* Maven 3.5.0 or newer (enforced by the build).
* A JDK for the Java version in the `maven.compiler.source` property of the root `pom.xml` (currently 21), registered in your Maven toolchains.

The `wicket-10.x` branch builds with Java 17, while `master` and `wicket-11.x` use Java 21.

## Toolchains

The build uses [Maven toolchains](https://maven.apache.org/guides/mini/guide-using-toolchains.html) to pick the JDK: the `maven-toolchains-plugin` asks for a JDK whose version matches `maven.compiler.source`. You need a `toolchains.xml` in your `~/.m2` directory that provides that version, for example:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<toolchains>
	<toolchain>
		<type>jdk</type>
		<provides>
			<version>21</version>
		</provides>
		<configuration>
			<jdkHome>/path/to/jdk-21</jdkHome>
		</configuration>
	</toolchain>
</toolchains>
```

[`config/toolchains.xml`](https://github.com/wicketstuff/core/blob/wicket-11.x/config/toolchains.xml) is the file the CI build uses: it maps every version to `${env.JAVA_HOME}`, so it works when the JDK that runs Maven is the one you need.

## Building

```text
mvn install
```

Add `-Djavadoc.disabled=true` to skip the javadoc generation, as the CI build does.

## Continuous integration

Every push to the branch and every pull request against it is built by the GitHub Actions workflow in [`.github/workflows/ci.yml`](https://github.com/wicketstuff/core/blob/wicket-11.x/.github/workflows/ci.yml). The results are in the [Actions tab](https://github.com/wicketstuff/core/actions) of the repository; no login is required.

The workflow builds with Java 21 (Temurin) and runs:

```text
mvn -s sonatype-settings.xml verify -Pdo-publish -Djavadoc.disabled=true -B -V
```

For a push (not for a pull request) it runs `deploy` instead of `verify`, which publishes the snapshot artifacts to the Sonatype Central snapshot repository.

Dependabot (configured in [`.github/dependabot.yml`](https://github.com/wicketstuff/core/blob/wicket-11.x/.github/dependabot.yml)) opens pull requests for dependency and GitHub Actions updates.
