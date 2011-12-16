package org.wicketstuff;

import org.wicketstuff.artwork.liquidcanvas.LiquidCanvasBehavior;
import org.wicketstuff.artwork.liquidcanvas.graphics.Border;

public class OneGraphicPage extends AbstractGraphicPage {

	@Override
	protected LiquidCanvasBehavior getBehavior() {
		return new LiquidCanvasBehavior(new Border());
	}

}
