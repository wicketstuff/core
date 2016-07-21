package org.apache.wicket.security.examples.springsecurity;

import org.apache.wicket.security.WaspApplication;
import org.springframework.security.authentication.AuthenticationManager;

/**
 * Secure Application that uses spring-security to authenticate users.
 * 
 * @author marrink
 * @author Olger Warnier
 */
public interface SpringSecureApplication extends WaspApplication
{
	/**
	 * The authentication manager from Acegi, usually this is injected to your application
	 * by Spring.
	 * 
	 * @return manager
	 */
	public AuthenticationManager getAuthenticationManager();
}
