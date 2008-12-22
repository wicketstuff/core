package org.wicketstuff;

import org.wicketstuff.artwork.ArtworkCanvasBehavior;
import org.wicketstuff.artwork.graphics.Border;

public class OneGraphicPage extends AbstractGraphicPage {

	@Override
	protected ArtworkCanvasBehavior getBehavior() {
		return new ArtworkCanvasBehavior(new Border());
	}

}
