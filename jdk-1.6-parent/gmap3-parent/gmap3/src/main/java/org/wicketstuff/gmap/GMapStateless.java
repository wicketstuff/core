package org.wicketstuff.gmap;

import org.apache.wicket.Component;

/**
 * A stateless version of the GMap component. This can be used in stateless pages.
 * 
 */
public class GMapStateless extends GMap {

	private static final long serialVersionUID = 1L;

	public GMapStateless(String id, StatelessGMapHeaderContributor headerContrib) {
		super(id, headerContrib);

		map.setMarkupId(map.getId());
	}

	@Override
	protected OverlayListener getOverlayListener() {
		return new StatelessOverlayListener();
		
	}
	
	private class StatelessOverlayListener extends OverlayListener
	{
		private static final long serialVersionUID = 1L;
		
		@Override
        public boolean getStatelessHint(Component component) {
        	return true;
        }
		
		@Override
	    protected void onBind()
	    {
	        super.onBind();
	        getComponent().getBehaviorId(this);
	    }
	}
	
	@Override
	protected boolean getStatelessHint() {
		return true;
	}
}
