package org.wicketstuff.menu;


import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.link.AbstractLink;


public interface IMenuLink extends Serializable {

	public AbstractLink getLink(final String id);
	public Component getDisplayComponent(final String id);

}
