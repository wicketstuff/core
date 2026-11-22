---
title: Servlet Container Auth
category: security
description: Use your servlet container's authentication and roles in Wicket.
artifactId: wicketstuff-servlet3-auth
tags: jaas login servlet3
---

Wicket-Servlet3-Container-Auth package is designed to simplify the integration of wicket-auth-roles with the servlet 3 security container. It is designed to support all Realms (Basic, Digest,Ldap,Spenago,etc) configured from web.xml, but the document will initially focus on the Form-Based authentication.

## Maven

```xml
<dependency>
    <groupId>org.wicketstuff</groupId>
    <artifactId>wicketstuff-servlet3-auth</artifactId>
</dependency>
```

Import `org.wicketstuff:wicketstuff-bom` in your `dependencyManagement` section to manage the version.

## Overview
Typical usage is to extend the ServletContainerAuthenticatedWebApplication. By extending this class it
configures two important features.

* Enables a compile-time annotation processor to generate paths
for all pages annotated with `@AuthorizeInstantiation`. WebPages can also be annotated with `@MountPath("custom/path/whatever.html")` to override the default name generation.
* Enforces that all pages with @AuthorizeInstantiation are redirected to the mountedPath so that the security-container can handle authentication based on the specified realm.
```java
public class ContainerManagedSecurityApp extends ServletContainerAuthenticatedWebApplication
{

	@Override
	public Class<? extends Page> getHomePage()
	{
		return HomePage.class;
	}

	@Override
	protected Class<? extends ServletContainerAuthenticatedWebSession> getContainerManagedWebSessionClass()
	{
		return ServletContainerAuthenticatedWebSession.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass()
	{
		return SignInPage.class;
	}

}
```

## Annotations
The module supports two custom annotations and the compile-time code generator uses wicket-auth-roles @AuthorizeInstantiation annotation to generate a List of IRequestMapper objects that are then mounted at Application initialization.

### @SecureAutoMount

```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Inherited
public @interface SecureAutoMount
{
	/**
	 * Allow the configuration of the root path (not including contextPath) for auto generated paths of classes
	 * that are not an authorized page.
	 *
	 * @return Configured root for pages not requiring security
	 */
	String defaultRoot() default "";

	/**
	 * Allow the configuration of mime type for auto generated paths of classes
	 * that are not an authorized page.
	 *
	 * @return default mime type for auto generated paths. Defaults to "html".
	 */
	String defaultMimeExtension() default "html";

	/**
	 * Allow the configuration of the root path (not including contextPath) for auto generated paths of classes
	 * that are annotated with @AuthorizeInstantiation. This includes any class that resides in a package
	 * that is also annotated with @AuthorizeInstantiation.
	 *
	 * @return Configured root for pages requiring security. Defaults to "secure".
	 */
	String secureRoot() default "secure";

	/**
	 * Allow the configuration of mime type for auto generated paths of classes
	 * that are annotated with @AuthorizeInstantiation. This includes any class that resides in a package
	 * that is also annotated with @AuthorizeInstantiation.
	 *
	 * @return default mime type for auto generated paths. Defaults to "html".
	 */
	String secureMimeExtension() default "html";

	/**
	 * Allow explicit declaration of packages that should be scanned to generate the code for
	 * auto mounts.  If not set then the scanned packages are set to the package of the application
	 * annotated with @SecureMount as the root package and all packages that are a branch of the root.
	 * If packages are explicitly set then .* to end of packages allows the branches to be scanned. If a package is
	 * set without the .* then only that package is scanned.
	 *
	 * @return String array of packages that are scanned for auto mounting.
	 */
	String[] packagesToScan() default {};

}
```

The ServletContainerAuthenticatedWebApplication is annotated with @SecureAutoMount using all the defaults.
When an application inherits from ServletContainerAuthenticatedWebApplication, it also inherits the annotation.  If the application would like to override the defaults, then Simply annotate the Application with @SecureAutoMount and set the values as required.  Example

```java
@SecureAutoMount(defaultRoot="public", defaultMimeExtension="", secureRoot="private",secureMimeExtension="shtml")
```
The included sample does not override the default, but above is to see the ease of changing them.

