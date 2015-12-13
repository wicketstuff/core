package org.wicketstuff.selectize;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class TestApplication extends WebApplication{

    @Override
    public Class<? extends Page> getHomePage() {
	return TestPage.class;
    }

    @Override
    protected void init() {
        super.init();

//        getMarkupSettings().setStripWicketTags(true);
    }
}
