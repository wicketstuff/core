# wicket-jquery-ui
**jQuery UI integration in Wicket 1.5.x &amp; Wicket 6.x**

## Artifacts
- jQuery UI `wicket-jquery-ui`
- Calendar `wicket-jquery-ui-calendar`
- Kendo UI `wicket-kendo-ui`

## Getting started
In order to get started using this API, you may download appropriate jar(s) or set a maven dependency.

### Download jar(s)
If you are not using maven, you need to download the core jar here:
<https://oss.sonatype.org/index.html#nexus-search;quick~wicket-jquery-ui>

You may also download other jars as required (ie: `wicket-kendo-ui`).
Once done, just include the jar(s) in your project's build path.

### Using Maven

```xml
<dependency>
    <groupId>com.googlecode.wicket-jquery-ui</groupId>
    <artifactId>wicket-jquery-ui</artifactId>
    <version>6.8.1</version> <!-- or 1.5.10 -->
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
<tr><td>6.8.1</td><td>6.8.0</td><td>1.10.3</td></tr>
<tr><td>6.7.0</td><td>6.7.0</td><td>1.10.2</td></tr>
<tr><td>6.2.1</td><td>6.6.0</td><td>1.10.1</td></tr>
<tr><td>6.2.0</td><td>6.5.0</td><td>1.9.2</td></tr>
<tr><td>6.1.0</td><td>6.1.0</td><td>1.8.23</td></tr>
<tr><td>1.5.10</td><td>1.5.10</td><td>1.10.2</td></tr>
<tr><td>1.4.1</td><td>1.5.9</td><td>1.10.1</td></tr>
<tr><td>1.3.0</td><td>1.5.8</td><td>1.8.23</td></tr>
</table>

## Setting up the HTML markup
jQuery UI needs a CSS to work. No jQuery UI CSS has been shipped in the API, so you are free to use the one you wish.
You can get your own CSS at <http://jqueryui.com/themeroller/>, and put it (the CSS folder only) into your website.

The base template page is the following: (you might change the CSS href accordingly)
```html
<!DOCTYPE html>
<html xmlns:wicket="http://wicket.apache.org">
    <head>
        <link rel="stylesheet" type="text/css" href="css/ui-lightness/jquery-ui-1.10.3.custom.min.css" />
    </head>
    <body>
    </body>
</html>
```

It is highly recommended to strip wicket tags from the HTML rendering.
```java
/**
 * @see org.apache.wicket.Application#init()
 */
@Override
public void init()
{
	super.init();

	this.getMarkupSettings().setStripWicketTags(true); //IMPORTANT!
}
```

### Setting up the HTML markup, for Kendo UI
Kendo UI also needs CSSs to work. You need to download your own copy of Kendo UI: <http://www.kendoui.com/get-kendo-ui.aspx>.

Once done, you have to copy `kendo.common.min.css` and `kendo.choosen_theme.min.css` in into you webapp,
as well as the `texture` folder and the corresponding `choosen_theme` folder.

The base template page is then the following: (you might change the CSS href accordingly)
```html
<!DOCTYPE html>
<html xmlns:wicket="http://wicket.apache.org">
    <head>

        <!-- jQuery UI -->
        <link rel="stylesheet" type="text/css" href="css/ui-lightness/jquery-ui-1.10.3.custom.min.css" />

        <!-- Kendo UI -->
        <link rel="stylesheet" type="text/css" href="css/kendo/kendo.common.min.css" media="all" />
        <link rel="stylesheet" type="text/css" href="css/kendo/kendo.default.min.css" media="all" />
    </head>
    <body>
    </body>
</html>
```

## You are now ready to use wicket-jquery-ui!
Look at the samples provided in the demo site to get started with the code...
<http://www.7thweb.net/wicket-jquery-ui>
