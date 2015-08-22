package org.wicketstuff.context.examples;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.context.ContextProvider;

public class WicketApplication extends WebApplication {
    
    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }
    
    @Override
    public void init() {
        super.init();
        getComponentInitializationListeners().add(new ContextProvider());
    }

}
