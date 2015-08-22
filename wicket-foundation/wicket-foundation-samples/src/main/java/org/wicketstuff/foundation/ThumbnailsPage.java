package org.wicketstuff.foundation;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.wicketstuff.foundation.thumbnail.FoundationThumbnail;

public class ThumbnailsPage extends BasePage {

	private static final long serialVersionUID = 1L;

	public ThumbnailsPage(PageParameters params) {
		super(params);
		add(new FoundationThumbnail("thumbnail", new PackageResourceReference(this.getClass(), "space.jpg"), 
				new PackageResourceReference(this.getClass(), "space-th-sm.jpg")));
	}
}
