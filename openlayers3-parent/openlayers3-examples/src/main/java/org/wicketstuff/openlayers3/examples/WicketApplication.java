package org.wicketstuff.openlayers3.examples;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.annotation.scan.AnnotatedMountScanner;
import de.agilecoders.wicket.webjars.WicketWebjars;
import de.agilecoders.wicket.webjars.settings.WebjarsSettings;

/**
 * Provides an application that demonstrates the OpenLayers3 map.
 */
public class WicketApplication extends WebApplication {
	@Override
	protected void init() {
		super.init();
		getCspSettings().blocking().disabled();

		// scan for annotations
		new AnnotatedMountScanner().scanPackage("org.wicketstuff.openlayers3.examples").mount(this);

		// setup webjars
		WebjarsSettings webjarsSettings = new WebjarsSettings();
		WicketWebjars.install(this, webjarsSettings);

		// setup wicket boostrap
		BootstrapSettings bootstrapSettings = new BootstrapSettings();
		Bootstrap.install(this, bootstrapSettings);

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
