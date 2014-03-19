package org.wicketstuff;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.wicketstuff.artwork.liquidcanvas.LiquidCanvasBehavior;
import org.wicketstuff.artwork.liquidcanvas.graphics.Border;
import org.wicketstuff.artwork.liquidcanvas.graphics.Gradient;
import org.wicketstuff.artwork.liquidcanvas.graphics.Graphics;
import org.wicketstuff.artwork.liquidcanvas.graphics.RoundedRect;
import org.wicketstuff.artwork.liquidcanvas.graphics.Shadow;

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

    	Graphics g=new Shadow();
    	g.setChainedGraphics(new Border()).setChainedGraphics(new Gradient());
    	LiquidCanvasBehavior liquidCanvasBehaviorOne=new LiquidCanvasBehavior(g,new RoundedRect());
    	
    	WebMarkupContainer liquidOneExample=new WebMarkupContainer("LiquidOne");
    	liquidOneExample.add(liquidCanvasBehaviorOne);
        add(liquidOneExample);
        
        
    	g=new Shadow(9,"#FF3300",2);
    	g.setChainedGraphics(new Border()).setChainedGraphics(new Gradient());
    	LiquidCanvasBehavior liquidCanvasBehaviorTwo=new LiquidCanvasBehavior(g,new RoundedRect());

        
    	WebMarkupContainer liquidTwoExample=new WebMarkupContainer("LiquidTwo");
    	liquidTwoExample.add(liquidCanvasBehaviorTwo);
        add(liquidTwoExample);

        
    	g=new Gradient("rgba(255,255,255, 0.2)","#888");
    	
    	LiquidCanvasBehavior liquidCanvasBehaviorThree=new LiquidCanvasBehavior(g,new RoundedRect());

        
    	WebMarkupContainer liquidThreeExample=new WebMarkupContainer("LiquidThree");
    	liquidThreeExample.add(liquidCanvasBehaviorThree);
        add(liquidThreeExample);
        
        
        
    }
}
