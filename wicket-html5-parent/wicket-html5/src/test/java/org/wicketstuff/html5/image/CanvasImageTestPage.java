package org.wicketstuff.html5.image;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.wicketstuff.html5.image.CanvasImage;

public class CanvasImageTestPage extends WebPage
{
	private static final long serialVersionUID = 1L;

	public CanvasImageTestPage()
	{
		add(new CanvasImage("canvasimage", new PackageResourceReference(CanvasImageTest.class,
			"CanvasImage.gif")));
	}
}
