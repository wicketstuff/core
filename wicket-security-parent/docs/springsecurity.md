---
title: Spring Security
---

> This documentation was written for Wicket Security 1.x.

## Introduction

This how-to-guide shows you how to setup swarm to let Spring Security handle
the authentication for you.

It is advisable to have some basic knowledge of Spring Security as this guide
will only show you the basics and you will want to customize the here used
Spring Security configuration to your personal taste.

All the code and spring configuration is available in the Wicket Security
Examples

## Some helpful links

This guide is based on 2 other guides: 'wicket-spring' and 'Acegi and
Wicket-Auth-Roles'. So if you need some more information, in particular on
Spring configuration you might want to check them out.

Wicket-Spring
acegi-and-wicket-auth-roles
Acegi security documentation

## Requirements

I use the following libraries and versions but any newer version should work
too.

 * Wasp 0.1 (latest snapshot)
 * Swarm 0.1 (latest snapshot)
 * Wicket 1.3 (latest snapshot)
 * Wicket-Spring 1.3 (latest snapshot)
 * Acegi 1.0.5
 * EHCache 1.2.4

I recommend adding them to your maven pom to download them and any
dependencies they have automatically.

## The basic Application

Have your application extend `SwarmWebApplication` and implement the abstract
methods as described in the [Getting started with Swarm](gettingstarted.md).
If you plan to do more with Spring you can alternatively extend
`SpringWebApplication` and implement `WaspApplication` yourself.

## Spring configuration

Time to add some Spring. Note that i am using the "Application Object
Approach" from the Wicket-Spring tutorial.

