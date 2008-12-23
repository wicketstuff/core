package org.wicketstuff;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.artwork.ArtworkCanvasBehavior;
import org.wicketstuff.artwork.graphics.Border;
import org.wicketstuff.artwork.graphics.Gradient;
import org.wicketstuff.artwork.graphics.Graphics;
import org.wicketstuff.artwork.graphics.RoundedRect;
import org.wicketstuff.artwork.graphics.Shadow;

/**
 * Homepage
 */
public class HomePage extends WebPage {

	private static final long serialVersionUID = 1L;

	// TODO Add any page properties or variables here

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
    public HomePage(final PageParameters parameters) {

        // Add the simplest type of label
    	Graphics g=new Shadow();
    	g.setChainedGraphics(new Border()).setChainedGraphics(new Gradient());
    	ArtworkCanvasBehavior a=new ArtworkCanvasBehavior(g,new RoundedRect());
        add(new Label("message", "If you see this message wicket is properly configured and running").add(a));

        // TODO Add your page's components here
    }
}
