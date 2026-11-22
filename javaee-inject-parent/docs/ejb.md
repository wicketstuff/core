---
title: "@EJB annotation"
---

The samples on this page were written for Wicket 1.3/1.4 and Java EE 5. The injector registration is current; for the rest see the `javaee-inject-examples` module for working code.

1. Create your EJB, i.e.

```java
@Local
public interface ContactService {
  //.....
}

@Stateless
public class ContactServiceImplementation implements ContactService {
  //....
}
```

2. Package your EJB in a JAR file.
3. Declare your EJB in the web.xml descriptor of the webapplication, i.e.
```xml
    <ejb-local-ref>
    	<ejb-ref-name>ejb/contacts</ejb-ref-name>
    	<ejb-ref-type>Session</ejb-ref-type>
    	<local>wicket.javaee.service.ContactService</local>
    </ejb-local-ref>
```
4. Create your wicket page using the @EJB annotation to reference the developed EJB, i.e.

```java
public class ListContacts extends WebPage {

  @EJB(name="ejb/contacts") private ContactService contactService;

  public ListContacts() {
    List<Contact> contacts = contactService.getContacts();
    //....
```
5. Add the line
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
6. Package your web application in a WAR file.
7. Package the EJB module and the webapplication in an EAR file.
## Stateful EJB's

By default you're using always the same EJB instance (it's cached), but if you want to cache your EJB for Wicket Sessions, then use the following code in your Wicket component:
```java
  @EJB(name="ejb/contacts", description="stateful") private ContactService contactService;
```
So whenever you want to use stateful beans in your component, you need to put the stateful description in the @EJB, so the injector could separate them from stateless beans.
Note: for no-interface view stateful session beans you don't need to use the description attribute.
## Support for Global JNDI names

You can use the Java EE 6 based Global JNDI names, such as:
```text
java:global[/<app-name>]/<module-name>/<bean-name>[!<fully-qualified-interface-name>]
java:app/<module-name>/<bean-name>[!<fully-qualified-interface-name>]
java:module/<bean-name>[!<fully-qualified-interface-name>]
```
The java:global namespace can be used globally (for example accessing other applications remote beans), the java:app namespace is for application-scope JNDI access and the java:module namespace is for modules, which contains the lookupable element.

The app-name can be configured in the EAR files application.xml (<application-name> tag), and the module name can be configured with the <module-name> tags in both web.xml and ejb-jar.xml. These elements were not available in previous XSD's, so you need to check, whether your XML uses the latest schema.
If you want to use Global JNDI names, then in your WicketApplication init method use one of the following codes:
```java
getComponentInstantiationListeners().add(new JavaEEComponentInjector(this,
        new GlobalJndiNamingStrategy(APP_NAME, EJB_MODULE_NAME)));
getComponentInstantiationListeners().add(new JavaEEComponentInjector(this,
        new AppJndiNamingStrategy(EJB_MODULE_NAME)));
getComponentInstantiationListeners().add(new JavaEEComponentInjector(this,
        new ModuleJndiNamingStrategy()));
```
