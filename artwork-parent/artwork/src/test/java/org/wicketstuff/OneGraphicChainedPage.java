package org.wicketstuff;

import org.wicketstuff.artwork.liquidcanvas.LiquidCanvasBehavior;
import org.wicketstuff.artwork.liquidcanvas.graphics.Border;
import org.wicketstuff.artwork.liquidcanvas.graphics.Graphics;
import org.wicketstuff.artwork.liquidcanvas.graphics.Shadow;

public class OneGraphicChainedPage extends AbstractGraphicPage {

	@Override
	protected LiquidCanvasBehavior getBehavior() {
		Graphics g=new Border();
		g.setChainedGraphics(new Shadow());
		
		return new LiquidCanvasBehavior(g);
	}

}
