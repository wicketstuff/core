---
title: JEE Web
category: web
description: Embed servlet, JSP and JSF output in Wicket pages.
artifactId: wicketstuff-jee-web
tags: jsp jsf servlet
---

The `JEEWebResolver` is used to embed Servlet, JSP and JSF content into Wicket HTML pages, by a custom Wicket tag. Because include is used to apply the content, every restriction of include applies to the embedded content (no header modifications and so on). To use it you should register it with the page settings in the `init` method of the Wicket application.

## Maven

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-jee-web</artifactId>
</dependency>
```

Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

## Setup
WebApplication:

```java
@Override
protected void init() {
	super.init();
	getPageSettings().addComponentResolver(new JEEWebResolver());
}
```

## Usage

A tag specifies the location which embed content to load. (The argument is given to the `getRequestDispatcher` method of the `ServletContext`):

```html
<wicket:jsp file="/de/test/jsp/TestPage.jsp"/>

or

<wicket:servlet path="/de/test/servlet/Servlet/">

or

<wicket:jsf file="/TestPage.xhtml"/>
```

## Tags for JSP / JSF
```text
JSP: <%@ taglib prefix="wicket" uri="http://wicketstuff-jee-web.org/functions/jsp" %>
JSF: <div xmlns="http://www.w3.org/1999/xhtml" xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html" xmlns:wicket="http://wicketstuff-jee-web.org/functions/jsf">

Tag: url // Parameters: page(required), query(optional) // Example:
JSP Example: <a href="<wicket:url page="mypage.MyTestPage" query="param1=value1&param2=value2"/>">LINK</a>
JSF Example: Tag is the same but should not be used within a href, please refer to the EL-Functions
```

## EL-Functions for JSP / JSF
```text
JSP: <%@ taglib prefix="wicket" uri="http://wicketstuff-jee-web.org/functions/jsp" %>
JSF: <div xmlns="http://www.w3.org/1999/xhtml" xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html" xmlns:wicket="http://wicketstuff-jee-web.org/functions/jsf">

EL-Function:
${wicket:url('mypackage.MyPage')}
${wicket:urlWithQuery('mypackage.MyPage','param1=value1')}
```

## Forms (GET / POST)
POST, JSP fragment:

```html
<form action="<wicket:url page="mypackage.MyPage2"/>" method="POST">
	<input type="hidden" name="hiddenparam" value="testvalue">
	<input type="submit" value="Submit">
</form>
```

`mypackage.TestPage2`:

```java
public TestPage2(PageParameters parameters){
	String hiddenparam = RequestCycle.get().getRequest()
	.getPostParameters().getParameterValue("hiddenparam");
}
```

GET, JSP fragment:

```html
<form action="<wicket:url page="mypackage.MyPage2"/>" method="GET">
	<input type="hidden" name="hiddenparam" value="testvalue">
	<input type="submit" value="Submit">
</form>
```

`mypackage.TestPage2`:

```java
public TestPage2(PageParameters parameters){
	String hiddenparam = parameters.get("hiddenparam").toString();
}
```

## Ajax-Support

WebApplication:

```java
    @Override
    protected void init() {
	super.init();
		getPageSettings().addComponentResolver(new JEEWebResolver());
		JEEWebGlobalAjaxHandler.configure(this);
    }
```
WebPage (IMPORTANT: In constructor use setStatelessHint(false); !!!):

```java
    @Override
    public void onEvent(IEvent<?> event) {
		JEEWebGlobalAjaxEvent castedEvent =
		JEEWebGlobalAjaxEvent.getCastedEvent(event);
		if (castedEvent!= null) {
			AjaxRequestTarget ajaxRequestTarget = castedEvent.getAjaxRequestTarget();

			// Get-Request
			castedEvent.getPageParameters().get("param");

			// Post-Request
			castedEvent.getPostParameters().getParameterValue("param")
		}
    }
```
In JSP:

```html
<a href="#" onClick="${wicket:ajaxGetWithQuery('param=value')}">Update</a>
<a href="#" onClick="${wicket:ajaxGet()}">Update</a>
```

In JSP with JavaScript:

```html
<script type="text/javascript">
	function processCallBack(){
		var url = '${wicket:ajaxCallbackUrl()}';
		var urlWithPreRenderedArgs =
		'${wicket:ajaxCallbackUrlWithQuery("param=value")}';

		// Get-Request
		var url = Wicket.Ajax.applyGetParameters(url,{"param":"value"})
		Wicket.Ajax.wrapget(url);

		// Post-Request
		Wicket.Ajax.wrappost(url,{"param":"value"});
	}
	processCallBack();
</script>
```

Forms (GET / POST given as String to the EL function):

```html
<form onsubmit="${wicket:ajaxFormSubmit('POST')}"> .... </form>
```

## Links
* <https://cwiki.apache.org/confluence/display/WICKET/Including+JSP+files+in+HTML+templates>
* <http://apache-wicket.1842946.n4.nabble.com/Wicket-1-5-and-JSP-servlet-wrapping-td4407174.html>

The [`jee-web-examples`](https://github.com/wicketstuff/core/tree/wicket-11.x/jee-web-parent/jee-web-examples) module contains a runnable example application.

Source: [`jee-web-parent`](https://github.com/wicketstuff/core/tree/wicket-11.x/jee-web-parent)
