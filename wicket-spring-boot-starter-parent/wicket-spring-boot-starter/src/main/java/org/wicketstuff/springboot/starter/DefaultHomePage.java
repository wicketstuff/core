package org.wicketstuff.springboot.starter;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

/**
 * The default home page loaded by the Wicket Spring Boot Starter
 * when no custom WebApplication bean is provided by the application.
 */
public class DefaultHomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public DefaultHomePage() {
		add(new Label("version", getApplication().getFrameworkSettings().getVersion()));
	}
}