One important note is packagesToScan option.
If not set then the compile-time annotation processor will only look for annotations to process starting from the Application root package as the trunk of a tree and traverse the nodes of that tree. If you would like to specify a custom list then append a .* to the end of the package to search the package and all of the nodes. Do not use the .* if you only want to include that package.  When setting the packagesToScan it no longer uses the Application root as a path unless it is also explicitly set.

### @MountPath

The @MountPath is included from another Wicket Stuff project, Wicket Mount (`wicket-mount-parent`). Do not confuse this @MountPath with the runtime version of automounting found in the WicketStuff project Wicket-Annotation. Those may merge in the future, but it was decided to not merge with that project so as not to conflict with the existing userbase.

```java
package org.wicketstuff.wicket.mount.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jsarman
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PACKAGE, ElementType.TYPE})
@Documented
@Inherited
public @interface MountPath
{

	String value() default "/*";

}

```

Here the MountPath supports just a value option. The value is the actual path.
If no value is set then the /* is replaced by the compiler with the following
for four different scenarios:

#### Scenario 1
@MountPath is set on a class that does not have @AuthorizeInstantiation, this means it doesn't extend a class that has it or exist in a package the @AuthorizeInstantiation is set on a package-info.class.

If value is left the default "/*"  then the annotation processor will replace the value with

defaultRoot + getClass().getSimpleName() + defaultMimeExtension (includes / and . if needed)

If the value is set and ends with /* then the * is replaced with getSimpleName + defaultMimeExtension

If the /* is not part of the value then that value is used.

#### Scenario 2
@MountPath set on package level.

If value = /* (default) then Scenario 1 applies for all Pages in the Package.

Otherwise the value is appended with /* if not present then Scenario 1 applies for all Pages in the Package.
Note: the if a @MountPath exists at the class level and the package level the class level setting is used.

#### Scenario 3
Only an @AuthorizeInstantiation exists on a class.  No MountPaths on the class or at package level.

In this scenario the Path is set to

secureRoot + simpleName + secureMimeExtension of course adding the / and the . when required.

#### Scenario 4
@AuthorizeInstantiation exists on package-info.
This applies Scenario 3 on all Pages in the package.

#### Order of precedence for creating paths
* @MountPath at class level
* @MountPath at package level
* @AuthorizeInstantiation at class and package level

Since @MountPath has higher precedence than @AuthorizeInstantiation, if used together it is the developers responsibility to ensure the path set is part of the secure domains assigned in web.xml.
**It is always the developers responsibility** to verify the secureRoot matches a security-constraint in web.xml, if not when a page annotated with @AuthorizeInstantiation is instantiated you will receive the unauthorized page and not the login page.  This is the default behavior, even though wicket could just serve up the login page, that would then give administrators an edge when arguing about a breach.

Here is a sample of the auto-generated mount list Java file:
**Note this file is generated during a clean build, when making changes to the annotation please clean build to save yourself the headache of why it didn't rebuild the auto-generated file**

```java
package org.wicketstuff.servlet3.secure.example;

import org.wicketstuff.wicket.mount.core.*;
import org.apache.wicket.request.IRequestMapper;
import org.apache.wicket.core.request.mapper.MountedMapper;
import java.util.*;

public class ContainerManagedSecurityAppMountInfo implements MountInfo
{
	@Override
	public List<IRequestMapper> getMountList() {
		List<IRequestMapper> ret = new ArrayList<IRequestMapper>();
		ret.add(new MountedMapper("index.html", org.wicketstuff.servlet3.secure.example.ui.HomePage.class));
		ret.add(new MountedMapper("secure/Page3.html", org.wicketstuff.servlet3.secure.example.ui.pkgExample.Page3.class));
		ret.add(new MountedMapper("secure/admin/admin.html", org.wicketstuff.servlet3.secure.example.ui.admin.AdminPage.class));
		ret.add(new MountedMapper("secure/Page2.html", org.wicketstuff.servlet3.secure.example.ui.user.Page2.class));
		return ret;
	}
}
```
The Java file is packaged in the same package of the Application.  The name is the ApplicationName concatenated with MountInfo. ContainerManagedSecurityAppMountInfo from the included example application ContainerManagedSecurityApp.

Here are the Page Snippets used to generate the MountInfo:

```java
@MountPath("index.html")
public class HomePage extends BasePage

@MountPath("secure/admin/admin.html")
@AuthorizeInstantiation({"tomcat", "admin"})
public class AdminPage extends BasePage

/** This is in package-info.java **/
@AuthorizeInstantiation({"tomcat", "role1", "admin"})
package org.wicketstuff.servlet3.secure.example.ui.pkgExample;

