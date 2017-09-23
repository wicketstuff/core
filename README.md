# wicket-jquery-ui
**jQuery UI integration in Wicket Wicket 6.x, Wicket 7.x &amp; Wicket8.x**  
[![TravisCI](https://travis-ci.org/sebfz1/wicket-jquery-ui.svg?branch=wicket8.x)](https://travis-ci.org/sebfz1/wicket-jquery-ui)

## Getting started
In order to get started using this API, you may download appropriate jar(s) or set a maven dependency.

### Download jar(s)
If you are not using maven, you need to download the core jar here:
<http://central.maven.org/maven2/com/googlecode/wicket-jquery-ui/>

You may also download other jars as required (ie: `wicket-kendo-ui`).  
Once done, just include the jar(s) in your project's build path.

### Using Maven

```xml
<dependency>
    <groupId>com.googlecode.wicket-jquery-ui</groupId>
    <artifactId>wicket-jquery-ui</artifactId>
    <version>8.0.0-M7</version>
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
<tr><td>8.0.0-M7</td><td>8.0.0-M7</td><td>1.12.1</td></tr>
<tr><td>8.0.0-M6</td><td>8.0.0-M6</td><td>1.12.1</td></tr>
<tr><td>8.0.0-M5</td><td>8.0.0-M5</td><td>1.12.1</td></tr>
<tr><td>8.0.0-M4</td><td>8.0.0-M4</td><td>1.12.1</td></tr>
<tr><td>8.0.0-M3</td><td>8.0.0-M3</td><td>1.12.1</td></tr>
<tr><td>8.0.0-M2</td><td>8.0.0-M2</td><td>1.12.0</td></tr>
<tr><td>8.0.0-M1</td><td>8.0.0-M1</td><td>1.12.0</td></tr>
<tr><td>7.8.0</td><td>7.8.0</td><td>1.12.1</td></tr>
<tr><td>7.7.0</td><td>7.7.0</td><td>1.12.1</td></tr>
<tr><td>7.6.0</td><td>7.6.0</td><td>1.12.1</td></tr>
<tr><td>7.5.0</td><td>7.5.0</td><td>1.12.0</td></tr>
<tr><td>7.4.0</td><td>7.4.0</td><td>1.12.0</td></tr>
<tr><td>7.3.1</td><td>7.3.0</td><td>1.12.0</td></tr>
<tr><td>7.3.0</td><td>7.3.0</td><td>1.11.4</td></tr>
<tr><td>7.2.0</td><td>7.2.0</td><td>1.11.4</td></tr>
<tr><td>7.1.0</td><td>7.1.0</td><td>1.11.4</td></tr>
<tr><td>7.0.0</td><td>7.0.0</td><td>1.11.4</td></tr>
<tr><td>7.0.0-M6</td><td>7.0.0-M6</td><td>1.11.4</td></tr>
<tr><td>7.0.0-M5</td><td>7.0.0-M5</td><td>1.11.2</td></tr>
<tr><td>6.27.0</td><td>6.27.0</td><td>1.12.1</td></tr>
<tr><td>6.26.0</td><td>6.26.0</td><td>1.12.1</td></tr>
<tr><td>6.25.1</td><td>6.25.0</td><td>1.12.0</td></tr>
<tr><td>6.25.0</td><td>6.25.0</td><td>1.12.0</td></tr>
<tr><td>6.24.0</td><td>6.24.0</td><td>1.12.0</td></tr>
<tr><td>6.20.0</td><td>6.20.0</td><td>1.11.4</td></tr>
<tr><td>6.19.0</td><td>6.19.0</td><td>1.11.2</td></tr>
<tr><td>6.18.0</td><td>6.18.0</td><td>1.11.2</td></tr>
<tr><td>6.17.0</td><td>6.17.0</td><td>1.11.1</td></tr>
<tr><td>6.16.0</td><td>6.16.0</td><td>1.11.0</td></tr>
<tr><td>6.15.0</td><td>6.15.0</td><td>1.10.4</td></tr>
<tr><td>6.14.0</td><td>6.14.0</td><td>1.10.4</td></tr>
<tr><td>6.13.1</td><td>6.13.0</td><td>1.10.4</td></tr>
<tr><td>6.13.0</td><td>6.13.0</td><td>1.10.3</td></tr>
<tr><td>6.12.0</td><td>6.12.0</td><td>1.10.3</td></tr>
<tr><td>6.11.0</td><td>6.11.0</td><td>1.10.3</td></tr>
<tr><td>6.10.0</td><td>6.10.0</td><td>1.10.3</td></tr>
<tr><td>6.9.1</td><td>6.9.0</td><td>1.10.3</td></tr>
<tr><td>6.9.0</td><td>6.9.0</td><td>1.10.3</td></tr>
<tr><td>6.8.1</td><td>6.8.0</td><td>1.10.3</td></tr>
<tr><td>6.7.0</td><td>6.7.0</td><td>1.10.2</td></tr>
<tr><td>1.5.11</td><td>1.5.11</td><td>1.11.0</td></tr>
<tr><td>1.5.10</td><td>1.5.10</td><td>1.10.2</td></tr>
</table>

<table>
<tr><th>wicket-kendo-ui</th><th>Wicket</th><th>Kendo UI</th></tr>
<tr><td>8.0.0-M7</td><td>8.0.0-M7</td><td>2017.2.621 (ASFv2)</td></tr>
<tr><td>8.0.0-M6</td><td>8.0.0-M6</td><td>2017.2.504 (ASFv2)</td></tr>
<tr><td>8.0.0-M5</td><td>8.0.0-M5</td><td>2017.1.223 (ASFv2)</td></tr>
<tr><td>8.0.0-M4</td><td>8.0.0-M4</td><td>2016.3.1118 (ASFv2)</td></tr>
<tr><td>8.0.0-M3</td><td>8.0.0-M3</td><td>2016.3.1118 (ASFv2)</td></tr>
<tr><td>8.0.0-M2</td><td>8.0.0-M2</td><td>2016.3.1028 (ASFv2)</td></tr>
<tr><td>8.0.0-M1</td><td>8.0.0-M1</td><td>2016.2.714 (ASFv2)</td></tr>
<tr><td>7.8.0</td><td>7.8.0</td><td>2017.2.621 (ASFv2)</td></tr>
<tr><td>7.7.0</td><td>7.7.0</td><td>2017.1.223 (ASFv2)</td></tr>
<tr><td>7.6.0</td><td>7.6.0</td><td>2016.3.1118 (ASFv2)</td></tr>
<tr><td>7.5.0</td><td>7.5.0</td><td>2016.3.1028 (ASFv2)</td></tr>
<tr><td>7.4.0</td><td>7.4.0</td><td>2016.2.714 (ASFv2)</td></tr>
<tr><td>7.3.1</td><td>7.3.0</td><td>2016.2.714 (ASFv2)</td></tr>
<tr><td>7.3.0</td><td>7.3.0</td><td>2016.1.112 (ASFv2)</td></tr>
<tr><td>7.2.0</td><td>7.2.0</td><td>2016.1.112 (ASFv2)</td></tr>
<tr><td>7.1.0</td><td>7.1.0</td><td>2015.3.930 (ASFv2)</td></tr>
<tr><td>7.0.0</td><td>7.0.0</td><td>2015.2.624 (ASFv2)</td></tr>
<tr><td>7.0.0-M6</td><td>7.0.0-M6</td><td>2015.1.318 (ASFv2)</td></tr>
<tr><td>7.0.0-M5</td><td>7.0.0-M5</td><td>2014.3.1119 (ASFv2)</td></tr>
<tr><td>6.27.0</td><td>6.27.0</td><td>2017.2.621 (ASFv2)</td></tr>
<tr><td>6.26.0</td><td>6.26.0</td><td>2016.3.1118 (ASFv2)</td></tr>
<tr><td>6.25.1</td><td>6.25.0</td><td>2016.3.1028 (ASFv2)</td></tr>
<tr><td>6.25.0</td><td>6.25.0</td><td>2016.3.1028 (ASFv2)</td></tr>
<tr><td>6.24.0</td><td>6.24.0</td><td>2016.2.714 (ASFv2)</td></tr>
<tr><td>6.20.0</td><td>6.20.0</td><td>2015.1.318 (ASFv2)</td></tr>
<tr><td>6.19.0</td><td>6.19.0</td><td>2014.3.1119 (ASFv2)</td></tr>
<tr><td>6.18.0</td><td>6.18.0</td><td>2014.2.716 (ASFv2)</td></tr>
<tr><td>6.17.0</td><td>6.17.0</td><td>2014.2.716 (ASFv2)</td></tr>
<tr><td>6.16.0</td><td>6.16.0</td><td>2014.1.416 (ASFv2)</td></tr>
<tr><td>6.15.0</td><td>6.15.0</td><td>2014.1.416 (ASFv2)</td></tr>
<tr><td>6.14.0</td><td>6.14.0</td><td>2013.3.1119 (GPLv3)</td></tr>
<tr><td>6.13.1</td><td>6.13.0</td><td>2013.3.1119 (GPLv3)</td></tr>
<tr><td>6.13.0</td><td>6.13.0</td><td>2013.3.1119 (GPLv3)</td></tr>
<tr><td>6.12.0</td><td>6.12.0</td><td>2013.1.319 (GPLv3)</td></tr>
<tr><td>6.11.0</td><td>6.11.0</td><td>2013.1.319 (GPLv3)</td></tr>
<tr><td>6.10.0</td><td>6.10.0</td><td>2013.1.319 (GPLv3)</td></tr>
<tr><td>6.9.1</td><td>6.9.0</td><td>2013.1.319 (GPLv3)</td></tr>
<tr><td>6.9.0</td><td>6.9.0</td><td>2013.1.319 (GPLv3)</td></tr>
<tr><td>6.8.1</td><td>6.8.0</td><td>2013.1.319 (GPLv3)</td></tr>
<tr><td>6.7.0</td><td>6.7.0</td><td>2012.3.1114 (GPLv3)</td></tr>
<tr><td>1.5.11</td><td>1.5.11</td><td>2014.1.416 (ASFv2)</td></tr>
<tr><td>1.5.10</td><td>1.5.10</td><td>2012.3.1114 (GPLv3)</td></tr>
</table>

## Setting up the jQuery UI theme

### Using standard themes
To use wicket-jquery-ui with a standard theme, just add the corresponding theme dependency to your webapp pom

```xml
<dependency>
	<groupId>com.googlecode.wicket-jquery-ui</groupId>
	<artifactId>wicket-jquery-ui-theme-uilightness</artifactId>
	<version>8.0.0-M7</version>
</dependency>
```

### Using a custom theme
Please follow the instruction on [How to change resource references](https://github.com/sebfz1/wicket-jquery-ui/wiki/%5Bhowto%5D-change-resource-references)

## Setting up the Kendo UI theme

### Using standard themes
To use wicket-kendo-ui with a standard theme, just add the corresponding theme dependency to your webapp pom

```xml
<dependency>
	<groupId>com.googlecode.wicket-jquery-ui</groupId>
	<artifactId>wicket-kendo-ui-theme-default</artifactId>
	<version>8.0.0-M7</version>
</dependency>
```

### Using a custom theme
Please follow the instruction on [How to change resource references](https://github.com/sebfz1/wicket-jquery-ui/wiki/%5Bhowto%5D-change-resource-references)

## You are now ready to use wicket-jquery-ui!
Look at the samples provided in the demo site to get started with the code...  
<http://www.7thweb.net/wicket-jquery-ui>
