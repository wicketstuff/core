package com.iluwatar.foundation.iconbar;

import org.apache.wicket.request.resource.ResourceReference;

import com.iluwatar.foundation.icon.IconType;

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
