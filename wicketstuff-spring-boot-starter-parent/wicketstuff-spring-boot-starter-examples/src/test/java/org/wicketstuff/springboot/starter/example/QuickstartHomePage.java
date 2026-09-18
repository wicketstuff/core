package org.wicketstuff.springboot.starter.example;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Mirrors the custom {@code HomePage} from the README's Quickstart section: a Wicket page
 * that has a Spring-managed service injected via {@code @SpringBean}.
 *
 * @author Daniel Bartl
 */
public class QuickstartHomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	@SpringBean
	private GreetingService greetingService;

	public QuickstartHomePage() {
		add(new Label("message", greetingService.greet()));
	}
}
