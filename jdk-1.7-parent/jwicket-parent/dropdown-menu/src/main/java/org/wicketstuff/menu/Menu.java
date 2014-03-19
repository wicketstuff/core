package org.wicketstuff.menu;


import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The {@code Menu} class represents a complete menu within a {@link MenuBarPanel}. A
 * {@link MenuBarPanel} consist of a {@code List} of {@link Menu}s. 
 *
 * @author Stefan Lindner (lindner@visionet.de)
 */
public class Menu implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Model<String> model;
	private final List<IMenuLink> menuItems;
	private boolean visible = true;

	/**
	 * Constructs a menu.
	 *
	 * @param model The {@link Model} that name of the menu
	 * @param menuItems A {@code List} of the IMenuLink that belong to this {@link Menu}.
	 */
	public Menu(final Model<String> model, final List<IMenuLink> menuItems) {
		if (model == null) {
			throw new IllegalArgumentException("argument [model] cannot be null");
		}
		if (menuItems == null) {
			throw new IllegalArgumentException("argument [menuItems] cannot be null");
		}

		this.model = model;
		this.menuItems = menuItems;
	}


	public Menu(final Model<String> model) {
		this(model, new ArrayList<IMenuLink>());
	}
	
	
	public Menu addMenuItem(final IMenuLink item) {
		menuItems.add(item);
		return this;
	}

	
	/**
	 * Gets the model. It returns the object that wraps the backing model. The model of a
	 * {@link Menu} is used to hold the {@code String} that is used to disply the title
	 * of a menu.
	 * 
	 * @return The model
	 */
	public Model<String> getModel() {
		return this.model;
	}


	/**
	 * Gets the {@link IMenuLink}s of this {@link Menu}.
	 *
	 * @return All {@link IMenuLink}s of this {@link Menu}.
	 */
	public List<IMenuLink> getMenuItems() {
		return this.menuItems;
	}


	/**
	 * Sets wheter the complete {@link Menu} is visible.
	 *
	 * @param visible {@code true} if this {@link Menu} should be visible.
	 * @return this
	 */
	public Menu setVisible(boolean visible) {
		this.visible = visible;
		return this;
	}


	/**
	 * Gets whether the {@link Menu} is visible.
	 *
	 * @return {@code true} if the {@link Menu} is visible.
	 */
	public boolean isVisible() {
		return this.visible;
	}
	
	
	
	
	
	private Component associatedComponent;
	public Menu setAssociatedComponent(final Component c) {
		this.associatedComponent = c;
		return this;
	}


	public void redraw(final AjaxRequestTarget target) {
		if (this.associatedComponent != null) {
			target.add(this.associatedComponent);
		}
	}



	public String toString() {
		return "Menu: " + getModel().getObject() + " visible = " + isVisible();
	}

}
