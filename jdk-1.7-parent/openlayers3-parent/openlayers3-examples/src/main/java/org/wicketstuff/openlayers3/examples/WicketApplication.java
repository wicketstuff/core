package org.wicketstuff.openlayers3.examples;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Provides an application that demonstrates the OpenLayers3 map.
 */
public class WicketApplication extends WebApplication {

    public WicketApplication() {

    }

    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }
}