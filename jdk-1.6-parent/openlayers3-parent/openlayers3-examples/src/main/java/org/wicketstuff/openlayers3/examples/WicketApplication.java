package org.wicketstuff.openlayers3.examples;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Provides an application that demonstrates the OpenLayers3 map.
 */
public class WicketApplication extends WebApplication {

    public WicketApplication() {

    }

    @Override
    protected void init() {
        super.init();

        BootstrapSettings settings = new BootstrapSettings();
        Bootstrap.install(this, settings);
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }
}