Configure your `web.xml` as follows:

	<!DOCTYPE web-app PUBLIC "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN" "http://java.sun.com/dtd/web-app_2_3.dtd">
	<web-app>
	    <display-name>Wicket Security Examples</display-name>
	    <!-- Additional Spring config -->
	    <context-param>
	        <param-name>contextConfigLocation</param-name>
	        <param-value>/WEB-INF/applicationContext.xml</param-value>
	    </context-param>
	    <!-- Spring Wicket app-->
	    <filter>
	        <filter-name>acegi</filter-name>
	        <filter-class>
	            org.apache.wicket.protocol.http.WicketFilter
	        </filter-class>
	        <init-param>
	            <param-name>applicationFactoryClassName</param-name>
	            <param-value>
	                org.apache.wicket.spring.SpringWebApplicationFactory
	            </param-value>
	        </init-param>
	    </filter>
	    <!-- regular mapping for spring wicket app -->
	    <filter-mapping>
	        <filter-name>acegi</filter-name>
	        <url-pattern>/acegi/*</url-pattern>
	    </filter-mapping>
	    <!-- Listener for Spring -->
	    <listener>
	        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	    </listener>
	</web-app>

This tells Wicket to use the SpringWebApplicationFactory to create your webapp and tells spring to look in the applicationContext.xml for additional configuration. We'll add some more Acegi configuration later but first lets have a look at the applicationContext.xml:

	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans"
	       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.0.xsd">

		<!-- setup wicket application -->
		<bean id="wicketApplication" class="org.apache.wicket.security.examples.acegi.MyAcegiApplication">
			<property name="authenticationManager" ref="authenticationManager"/>
		</bean>
	</beans>

As you can see this were we specify which application the factory is supposed
to return. You'll also notice property specifying an authenticationManager.
This is actually the first bit of Acegi we are dealing with and does nothing
more then tell Spring that our application has a property named
"authenticationManager" which Spring will inject for us. Now i can already
here you think: "wait we don't have that property in our application". And you
are right so lets do that now.

	import org.acegisecurity.AuthenticationManager;

	public class MyAcegiApplication extends SwarmWebApplication {
	    // To be injected by Spring
	    private AuthenticationManager authenticationManager;

	    public MyAcegiApplication() {          .....
	    }

		public AuthenticationManager getAuthenticationManager() {
			return authenticationManager;
	    }
		public void setAuthenticationManager(final AuthenticationManager authenticationManager) {
			this.authenticationManager = authenticationManager;
	    }
		......

The `AuthenticationManager` handles all request for authentication and will be
used later in our `LoginContext`. The getters and setters are for Spring and
they also allow us to get to the manager from our `LoginContext`.

## Acegi configuration

Time to add the remainder of the configuration in web.xml. When you add the
filter mapping for Acegi (shown in a moment) it is important to place it
before the Wicket filter mapping. The complete web.xml looks like this:

	<!DOCTYPE web-app PUBLIC "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN" "http://java.sun.com/dtd/web-app_2_3.dtd">
	<web-app>
	    <display-name>Wicket Security Examples</display-name>
	    <!-- Additional Spring config -->
	    <context-param>
	        <param-name>contextConfigLocation</param-name>
	        <param-value>/WEB-INF/applicationContext.xml</param-value>
	    </context-param>
	    <!-- acegi filter-->
	    <filter>
	        <filter-name>Acegi HTTP Request Security Filter</filter-name>
	        <filter-class>org.acegisecurity.util.FilterToBeanProxy</filter-class>
	        <init-param>
	            <param-name>targetClass</param-name>
	            <param-value>org.acegisecurity.util.FilterChainProxy</param-value>
	        </init-param>
	    </filter>
	    <!-- Spring Wicket app-->
	    <filter>
	        <filter-name>acegi</filter-name>
	        <filter-class>
	            org.apache.wicket.protocol.http.WicketFilter
	        </filter-class>
	        <init-param>
	            <param-name>applicationFactoryClassName</param-name>
	            <param-value>
	                org.apache.wicket.spring.SpringWebApplicationFactory
	            </param-value>
	        </init-param>
	    </filter>
	    <!-- Acegi filter first, only for /acegi urls -->
	    <filter-mapping>
	        <filter-name>Acegi HTTP Request Security Filter</filter-name>
	        <url-pattern>/acegi/*</url-pattern>
	    </filter-mapping>
	    <!-- regular mapping for spring wicket app -->
	    <filter-mapping>
	        <filter-name>acegi</filter-name>
	        <url-pattern>/acegi/*</url-pattern>
	    </filter-mapping>
	    <!-- Listener for Spring -->
	    <listener>
	        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	    </listener>
	</web-app>

You can either use the same url pattern as your wicket app or /* to have all
urls go through the acegi filter.

The remainder of the Acegi configuration is placed inside applicationContext.xml. First some general configuration to set up caching etc. Don't ask me what it is for, i don't know i just copied it from the "Acegi and Wicket-Auth_roles" tutorial

	<!-- Maintains security context between requests (using the session). -->
	<bean id="httpSessionContextIntegrationFilter"
		class="org.acegisecurity.context.HttpSessionContextIntegrationFilter">
		<property name="forceEagerSessionCreation" value="true"/>
	</bean>

	<!-- Users cache for Acegi (Ehcache). -->
	<bean id="userCache" class="org.acegisecurity.providers.dao.cache.EhCacheBasedUserCache">
		<property name="cache">
		<bean class="org.springframework.cache.ehcache.EhCacheFactoryBean">
			<property name="cacheManager" ref="cacheManager"/>
			<property name="cacheName" value="your.application.name.USER_CACHE"/>
		</bean>
		</property>
	</bean>
	<bean id="cacheManager" class="org.springframework.cache.ehcache.EhCacheManagerFactoryBean"/>

Next the configuration of our "authenticationManger" specified earlier in our config file.

	<!-- Authentication manager, configured with one provider that retrieves authentication information
	        from test data. -->
	<bean id="authenticationManager" class="org.acegisecurity.providers.ProviderManager">
		<property name="providers">
			<list>
				<ref local="testAuthenticationProvider"/>
			</list>
		</property>
    </bean>

	<!-- Authentication provider for test authentication. -->
	<bean id="testAuthenticationProvider" class="org.acegisecurity.providers.TestingAuthenticationProvider">
	</bean>

As you can see our "authenticationManager" is actually is actually a
`ProviderManager` which means he will delegate authentication to a
`AuthenticationProvider`. In our case a `TestingAuthenticationProvider`. The
Wicket-Auth-Roles tutorial uses a `LdapAuthenticationProvider` so check there
if you want to see something a little more complex. The principal remains the
same however.

The complete `applicationContext.xml` now looks like this:

	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.0.xsd">
		<!-- setup wicket application -->
		<bean id="wicketApplication" class="org.apache.wicket.security.examples.acegi.MyAcegiApplication">
			<property name="authenticationManager" ref="authenticationManager"/>
		</bean>
		<!-- acegi config -->
		<!-- Proxy to a set of filters that enforce authentication and authorization. -->
		<bean id="filterChainProxy" class="org.acegisecurity.util.FilterChainProxy">
			<property name="filterInvocationDefinitionSource">
				<value>
	        CONVERT_URL_TO_LOWERCASE_BEFORE_COMPARISON
	        PATTERN_TYPE_APACHE_ANT
	        /**=httpSessionContextIntegrationFilter
	      </value>
			</property>
		</bean>
		<!-- Maintains security context between requests (using the session). -->
		<bean id="httpSessionContextIntegrationFilter" class="org.acegisecurity.context.HttpSessionContextIntegrationFilter">
			<property name="forceEagerSessionCreation" value="true"/>
		</bean>
		<!-- Users cache for Acegi (Ehcache). -->
		<bean id="userCache" class="org.acegisecurity.providers.dao.cache.EhCacheBasedUserCache">
			<property name="cache">
				<bean class="org.springframework.cache.ehcache.EhCacheFactoryBean">
					<property name="cacheManager" ref="cacheManager"/>
					<property name="cacheName" value="your.application.name.USER_CACHE"/>
				</bean>
			</property>
		</bean>
		<bean id="cacheManager" class="org.springframework.cache.ehcache.EhCacheManagerFactoryBean"/>
		<!-- Authentication manager, configured with one provider that retrieves authentication information
	        from test data. -->
		<bean id="authenticationManager" class="org.acegisecurity.providers.ProviderManager">
			<property name="providers">
				<list>
					<ref local="testAuthenticationProvider"/>
				</list>
			</property>
		</bean>
		<!-- Authentication provider for test authentication. -->
		<bean id="testAuthenticationProvider" class="org.acegisecurity.providers.TestingAuthenticationProvider">
	    </bean>
	</beans>

## Swarm integration

At some point in your application you will have received a username and
password (or some other credentials) from a user. Swarm uses a `LoginContext`
and usually you would just supply that with the username and password. But
Acegi requires `AuthenticationToken`s (which have to match with the
`AuthenticationProvider`). So instead of passing the username and password to
our `LoginContext` we wrap them inside a `TestingAuthenticationToken`.
Remember you can use any other provider/tokens you like.

	public boolean signIn(final String username, final String password) {
		// authentication in swarm is handled by contexts, which are
		// disposed after use.
		LoginContext context = new AcegiLoginContext(new TestingAuthenticationToken(
								username, password, getAuthorities(username, password)));
		try {
			((WaspSession)getSession()).login(context);
		}
		catch (LoginException e) {
			log.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	/**
	 * This example uses the TestingAuthenticationToken therefore we
	 * need to provide the authorities up front. Other
	 * AuthenticationTokens will get this from a database or wherever
	 * they are designed to get it from.
	 *
	 * @param username
	 * @param password
	 * @return
	 */
	private GrantedAuthority[] getAuthorities(String username, String password) {
		GrantedAuthority[] authorities = null;
		if (username != null && Objects.equal(username, password)) {
			authorities = new GrantedAuthority[1];
			if ("ceo".equals(username))
				authorities[0] = new GrantedAuthorityImpl("organisation.rights");
			else
				authorities[0] = new GrantedAuthorityImpl("department.rights");

			// the subject returned in AcegiLoginContext knows how to
			// convert these names to principals
		}
		return authorities;
	}

