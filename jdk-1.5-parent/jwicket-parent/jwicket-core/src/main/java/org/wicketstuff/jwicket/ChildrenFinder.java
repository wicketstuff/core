package org.wicketstuff.jwicket;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Component.IVisitor;


/**
 * Find a page's child component by it's markup id
 */
public class ChildrenFinder implements IVisitor<Component>, Serializable {
	private static final long serialVersionUID = 1L;
	private final String id;
	private List<Component> found = new ArrayList<Component>();

	public ChildrenFinder(String id) {
		this.id = id;
	}

	public Object component(Component component) {
		if (component.getParent().getMarkupId().equals(id)) {
			this.found.add(component);
			return IVisitor.STOP_TRAVERSAL;
		}
		if (component instanceof MarkupContainer) {
			return ((MarkupContainer)component).visitChildren(this);
		}
		return IVisitor.CONTINUE_TRAVERSAL;
	}

	public List<Component> getFoundComponents() {
		return found;
	}

}