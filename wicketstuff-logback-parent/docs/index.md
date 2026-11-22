---
title: Logback
category: infra
description: "Helpers for using Logback with Wicket: configuration listener and request-aware log patterns."
artifactId: wicketstuff-logback
tags: logging
---

The wicketstuff-logback module is the home for classes that can help with using [Wicket](https://wicket.apache.org) and [Logback](https://logback.qos.ch) together.

Features:

- **LogbackConfigListener** for logback config relocation
- a new conversion word `%web` to enrich your log messages with request information
- **ConfiguratorPage** to inspect and change effective logging level per logger online

## Maven

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-logback</artifactId>
</dependency>
```

Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

## LogbackConfigListener
**LogbackConfigListener** is a ServletContextListener that can be used in web applications to define the location of the logback configuration file and optionally to make the webapp's context path available as a placeholder in the logback config. **LogbackConfigListener** was modelled after spring's Log4jConfigListener which is the same for log4j.

### web.xml snippet
It has to be defined in the **web.xml**. The config location is specified in the `logbackConfigLocation` context-param. The context path placeholder name can optionally be specified with `logbackConfigContextPathKey`.

```xml
<!-- Mandatory: using a web app resource to configure logback -->
<context-param>
	<param-name>logbackConfigLocation</param-name>
	<param-value>/WEB-INF/log-sc.xml</param-value>
</context-param>

<!-- Optional: make context path available in logback config with ${contextPath} -->
<context-param>
	<param-name>logbackConfigContextPathKey</param-name>
	<param-value>contextPath</param-value>
</context-param>

<!-- Mandatory: applies custom config location  -->
<listener>
	<listener-class>org.wicketstuff.logback.LogbackConfigListener</listener-class>
</listener>
```

Where the configuration file is a web application resource. `/WEB-INF/log-sc.xml` is a standard logback config file.

### logbackConfigLocation value
Placeholders (for example ${user.home}) in logbackConfigLocation are supported. Some example locations:

- **/WEB-INF/log-sc.xml** (starts with `/`) -> loaded from servlet context
- **classpath:foo/log-cp.xml** (starts with `classpath:`) -> loaded from classpath
- **file:/D:/log-absfile.xml** (is a valid url) -> loaded as url
- **D:/log-absfile.xml** (is an absolute file path) -> loaded as absolute file
- **log-relfile.xml** (is a relative file path) -> loaded as file relative to the servlet container working directory

## %web conversion word (**WicketWebFormattingConverter**)
**WicketWebFormattingConverter** can automatically add detailed information about the current HttpServletRequest to the log message. This class can be registered with logback's **PatternLayoutEncoder** to add support for a new conversion word: `%web`. It needs to be registered with the `<conversionRule />` element in the logback config.

If **PatternLayoutEncoder** is configured as encoder for a logback appender the `%web` in pattern gets replaced with information about the current request. This involves the http method, url, query string, session id, etc. If no request is available (because for example not in a web request handling thread) the conversion word is replaced with an empty string.

### logback.xml snippet
To use it you have to define it in your logback config (note `%web` in the pattern).

```xml
<conversionRule conversionWord="web"
	converterClass="org.wicketstuff.logback.WicketWebFormattingConverter" />

<appender name="appender" class="ch.qos.logback.core.ConsoleAppender">
	<encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
		<charset>UTF-8</charset>
		<pattern>%d|%p|%t|%c{36}|%r|%web%n\t%caller{1}\t%m%n%xEx</pattern>
	</encoder>
	<filter class="ch.qos.logback.classic.filter.ThresholdFilter">
		<level>debug</level>
	</filter>
</appender>
```

### Example output
Let us say that you have the above logback config and a wicket HomePage logging every construction like this:

```java
public class HomePage extends WebPage {
	private static final long serialVersionUID = 2696517689763106924L;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(HomePage.class);

	public HomePage() {
		LOGGER.info("Logging is good - said the lumberjack.");
	}
}
```

A log message would look like this (note the part in the first line after the last '|'):

```text
2011-02-21 14:18:26,281|INFO|"http-nio-8080"-exec-2|o.w.logback.examples.HomePage|28066|GET http://localhost:8080/wicketstuff-logback-examples/?null null null 127.0.0.1:59363 127.0.0.1:8080 null Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.13 (KHTML, like Gecko) Chrome/9.0.597.98 Safari/534.13
	Caller+0	 at org.wicketstuff.logback.examples.HomePage.<init>(HomePage.java:19)
	Logging is good - said the lumberjack.
```

## ConfiguratorPage
**ConfiguratorPage** is a wicket webpage that can be mounted in your app to do some online work with logback: inspect or modify current logging levels for each Logger registered in logback.

## Dependencies
Wicketstuff-logback depends on [Wicket](https://wicket.apache.org) and [logback-classic](https://logback.qos.ch).

## Further reading
For more info see the javadoc of the classes. For a complete example see the examples web application.

The [`wicketstuff-logback-examples`](https://github.com/wicketstuff/core/tree/wicket-10.x/wicketstuff-logback-parent/wicketstuff-logback-examples) module contains a runnable example application.

Source: [`wicketstuff-logback-parent`](https://github.com/wicketstuff/core/tree/wicket-10.x/wicketstuff-logback-parent)
