package org.wicketstuff.minis.resolver;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class TestApplication extends WebApplication{

    @Override
    public Class<? extends Page> getHomePage() {
	return TestServletAndJSPPage.class;
    }
    
    @Override
    protected void init() {
	getPageSettings().addComponentResolver(new WicketServletAndJSPResolver());
	getMarkupSettings().setStripWicketTags(true);
    }

}