/** This a class in the same package */
package org.wicketstuff.servlet3.secure.example.ui.pkgExample;
public class Page3 extends BasePage
{

@AuthorizeInstantiation({"tomcat", "role1", "admin"})
public class Page2 extends BasePage
```

### How the mountPaths are set

 The ServletContainerAuthenticatedWebApplication class which itself is extended version of wicket-auth-roles' AuthenticatedWebApplication, overrides the Application init file:
```java
@Override
	protected void init()
	{
		super.init();

		// Add overrides for bookmarkable and nonbookmarkable page creations to allow servlet container
		// authorization mechanism to handle redirect to login page.
		final ContainerSecurityInterceptorListener listener = new ContainerSecurityInterceptorListener();
		getSecuritySettings().setUnauthorizedComponentInstantiationListener(listener);
		getRequestCycleListeners().add(listener);

		autoMountPages();
	}
```
The ContainerSecurityInterceptorListener is designed to force redirect the pages to the mounted url for pages that are bookmarkable and non-bookmarkable.  This is covered later.

Notice autoMountPages().  This method first mounts all the pages in the generated MountInfo class if that class exists, then checks to see if the signInPage class exists and is not annotated with a custom mount.
If a custom mount is not set using the @MountPath custom annotation, then the autoMountPages will generate a mount point and print the created mountpath as an Info listing of the logger. Mounting the signInPage is important for all form-based Realms, so that this mount point can be set in the configuration.

## The Wicket Filter

###  Servlet 3 using annotations

```java
@WebFilter(value = "/cms/*", dispatcherTypes = {REQUEST, FORWARD},
		initParams = {
				@WebInitParam(name = "applicationClassName",
						value = "org.wicketstuff.servlet3.secure.example.ContainerManagedSecurityApp"),
				@WebInitParam(name = "filterMappingUrlPattern", value = "/cms/*"),
				@WebInitParam(name = "configuration", value = "deployment")
		}
)
public class ContainerManagedSecurityWicketFilter extends WicketFilter
{

}

```
Important note: dispatcherTypes is set to REQUEST and FORWARD
```java
import static jakarta.servlet.DispatcherType.FORWARD;
import static jakarta.servlet.DispatcherType.REQUEST;
```
The FORWARD dispatcher will allow the SignInPage to be a Wicket page.
```xml
 <form-login-config>
            <form-login-page>/cms/login.html</form-login-page>
            <form-error-page>/cms/index.html</form-error-page>
 </form-login-config>
```
In the web.xml snippet the login page is set to /cms/login.html.  The automounter will set the getSignInPageClass to this path if a @MountPath is not present.

If the FORWARD dispatcher is not present then when the page is needed a 404 code will be given because the filter is not configured to receive a FORWARD. This has caused confusion in the past when developers have tried to use a login page from wicket.  The issue has always been that the Forward was not added as a dispatcher.

###  Wicket filter XML

```xml
        <filter>
            <filter-name>ContainerManagedSecurityApp</filter-name>
            <filter-class>org.apache.wicket.protocol.http.WicketFilter</filter-class>
            <init-param>
                <param-name>applicationClassName</param-name>
                <param-value>org.wicketstuff.servlet3.secure.example.ContainerManagedSecurityApp</param-value>
            </init-param>
        </filter>
        <filter-mapping>
            <filter-name>ContainerManagedSecurityApp</filter-name>
            <url-pattern>/cms/*</url-pattern>
           <dispatcher>REQUEST</dispatcher>
           <dispatcher>FORWARD</dispatcher>
        </filter-mapping>
```
Same deal here do not forget to set the dispatchers if you would like to use a SigninPage from Wicket.
The example simply uses the SignInPage class found in wicket-auth-roles.

### System Administration stuff (Developers place your admin hat on here )

Typically these parts would be configured by a Servlet Administrator (refrain from laughing devs) in the global configurations depending on the servlet container.  In Tomcat the sysadmin can place these values in the global web.xml for example so that these values can be changed without needing to recompile the war file or exposing sensitive settings.  However the dev can also just add them in the war files web.xml.

Here is the examples configuration

```xml
<security-constraint>
        <display-name>ExampleConstraint</display-name>
        <web-resource-collection>
            <web-resource-name>secure-example</web-resource-name>
            <description/>
            <!-- ex context is /cms/* and secureRoot is set to secure, so makesure /cms/secure/* is a url-pattern like so -->
            <url-pattern>/cms/secure/*</url-pattern>
        </web-resource-collection>
        <auth-constraint>
            <description>Example Roles</description>
            <role-name>tomcat</role-name> <!-- See tomcat-users.xml and uncomment for testing -->
            <role-name>role1</role-name>
            <role-name>admin</role-name>
        </auth-constraint>
    </security-constraint>
    <login-config>
        <auth-method>FORM</auth-method>
        <!-- the example realm, please don't use this in your production environment -->
        <realm-name>UserDatabaseRealm</realm-name>
        <form-login-config>
            <!-- SignInPage.class from wicket-auth-roles maps to this in the example -->
            <form-login-page>/cms/login.html</form-login-page>
            <!-- dummy page as wicket handles the error -->
            <form-error-page>/cms/index.html</form-error-page>
        </form-login-config>
    </login-config>
    <security-role> <!-- Specify all the roles available -->
        <role-name>tomcat</role-name>
    </security-role>
    <security-role>
        <role-name>role1</role-name>
    </security-role>
    <security-role>
        <role-name>admin</role-name>
    </security-role>
```

## Advanced topics

### Configure for Tomcat 7
This will configure the example for the Tomcat 7.  Since the original example was built and tested using tomcat 7 no changes to the war are necessary.  To test in Tomcat 7 the developer needs to edit the tomcat-users.xml and uncomment the following

${catalina_home}/conf/tomcat-users.xml
```xml
 <role rolename="tomcat"/>
  <role rolename="role1"/>
  <user username="tomcat" password="tomcat" roles="tomcat"/>
  <user username="both" password="tomcat" roles="tomcat,role1"/>
  <user username="role1" password="tomcat" roles="role1"/>
```

also the admin user will usually be uncommented if you use an IDE for doing deployment during development.  In my case it is set as
```xml
<user password="tomcat" roles="manager-script,admin" username="admin"/>
```
If it is not uncommented you can uncomment or create as so.

After these are uncommented then you only need to deploy the war and test.

### Configure for Jetty

Jetty testing is simple.  All files to test with jetty have been added to the example.
All users have test password set to tomcat.

Users:
* admin with role admin
* tomcat with role tomcat
* role1 with role role1

To test with jetty simply run mvn jetty:run on the wicket-servlet3-example pom.

### Configure for Glassfish 4
This will configure the example for the File Realm in Glassfish 4. This assumes some knowledge with glassfish, I personally recommend netbeans 7.${currentRelease} 7.4 as of this blog, that has Glassfish4 installed when installing the IDE. This topic will not depend on it but it is definitely a simple way to use glassfish through an IDE.

1.  Start glassfish and goto the Administrator Console.  In Netbeans goto service tab -> Servers -> glassfish. Right Click and select Start.  Once Started Right click Select View Domain Admin Console.  Otherwise get there however is normal to your env.

2. In the left column of the domain console goto Configurations -> Security -> Realms and select the file realm.

3. Click manage users and create 3 users.  Pretend these were setup by administrators :) They decided on
the ROLE names that didn't map to the roles already in the war.
   * admin with roles USER,ADMIN
   * user1 with role USER
   * user2 with role USER

4. Verify the default realm is set to file

 Click the Security link in left column the set default Realm to file.
When the default realm is set Glassfish will use this realm if the realm specified in web.xml does not match
a realm in the glassfish pool of realms.  Since web.xml is set to UserDatabaseRealm then glassfish will revert to using the default realm "file".

Now create the file glassfish-web.xml so that the ROLES created in the file realm can be mapped to the roles
used in the example code.  In the file the following mappings are set
 ADMIN maps to admin
 ADMIN maps to tomcat
 USER maps to role1

This allows for an app with predefined roles to be used in glassfish without needing to update the roles set in the @AuthorizeInstantiation and described in web.xml. Conversely it also for users with roles that may have been created in the system prior to app development to map the older roles to the the new, ie decouples backend roles like LDAP groups or active directory from developers definition for the app roles.

WEB-INF/glassfish-web.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE glassfish-web-app PUBLIC "-//GlassFish.org//DTD GlassFish Application Server 3.1 Servlet 3.0//EN" "http://glassfish.org/dtds/glassfish-web-app_3_0-1.dtd">
<glassfish-web-app error-url="">
  <security-role-mapping>
    <role-name>admin</role-name>
    <group-name>ADMIN</group-name>
  </security-role-mapping>
  <security-role-mapping>
    <role-name>role1</role-name>
    <group-name>USER</group-name>
  </security-role-mapping>
  <security-role-mapping>
    <role-name>tomcat</role-name>
    <group-name>ADMIN</group-name>
  </security-role-mapping>
</glassfish-web-app>
```

That is it recompile so that the glassfish-web.xml is added in the war and deploy to glassfish.
So only change needed was to create a custom mapping class to map glassfish roles to the war file roles.

### Configure using Wildfly 8
This will configure the example for the ApplicationRealm in Wildfly 8. This assumes some knowledge with wildfly 8, but I was able to download wildfly 8 and have it working in 15 minutes without much hassle so this should be easy even if you have never used wildfly 8.  I wanted to test this hopefully so that Emond Papegaaij gave it a look so my code could be further scrutinized and optimized if he was interested.  He originally encouraged me to give Wildfly a try so I did that.

1. In the Wildfly bin directory execute add-user.sh or add-user.bat depending on OS. Add three users to the ApplicationRealm.
  admin with roles admin
  tomcat with role tomcat
  role1 with role role1.
The add-user tool will guide you through this process.

2. update web.xml and change the Realm
   ```xml
   <realm-name>ApplicationRealm</realm-name>
   ```

3. Recompile the war so that the war is updated with the web.xml changes.

4. In the wildfly bin directory execute standalone.sh or standalone.bat to start server.

5. In the wildfly/standalone/deployments directory place the war file. The output of the console in which standalone server was started will state that the war is deployed and display the mounted context path.
`/wicketstuff-servlet3-examples-<version>`

Go to `http://localhost:8080/wicketstuff-servlet3-examples-<version>/cms/index.html` in my case change the context path accordingly to match what wildfly set for your deployment.

## Enjoyed what you learned but would like to use wicket-auth-roles without this dependency
That is fine, and here is what you need to know to implement that.
Most import part.
  set the dispatchers to REQUEST and FORWARD.   REQUEST is set when no dispatchers are specified and FORWARD is required if the
```xml
<form-login-config>
```
option is set in web.xml such that you would like wicket to serve the login page.
If you set the FORWARD dispatcher you also need the REQUEST dispatcher, it is only the default when the dispatcher is not not explicit.  This allows the security mechanism to serve the login page using a wicket generated page.
If you would like to use a REALM but prevent the servlet container from intercepting any page then set
```xml
<url-pattern>/never/will/occur/*</url-pattern>
```
 to a pattern that will never be matched.
As you can see you are now hacking the web.xml to only use the Realm setting so that HttpServletRequest#login(String username, String password) authenticates without the specified realm of the servlet container intercepting requests to a secure domain. In this case wicket-auth-roles becomes solely responsible for authorization.  Another option is to specify a valid url-pattern and set the form-login-page to a valid wicket signinpage.  In this instance if you call setResponsePage(SomePage.class) then the servlet container will intercept the page assuming it is mapped to a matching pattern of the url-pattern and serve the login page.  However the continueToOriginalDestination() method will not work. If a setResponsePage(new SomePage()) is coded then wicket-auth-roles will catch the onUnauthorizedInstantiation and serve the login page. In this instance the continueToOriginalDestination() will work normally.  To fix the case for bookmarkable responses ie setResponsePage(SomePage.class) just review the ServletContainerAuthenticatedWebApplication code to see how it is handled.

The [`wicket-servlet3-examples`](https://github.com/wicketstuff/core/tree/wicket-11.x/wicket-servlet3-parent/wicket-servlet3-examples) module contains a runnable example application.

Source: [`wicket-servlet3-parent`](https://github.com/wicketstuff/core/tree/wicket-11.x/wicket-servlet3-parent)
