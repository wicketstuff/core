package org.wicketstuff.jwicket;


import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;


/**
 * Find a page's child component by it's markup id
 */
public class ComponentFinder implements IVisitor<Component, Void>, Serializable {
	private static final long serialVersionUID = 1L;
	private final String id;
	private Component found;

	public ComponentFinder(String id) {
		this.id = id;
	}

	
	public void component(Component component, IVisit<Void> visit) {
		
		if (component.getMarkupId().equals(id)) {
			this.found = component;
			visit.stop();
		}
		if (component instanceof MarkupContainer) {
			((MarkupContainer)component).visitChildren(this);
			
			visit.stop();
		}
	}

	public Component getFoundComponent() {
		return found;
	}

}