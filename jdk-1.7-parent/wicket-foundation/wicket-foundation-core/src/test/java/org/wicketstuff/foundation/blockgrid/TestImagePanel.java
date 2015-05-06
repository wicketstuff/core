package org.wicketstuff.foundation.blockgrid;

import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.PackageResourceReference;

public class TestImagePanel extends Panel {

	private static final long serialVersionUID = 1L;

	public TestImagePanel(String id, String imageFilename) {
		super(id);
		add(new Image("img", new PackageResourceReference(this.getClass(), imageFilename)));
	}

}
