package org.wicketstuff.foundation.iconbar;

import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.foundation.icon.IconType;

/**
 * IconBarItem font implementation.
 * @author ilkka
 *
 */
public class IconBarFontItem implements IconBarItem {

	private String text;
	private IconType iconType;

	public IconBarFontItem(IconType iconType, String text) {
		this.iconType = iconType;
		this.text = text;
	}
	
	@Override
	public ResourceReference getImageResourceReference() {
		return null;
	}

	@Override
	public String getLabel() {
		return text;
	}

	@Override
	public IconType getIconType() {
		return iconType;
	}
}
