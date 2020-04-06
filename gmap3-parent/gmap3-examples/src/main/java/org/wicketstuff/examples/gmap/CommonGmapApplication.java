package org.wicketstuff.examples.gmap;

import org.apache.wicket.protocol.http.WebApplication;

public abstract class CommonGmapApplication extends WebApplication {
	@Override
	protected void init() {
		getCspSettings().blocking().disabled();
		super.init();
	}
}
