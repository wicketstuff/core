package org.wicketstuff.foundation.thumbnail;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.foundation.component.FoundationBasePanel;
import org.wicketstuff.foundation.util.Attribute;

/**
 * 
 * Thumbnail component.
 *
 */
public class FoundationThumbnail extends FoundationBasePanel {

	private static final long serialVersionUID = 1L;

	public FoundationThumbnail(String id, final ResourceReference fullImage, ResourceReference thumbnail) {
		super(id);
		Link<Void> link = new Link<Void>("fullImageLink") {

			private static final long serialVersionUID = 1L;
			
			@Override
			protected void onComponentTag(ComponentTag tag) {
				Attribute.addClass(tag, "th");
				super.onComponentTag(tag);
			}

			@Override
			public void onClick() {
				throw new RedirectToUrlException(urlFor(fullImage, null).toString());
			}
		};
		add(link);
		link.add(new Image("thumbnail", thumbnail));
	}
}
