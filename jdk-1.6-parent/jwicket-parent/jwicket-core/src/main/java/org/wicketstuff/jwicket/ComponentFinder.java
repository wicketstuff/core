package org.wicketstuff.jwicket;

import org.apache.wicket.Component;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import java.io.Serializable;


/**
 * Find a page's child component by it's markup id
 */
public class ComponentFinder implements IVisitor<Component, Component>, Serializable {
    private static final long serialVersionUID = 1L;

    private final String markupId;

    public ComponentFinder(String markupId) {
        this.markupId = markupId;
    }

    @Override
    public void component(Component component, IVisit<Component> visit) {
        if (component.getMarkupId().equals(getMarkupId())) {
            visit.stop(component);
        }
    }

    private String getMarkupId() {
        return this.markupId;
    }
}