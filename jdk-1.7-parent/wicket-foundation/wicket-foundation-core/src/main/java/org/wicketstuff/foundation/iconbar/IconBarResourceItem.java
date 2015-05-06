package org.wicketstuff.foundation.iconbar;

import java.io.Serializable;

import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.foundation.icon.IconType;

/**
 * IconBarItem resource implementation.
 * @author ilkka
 *
 */
public class IconBarResourceItem implements IconBarItem, Serializable {

	private static final long serialVersionUID = 1L;
	
	private ResourceReference resource;
	private String text;

	public IconBarResourceItem(ResourceReference resource, String text) {
		this.resource = resource;
		this.text = text;
	}
	
	@Override
	public ResourceReference getImageResourceReference() {
		return resource;
	}

	@Override
	public String getLabel() {
		return text;
	}

	@Override
	public IconType getIconType() {
		return null;
	}
}
