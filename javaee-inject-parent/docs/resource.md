---
title: "@Resource annotation"
---

The samples on this page were written for Wicket 1.3/1.4 and Java EE 5. The injector registration is current; for the rest see the `javaee-inject-examples` module for working code.

1. Add a resource reference in your web.xml descriptor, i.e.
```xml
	<env-entry>
	    <env-entry-name>welcomeMessage</env-entry-name>
	    <env-entry-type>java.lang.String</env-entry-type>
	    <env-entry-value>@Welcome</env-entry-value>
	</env-entry>
```
2. Use the @Resource annotation in your wicket page, always specifying the name of the referenced resource, i.e.
```java
public class ListContacts extends WebPage {

  @Resource(name="welcomeMessage") private String welcome;

  public ListContacts() {
    new Label(this, "welcomeMessage", welcome);
    //.......
```
3. Add the line
```java
getComponentInstantiationListeners().add(new JavaEEComponentInjector(this));
```
inside the init() method of your Wicket WebApplication, like in the example:
```java
public class WicketJavaEEApplication extends WebApplication {

   protected void init() {
      super.init();
      getComponentInstantiationListeners().add(new JavaEEComponentInjector(this));
   }
}
```
