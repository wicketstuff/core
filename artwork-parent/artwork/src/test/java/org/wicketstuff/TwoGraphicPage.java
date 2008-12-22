package org.wicketstuff;

import org.wicketstuff.artwork.ArtworkCanvasBehavior;
import org.wicketstuff.artwork.graphics.Border;
import org.wicketstuff.artwork.graphics.Shadow;

public class TwoGraphicPage extends AbstractGraphicPage {

	@Override
	protected ArtworkCanvasBehavior getBehavior() {
		return new ArtworkCanvasBehavior(new Border(),new Shadow());
	}

}
