package org.wicketstuff.springboot.starter;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * The default {@link WebApplication} used by the Wicket Spring Boot Starter
 * when the user does not provide their own custom implementation bean.
 */
public class DefaultWebApplication extends WebApplication {

	@Override
	public Class<? extends Page> getHomePage() {
		return DefaultHomePage.class;
	}
}
