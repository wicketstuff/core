package com.example.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class HomePage extends WebPage {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private GreetingService greetingService;

	public HomePage() {
		add(new Label("greeting", greetingService.greet()));
	}
}
