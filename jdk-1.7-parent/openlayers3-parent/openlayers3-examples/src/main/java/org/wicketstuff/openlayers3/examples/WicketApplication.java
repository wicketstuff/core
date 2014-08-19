package org.wicketstuff.openlayers3.examples;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.annotation.scan.AnnotatedMountScanner;

/**
 * Provides an application that demonstrates the OpenLayers3 map.
 */
public class WicketApplication extends WebApplication {

    /**
     * Creates a new instance.
     */
    public WicketApplication() {

    }

    @Override
    protected void init() {
        super.init();

        // scan for annotations
        new AnnotatedMountScanner().scanPackage("org.wicketstuff.openlayers3.examples").mount(this);

        // setup wicket boostrap
        BootstrapSettings settings = new BootstrapSettings();
        Bootstrap.install(this, settings);
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

    @Override
    public RuntimeConfigurationType getConfigurationType() {
        return RuntimeConfigurationType.DEVELOPMENT;
    }
}