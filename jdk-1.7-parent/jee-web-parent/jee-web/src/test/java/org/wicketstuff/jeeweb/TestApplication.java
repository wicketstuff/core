package org.wicketstuff.jeeweb;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.jeeweb.JEEWebResolver;

public class TestApplication extends WebApplication{

    @Override
    public Class<? extends Page> getHomePage() {
	return TestServletAndJSPPage.class;
    }
    
    @Override
    protected void init() {
	getPageSettings().addComponentResolver(new JEEWebResolver());
	getMarkupSettings().setStripWicketTags(true);
    }

}
