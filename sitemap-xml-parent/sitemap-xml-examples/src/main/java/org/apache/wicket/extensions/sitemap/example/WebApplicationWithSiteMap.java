package org.apache.wicket.extensions.sitemap.example;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class WebApplicationWithSiteMap extends WebApplication {
	public Class<? extends Page> getHomePage() {
		return ExamplePage.class;
	}

	@Override
	protected void init() {
		super.init();
		mountResource("sitemap.xml", new ExampleSiteMap());
	}

}