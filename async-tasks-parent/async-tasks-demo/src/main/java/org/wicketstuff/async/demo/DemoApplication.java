package org.wicketstuff.async.demo;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

public class DemoApplication extends WebApplication {
    @Override
    public Class<? extends WebPage> getHomePage() {
        return DemoPage.class;
    }
}
