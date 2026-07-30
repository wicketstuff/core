# wicket-jquery-ui
**jQuery UI &amp; Kendo UI integration in Wicket 10.x**

Part of [WicketStuff](https://github.com/wicketstuff/core); the sources live in
the [`wicketstuff-jquery-ui-parent`](https://github.com/wicketstuff/core/tree/master/wicketstuff-jquery-ui-parent)
module.

[![Build](https://github.com/wicketstuff/core/actions/workflows/ci.yml/badge.svg)](https://github.com/wicketstuff/core/actions/workflows/ci.yml)

## Getting started
In order to get started using this API, you may download appropriate jar(s) or set a maven dependency.

### Download jar(s)
If you are not using maven, you need to download the core jar here:
<https://repo1.maven.org/maven2/org/wicketstuff/wicketstuff-jquery-ui/>

You may also download other jars as required (ie: `wicketstuff-kendo-ui`).
Once done, just include the jar(s) in your project's build path.

### Using Maven

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-jquery-ui</artifactId>
    <version>10.1.0</version>
</dependency>
```
```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-kendo-ui</artifactId>
    <version>10.1.0</version>
</dependency>
```

If the version you specified is *snapshot*, you might define this repository:

```xml
<repositories>
    <repository>
        <id>sonatype-snapshots</id>
        <name>Sonatype Snapshots Repository</name>
        <url>http://oss.sonatype.org/content/repositories/snapshots/</url>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
</repositories>
```

### Versions

<table>
<tr><th>wicket-jquery-ui</th><th>Wicket</th><th>jQuery UI</th></tr>
<tr><td>9.11.0</td><td>9.11.0</td><td>1.13.2</td></tr>
<tr><td>9.9.1</td><td>9.9.1</td><td>1.12.1</td></tr>
<tr><td>9.9.0</td><td>9.9.1</td><td>1.12.1</td></tr>
<tr><td>9.8.0</td><td>9.8.0</td><td>1.12.1</td></tr>
<tr><td>9.7.0</td><td>9.7.0</td><td>1.12.1</td></tr>
<tr><td>9.6.0</td><td>9.6.0</td><td>1.12.1</td></tr>
<tr><td>9.5.0</td><td>9.5.0</td><td>1.12.1</td></tr>
<tr><td>9.3.0</td><td>9.3.0</td><td>1.12.1</td></tr>
<tr><td>9.2.1</td><td>9.2.0</td><td>1.12.1</td></tr>
<tr><td>9.2.0</td><td>9.2.0</td><td>1.12.1</td></tr>
<tr><td>9.2.0</td><td>9.2.0</td><td>1.12.1</td></tr>
<tr><td>9.1.0</td><td>9.1.0</td><td>1.12.1</td></tr>
<tr><td>9.0.0</td><td>9.0.0</td><td>1.12.1</td></tr>
</table>

<table>
<tr><th>wicket-kendo-ui</th><th>Wicket</th><th>Kendo UI</th></tr>
<tr><td>10.10.0</td><td>10.10.0</td><td>2025.3.1002 (ASFv2)</td></tr>
<tr><td>9.11.0</td><td>9.11.0</td><td>2022.3.913 (ASFv2)</td></tr>
<tr><td>9.9.1</td><td>9.9.1</td><td>2022.2.510 (ASFv2)</td></tr>
<tr><td>9.8.0</td><td>9.7.0</td><td>2021.3.1207 (ASFv2)</td></tr>
<tr><td>9.7.0</td><td>9.7.0</td><td>2021.3.1207 (ASFv2)</td></tr>
<tr><td>9.6.0</td><td>9.6.0</td><td>2021.3.914 (ASFv2)</td></tr>
<tr><td>9.5.0</td><td>9.5.0</td><td>2021.3.914 (ASFv2)</td></tr>
<tr><td>9.3.1</td><td>9.3.1</td><td>2021.1.119 (ASFv2)</td></tr>
<tr><td>9.3.0</td><td>9.3.0</td><td>2021.1.119 (ASFv2)</td></tr>
<tr><td>9.2.1</td><td>9.2.1</td><td>2021.1.119 (ASFv2)</td></tr>
<tr><td>9.2.0</td><td>9.2.0</td><td>2020.3.915 (ASFv2)</td></tr>
<tr><td>9.1.0</td><td>9.1.0</td><td>2020.3.915 (ASFv2)</td></tr>
<tr><td>9.0.0</td><td>9.0.0</td><td>2020.3.915 (ASFv2)</td></tr>
</table>

## Setting up the jQuery UI theme

### Using standard themes
To use wicket-jquery-ui with a standard theme, just add the corresponding theme dependency to your webapp pom

```xml
<dependency>
	<groupId>org.wicketstuff</groupId>
	<artifactId>wicketstuff-jquery-ui-theme-uilightness</artifactId>
	<version>10.1.0</version>
</dependency>
```

### Using a custom theme
Please follow the instruction on [How to change resource references](https://github.com/sebfz1/wicket-jquery-ui/wiki/%5Bhowto%5D-change-resource-references)

## Setting up the Kendo UI theme

### Using standard themes
To use wicket-kendo-ui with a standard theme, just add the corresponding theme dependency to your webapp pom

```xml
<dependency>
	<groupId>org.wicketstuff</groupId>
	<artifactId>wicketstuff-kendo-ui-theme-default</artifactId>
	<version>10.1.0</version>
</dependency>
```

### Using a custom theme
Please follow the instruction on [How to change resource references](https://github.com/sebfz1/wicket-jquery-ui/wiki/%5Bhowto%5D-change-resource-references)

## Widgets requiring the commercial (pro) Kendo UI distribution

The bundled `kendo.ui.core.min.js` is built from Telerik's open-source
[kendo-ui-core](https://github.com/telerik/kendo-ui-core) (Apache License 2.0).
A few widgets have been removed from that open-source core over time and are now
only shipped in the commercial *Kendo UI for jQuery* distribution. This module
still provides Java wrappers for them, but they only work if you replace the
bundled core resource reference with a commercial `kendo.all.min.js` on your
classpath (see [How to change resource references](https://github.com/sebfz1/wicket-jquery-ui/wiki/%5Bhowto%5D-change-resource-references)).

Currently affected (removed from Kendo UI Core after the 2022.x line):

- **TreeView** (`org.wicketstuff.kendo.ui.widget.treeview`)
- **Editor** (`org.wicketstuff.kendo.ui.widget.editor`)

These wrappers are validated only against the widgets' client-side API and are
not exercised by the samples running on Kendo UI Core. If you use them with a
commercial distribution and hit an API mismatch, please open an issue.

## You are now ready to use wicket-jquery-ui!
Look at the samples in the `wicketstuff-jquery-ui-samples` module to get started
with the code. To run them locally:

1. Build the modules (from the repository root):
   ```
   mvn install -DskipTests -pl wicketstuff-jquery-ui-parent/wicketstuff-jquery-ui-samples -am
   ```
2. Run the `org.wicketstuff.jquery.ui.samples.Start` class (in the
   `wicketstuff-jquery-ui-samples` module, under `src/test/java`). It starts an
   embedded Jetty server. Run it from your IDE, or from the command line:
   ```
   cd wicketstuff-jquery-ui-parent/wicketstuff-jquery-ui-samples
   mvn test-compile
   java -cp "target/classes:target/test-classes:$(mvn -q dependency:build-classpath -Dmdep.outputFile=/dev/stdout -DincludeScope=test)" org.wicketstuff.jquery.ui.samples.Start
   ```
   The working directory must be the `wicketstuff-jquery-ui-samples` module, as
   `Start` loads the web application from the relative path `src/main/webapp`.
3. Open <http://localhost:8080/wicket-jquery-ui> — the application is mounted on
   the `/wicket-jquery-ui` context path, **not** the root (opening `/` returns a
   404).

