package org.wicketstuff.springboot.starter.example;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Example Home Page.
 */
public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	@SpringBean
	private SpringService springService;

	public HomePage() {
		add(new Label("message", "Welcome to Wicket + Spring Boot!"));
		add(new Label("springMessage", springService.getMessage()));
	}
}
