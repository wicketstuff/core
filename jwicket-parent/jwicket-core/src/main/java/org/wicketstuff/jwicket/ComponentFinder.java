package org.wicketstuff.jwicket;


import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Component.IVisitor;


/**
 * Find a page's child component by it's markup id
 */
public class ComponentFinder implements IVisitor<Component>, Serializable {
	private static final long serialVersionUID = 1L;
	private final String id;
	private Component found;

	public ComponentFinder(String id) {
		this.id = id;
	}

	public Object component(Component component) {
		if (component.getMarkupId().equals(id)) {
			this.found = component;
			return IVisitor.STOP_TRAVERSAL;
		}
		if (component instanceof MarkupContainer) {
			return ((MarkupContainer)component).visitChildren(this);
		}
		return IVisitor.CONTINUE_TRAVERSAL;
	}

	public Component getFoundComponent() {
		return found;
	}

}