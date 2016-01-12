package org.wicketstuff.jquery.demo.lavalamp;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.jquery.lavalamp.LavaLampMenuPanel;

public final class BPage extends WebPage
{

	private static final long serialVersionUID = 1L;

	public BPage()
	{
		add(new LavaLampMenuPanel("lavalampMenu", Utils.newMenuListWithLinkStyles())
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected ResourceReference getCssResourceReference()
			{
				return new PackageResourceReference(BPage.class, "bpage.css");
			}
		});
	}

}
