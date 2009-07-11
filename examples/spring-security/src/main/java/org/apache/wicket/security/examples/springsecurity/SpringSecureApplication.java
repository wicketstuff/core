package org.apache.wicket.security.examples.springsecurity;

import org.springframework.security.AuthenticationManager;
import org.apache.wicket.security.WaspApplication;

/**
 * Secure Application that uses spring-security to authenticate users.
 *
 * @author marrink
 * @author Olger Warnier
 */
public interface SpringSecureApplication extends WaspApplication
{
	/**
	 * The authentication manager from Acegi, usually this is injected to your
	 * application by Spring.
	 *
	 * @return manager
	 */
	public AuthenticationManager getAuthenticationManager();
}
