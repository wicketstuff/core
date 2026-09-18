package org.wicketstuff.springboot.starter;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

import java.io.Serial;

/**
 * The default home page loaded by the Wicket Spring Boot Starter
 * when the application provides no custom WebApplication bean.
 */
public class DefaultHomePage extends WebPage {
	@Serial
    private static final long serialVersionUID = 1L;

	public DefaultHomePage() {
		add(new Label("version", getApplication().getFrameworkSettings().getVersion()));
	}
}
