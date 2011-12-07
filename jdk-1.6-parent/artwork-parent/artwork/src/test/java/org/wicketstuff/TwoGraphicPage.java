package org.wicketstuff;

import org.wicketstuff.artwork.liquidcanvas.LiquidCanvasBehavior;
import org.wicketstuff.artwork.liquidcanvas.graphics.Border;
import org.wicketstuff.artwork.liquidcanvas.graphics.Shadow;

public class TwoGraphicPage extends AbstractGraphicPage {

	@Override
	protected LiquidCanvasBehavior getBehavior() {
		return new LiquidCanvasBehavior(new Border(),new Shadow());
	}

}
