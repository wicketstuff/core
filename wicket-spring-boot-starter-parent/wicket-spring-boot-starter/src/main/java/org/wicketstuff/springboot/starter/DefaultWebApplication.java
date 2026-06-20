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

	@Override
	protected void init() {
		super.init();

		// Mount resources to serve style.css and logo.png from the application root path
		mountResource("style.css", new org.apache.wicket.request.resource.PackageResourceReference(DefaultHomePage.class, "style.css"));
		mountResource("logo.png", new org.apache.wicket.request.resource.PackageResourceReference(DefaultHomePage.class, "logo.png"));

		// Ensure the package resource guard allows requests for these resources
		org.apache.wicket.markup.html.IPackageResourceGuard guard = getResourceSettings().getPackageResourceGuard();
		if (guard instanceof org.apache.wicket.markup.html.SecurePackageResourceGuard) {
			org.apache.wicket.markup.html.SecurePackageResourceGuard secureGuard = (org.apache.wicket.markup.html.SecurePackageResourceGuard) guard;
			secureGuard.addPattern("+*.css");
			secureGuard.addPattern("+*.png");
		}
	}
}