In Swarm authorization is handled by Principals and Permissions but Acegi uses
GrantedAuthorities. Normally these are assigned to the user by the
AuthenticationManager or the AuthenticationProvider. However we are using a
TestingAuthenticationProvider which like its name suggests is used for
testing. Therefor the provider does nothing and all granted authorities need
to be assigned up front. I am using a simple check based on username to decide
what kind of GrantedAuthority to assign to the token. The Strings
"organisation.rights" and "department.rights" correspond to Principals defined
in the policy file:

	grant principal org.apache.wicket.security.examples.authorization.MyPrincipal "organisation.rights"
	{
	    ....
	};
	grant principal org.apache.wicket.security.examples.authorization.MyPrincipal "department.rights"
	{
	    ....
	};

A specialized AcegiLoginContext takes a token and uses it to authenticate the
user with the AuthenticationManager in the application.

	/**
	 * A general purpose wrapper to authenticate a user with Swarm through Acegi. It
	 * does not support multi-login. Provided as is without warranty or
	 * responsibility for damage.
	 *
	 * @author marrink
	 */
	public final class AcegiLoginContext extends LoginContext
	{
	    private AbstractAuthenticationToken token;

	    /**
	     *
	     * Constructor for logoff purposes.
	     */
	    public AcegiLoginContext()
	    {

	    }

	    /**
	     * Constructs a new LoginContext with the provided Acegi
	     * AuthenticationToken.
	     *
	     * @param token
	     *            contains credentials like username and password
	     */
	    public AcegiLoginContext(AbstractAuthenticationToken token)
	    {
	        this.token = token;
	    }

	    /**
	     * @see org.apache.wicket.security.hive.authentication.LoginContext#login()
	     */
	    public Subject login() throws LoginException
	    {
	        if (token == null)
	            throw new LoginException("Insufficient information to login");
	        // Attempt authentication.
	        try
	        {
	            AuthenticationManager authenticationManager = ((MyAcegiApplication)Application.get())
	                    .getAuthenticationManager();
	            if (authenticationManager == null)
	                throw new LoginException(
	                        "AuthenticationManager is not available, check if your spring config contains a property for the authenticationManager in your wicketApplication bean.");
	            Authentication authResult = authenticationManager.authenticate(token);
	            setAuthentication(authResult);
	        }
	        catch (AcegiSecurityException e)
	        {
	            setAuthentication(null);
	            throw new LoginException(e);

	        }

	        catch (RuntimeException e)
	        {
	            setAuthentication(null);
	            throw new LoginException(e);
	        }
	        // cleanup
	        token = null;
	        // return result
	        return new AcegiSubject();
	    }

	    /**
	     * Sets the acegi authentication.
	     *
	     * @param authentication
	     *            the authentication or null to clear
	     */
	    private void setAuthentication(Authentication authentication)
	    {
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	    }

	    /**
	     * Notify Acegi.
	     *
	     * @see org.apache.wicket.security.hive.authentication.LoginContext#notifyLogoff(org.apache.wicket.security.hive.authentication.Subject)
	     */
	    public void notifyLogoff(Subject subject)
	    {
	        setAuthentication(null);
	    }
	}

