package org.wicketstuff;

import org.wicketstuff.artwork.ArtworkCanvasBehavior;
import org.wicketstuff.artwork.graphics.Border;
import org.wicketstuff.artwork.graphics.Graphics;
import org.wicketstuff.artwork.graphics.Shadow;

public class OneGraphicChainedPage extends AbstractGraphicPage {

	@Override
	protected ArtworkCanvasBehavior getBehavior() {
		Graphics g=new Border();
		g.setChainedGraphics(new Shadow());
		
		return new ArtworkCanvasBehavior(g);
	}

}
