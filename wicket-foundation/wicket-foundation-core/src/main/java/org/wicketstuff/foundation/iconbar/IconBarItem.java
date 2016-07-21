package org.wicketstuff.foundation.iconbar;

import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.foundation.icon.IconType;

/**
 * Interface for the icon bar item.
 * @author ilkka
 *
 */
public interface IconBarItem {

	ResourceReference getImageResourceReference();
	IconType getIconType();
	String getLabel();
	
}
