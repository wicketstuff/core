package org.wicketstuff.springboot.starter.example;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Mirrors the custom {@code WebApplication} from the README's Quickstart section: a
 * hand-written subclass that the starter's {@code @ConditionalOnMissingBean} default backs
 * off for, once it is registered as a Spring bean.
 */
class QuickstartWebApplication extends WebApplication {

	@Override
	public Class<? extends Page> getHomePage() {
		return QuickstartHomePage.class;
	}
}
