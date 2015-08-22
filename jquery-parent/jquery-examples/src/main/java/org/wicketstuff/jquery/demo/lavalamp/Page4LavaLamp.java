package org.wicketstuff.jquery.demo.lavalamp;

import java.util.List;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.jquery.demo.PageSupport;
import org.wicketstuff.jquery.lavalamp.LavaLampMenuPanel;
import org.wicketstuff.jquery.lavalamp.MenuItem;

public final class Page4LavaLamp extends PageSupport
{
	private static final long serialVersionUID = 1L;

	public Page4LavaLamp()
	{
		super();
		IModel<List<MenuItem>> model = new AbstractReadOnlyModel<List<MenuItem>>()
		{

			private static final long serialVersionUID = 1L;

			@Override
			public List<MenuItem> getObject()
			{
				return Utils.newMenuList();
			}
		};

		add(new LavaLampMenuPanel("lavalampMenu", model)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected ResourceReference getCssResourceReference()
			{
				return new PackageResourceReference(Page4LavaLamp.class, "lavalamp.css");
			}
		});
	}

}
