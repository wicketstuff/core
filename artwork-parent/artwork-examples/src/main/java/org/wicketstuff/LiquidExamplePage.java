package org.wicketstuff;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.wicketstuff.artwork.liquidcanvas.LiquidCanvasBehavior;
import org.wicketstuff.artwork.liquidcanvas.graphics.Border;
import org.wicketstuff.artwork.liquidcanvas.graphics.Gradient;
import org.wicketstuff.artwork.liquidcanvas.graphics.Graphics;
import org.wicketstuff.artwork.liquidcanvas.graphics.RoundedRect;
import org.wicketstuff.artwork.liquidcanvas.graphics.Shadow;
import org.wicketstuff.artwork.niftycorners.NiftyCornersBehavior;

/**
 * 
 */
public class LiquidExamplePage extends ArtworkParentExamplePage {

	private static final long serialVersionUID = 1L;

	// TODO Add any page properties or variables here

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
    public LiquidExamplePage(final PageParameters parameters) {
    	super(parameters);

        // Add the simplest type of label
    	Graphics g=new Shadow();
    	g.setChainedGraphics(new Border()).setChainedGraphics(new Gradient());
    	LiquidCanvasBehavior a=new LiquidCanvasBehavior(g,new RoundedRect());
        add(new WebMarkupContainer("message").add(a));
        
        
        add(new WebMarkupContainer("box").add(new NiftyCornersBehavior()));

        // TODO Add your page's components here
    }
}