The result of the authentication is placed inside a SecurityContextHolder which is stored somewhere in the session. The Subject gets the GrantedAuthorities from that context and translates them into Principals.

	/**
	 * Subject that gets is principals from the authenticated user in the
	 * {@link SecurityContextHolder}. This class is converts all authorities to
	 * {@link MyPrincipal}s but could serve as a template for your implementation.
	 *
	 * @author marrink
	 */
	public class AcegiSubject extends DefaultSubject
	{
	    private static final long serialVersionUID = 1L;

	    /**
	     * Constructor.
	     */
	    public AcegiSubject()
	    {
	        GrantedAuthority[] authorities = SecurityContextHolder.getContext().getAuthentication()
	                .getAuthorities();
	        if (authorities != null)
	        {
	            Principal principal;
	            for (int i = 0; i < authorities.length; i++)
	            {
	                principal = convert(authorities[i]);
	                if (principal != null)
	                    addPrincipal(principal);
	            }
	        }
	    }

	    /**
	     * Converts a {@link GrantedAuthority} to a {@link Principal}
	     *
	     * @param authority
	     * @return principal or null if the authority could not be converted
	     */
	    protected Principal convert(GrantedAuthority authority)
	    {
	        return new MyPrincipal(authority.getAuthority());
	    }
	}

## Running your webapp

Before you try and run your webapp there is a small matter of dependencies.

It appears that Maven also places a lot of Spring 1.x depencies on your
classpath (through Acegi). So make sure your webapp classpath only contains
the following dependencies:

 * asm-1.5.3.jar
 * cglib-nodep-2.1_3.jar
 * commons-codec-1.3.jar
 * commons-collections-3.1.jar
 * commons-lang-2.1.jar
 * commons-logging-1.1.jar
 * log4j-1.2.12.jar (or any other logging framework)
 * ehcache-1.2.4.jar
 * acegi-security-1.0.5.jar
 * wicket-ioc-1.3.0-SNAPSHOT.jar
 * swarm-0.1-SNAPSHOT.jar
 * wasp-0.1-SNAPSHOT.jar
 * wicket-spring-1.3.0-SNAPSHOT.jar
 * wicket-1.3.0-SNAPSHOT.jar
 * slf4j-api-1.4.2.jar
 * slf4j-log4j12-1.4.2.jar (or any other implementation)
 * spring-2.0.jar

## Questions?

Please send questions to the [Wicket mailing list](mailto:users@wicket.apache.org)
