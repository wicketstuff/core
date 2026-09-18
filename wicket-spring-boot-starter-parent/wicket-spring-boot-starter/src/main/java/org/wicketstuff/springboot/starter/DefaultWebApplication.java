package org.wicketstuff.springboot.starter;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.resource.PackageResourceReference;

/**
 * The default {@link WebApplication} used by the Wicket Spring Boot Starter
 * when the user does not provide their own custom implementation bean.
 */
public class DefaultWebApplication extends WebApplication {

	@Override
	public Class<? extends Page> getHomePage() {
		return DefaultHomePage.class;
	}

	@Override
	protected void init() {
		super.init();

		// Mount resources to serve style.css and logo.png from the application root path
		mountResource("style.css", new PackageResourceReference(DefaultHomePage.class, "style.css"));
		mountResource("logo.png", new PackageResourceReference(DefaultHomePage.class, "logo.png"));
	}
}
