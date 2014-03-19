package org.wicketstuff.gmap;

import org.apache.wicket.Component;

/**
 * 
 * @author robsonke
 *
 */
public class StatelessGMapHeaderContributor extends GMapHeaderContributor {
	private static final long serialVersionUID = 1L;

	public StatelessGMapHeaderContributor(final String scheme)
	{
		super(scheme);
	}
	
	@Override
	public boolean getStatelessHint(Component component) {
		return true;
	}
}
