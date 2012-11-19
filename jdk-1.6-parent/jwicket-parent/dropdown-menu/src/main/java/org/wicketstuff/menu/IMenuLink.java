package org.wicketstuff.menu;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.link.AbstractLink;

import java.io.Serializable;

public interface IMenuLink extends Serializable {

    AbstractLink getLink(final String id);

    Component getDisplayComponent(final String id);
}
