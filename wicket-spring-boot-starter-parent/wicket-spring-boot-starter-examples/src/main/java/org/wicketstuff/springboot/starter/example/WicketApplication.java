package org.wicketstuff.springboot.starter.example;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.stereotype.Component;

/**
 * The Wicket WebApplication registered as a Spring Component.
 */
@Component
public class WicketApplication extends WebApplication {

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

	@Override
	public void init() {
		super.init();
	}
}
