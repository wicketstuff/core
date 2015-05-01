package org.wicketstuff.html5.image;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.resource.PackageResourceReference;

public class InlineImageTestPage extends WebPage
{
	private static final long serialVersionUID = 1L;

	public InlineImageTestPage()
	{
		add(new InlineImage("inlineimage", new PackageResourceReference(InlineImageTest.class,
			"InlineImage.gif")));
	}
}
