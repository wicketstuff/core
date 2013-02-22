package org.wicketstuff.jwicket;


import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Find a page's child component by it's markup id
 */
public class ChildrenFinder implements IVisitor<Component, Void>, Serializable {
	private static final long serialVersionUID = 1L;
	private final String id;
	private List<Component> found = new ArrayList<Component>();

	public ChildrenFinder(String id) {
		this.id = id;
	}

	
	public void component(Component component, IVisit<Void> visit) {
		
		if (component.getParent().getMarkupId().equals(id)) {
			this.found.add(component);
			visit.stop();
		}
		if (component instanceof MarkupContainer) {
			// mocleiri: changed for 1.5 compatibility, not 100% sure it is equivalent to before.
			((MarkupContainer)component).visitChildren(this);
			visit.stop();
		}
	}

	public List<Component> getFoundComponents() {
		return found;
	}

}