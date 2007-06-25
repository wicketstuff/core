package org.wicketstuff.animator;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 * This is a convenience class for extracting the markupIds of the provided
 * components. Use this class to create models for the target parameter of any
 * StyleSubject.
 * 
 * @author Gerolf
 * 
 */
public class MarkupIdModel extends LoadableDetachableModel {

	private static final long serialVersionUID = 1L;
	private Set<Component> components;

	/**
	 * Constructs the model and adds the specified component.
	 * 
	 * @param component
	 *            the component to be added.
	 */
	public MarkupIdModel(Component component) {
		add(component);
	}

	/**
	 * Constructs the model and adds the specified components.
	 * 
	 * @param components
	 *            the components to be added.
	 */
	public MarkupIdModel(Collection<? extends Component> components) {
		add(components);
	}

	/**
	 * Use this method to add another component to the model.
	 * 
	 * @param component
	 *            the component to be added.
	 * @return this {@link MarkupIdModel} for fluent method calls.
	 */
	public MarkupIdModel add(Component component) {
		if (components == null) {
			components = new HashSet<Component>();
		}
		components.add(component);
		return this;
	}

	/**
	 * Use this method to add more components to the model.
	 * 
	 * @param components
	 *            the components to be added.
	 * @return this {@link MarkupIdModel} for fluent method calls.
	 */
	public MarkupIdModel add(Collection<? extends Component> components) {
		if (components == null) {
			components = new HashSet<Component>();
		}
		this.components.addAll(components);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.wicket.model.LoadableDetachableModel#load()
	 */
	@Override
	protected Object load() {
		Set<String> ids = new HashSet<String>();
		for (Component component : components) {
			ids.add(component.getMarkupId());
		}
		return ids;
	}
}
