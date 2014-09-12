package org.wicketstuff.jeeweb.examples;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.jeeweb.JEEWebResolver;
import org.wicketstuff.jeeweb.ajax.JEEWebGlobalAjaxHandler;

public class TestApplication extends WebApplication {

    @Override
    public Class<? extends Page> getHomePage() {
	return TestPage.class;
    }

    @Override
    protected void init() {
	super.init();
	getPageSettings().addComponentResolver(
		new JEEWebResolver());
	JEEWebGlobalAjaxHandler.configure(this);
	getMarkupSettings().setStripWicketTags(true);
    }
}
