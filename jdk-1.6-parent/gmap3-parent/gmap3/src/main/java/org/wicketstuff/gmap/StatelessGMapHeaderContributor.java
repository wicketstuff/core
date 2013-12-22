package org.wicketstuff.gmap;

import org.apache.wicket.Component;

public class StatelessGMapHeaderContributor extends GMapHeaderContributor {
	private static final long serialVersionUID = 1L;

	@Override
	public boolean getStatelessHint(Component component) {
		return true;
	}
}
