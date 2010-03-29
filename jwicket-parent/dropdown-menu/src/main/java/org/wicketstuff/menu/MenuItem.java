/*
 * This piece of code is dedicated to the wicket project (http://www.wicketframework.org).
 */
package org.wicketstuff.menu;


import java.io.Serializable;

import org.apache.wicket.markup.html.link.ILinkListener;
import org.apache.wicket.markup.html.link.IPageLink;
import org.apache.wicket.model.Model;


/**
 * The {@code MenuItem} class represents a single entry in a {@link Menu}. 
 *
 * @author Stefan Lindner (lindner@visionet.de)
 */
public class MenuItem implements Serializable{

	private static final long serialVersionUID = 1L;

	private final Model<String> model;
	private IPageLink pageLink;
	private ILinkListener linkLitener;
	private String externalUrl;
	private boolean enabled = true;
	private boolean visible = true;
	private MenuItemType menuItemType;

	/**
	 * Constructs a {@code MenuItem}.
	 * @param model The {@link Model} represents the label that is shown as representation for the link.
	 * @param pageLink Is a link to a {@link wicket.Page} that is to be displayed as reaction of clicking on
	 * this {@code MenuItem}.
	 */
	public MenuItem(final Model<String> model, IPageLink pageLink) {
		if (model == null)
			throw new IllegalArgumentException("argument [model] cannot be null");
		if (pageLink == null)
			throw new IllegalArgumentException("argument [pageLink] cannot be null");
		this.model = model;
		this.pageLink = pageLink;
		this.menuItemType = MenuItemType.PAGE_LINK;
	}


	/**
	 * Constructs a {@code MenuItem}.
	 * @param model The {@link Model} represents the label that is shown as representation for the link.
	 * @param linkLitener The {@code ILinkListener} is responsible for the action to be performed if the
	 * {@code MenuItem} was klicked.
	 */
	public MenuItem(final Model<String> model, ILinkListener linkLitener) {
		if (model == null)
			throw new IllegalArgumentException("argument [model] cannot be null");
		if (linkLitener == null)
			throw new IllegalArgumentException("argument [linkLitener] cannot be null");
		this.model = model;
		this.linkLitener = linkLitener;
		this.menuItemType = MenuItemType.LINK_LISTENER;
	}


	/**
	 * Constructs a {@code MenuItem} that leads to an external URL.
	 * @param model The {@link Model} represents the label that is shown as representation for the link.
	 * @param externalUrl A {@code String} representing the external url (e.g. {@code "http://www.wicketframework.org"}.
	 */
	public MenuItem(final Model<String> model, String externalUrl) {
		if (model == null)
			throw new IllegalArgumentException("argument [model] cannot be null");
		if (externalUrl == null)
			throw new IllegalArgumentException("argument [externalUrl] cannot be null");
		this.model = model;
		this.externalUrl = externalUrl;
		this.menuItemType = MenuItemType.EXTERNAL_URL;
	}


	/**
	 * Gets the {@link MenuItemType} that depends upon the way tis {@code MenuItem} was created.
	 * 
	 * <table border="1">
	  	<tr><th>{@link MenuItemType}</th><th>constructor</th></tr>
	  	<tr><td>{@link MenuItemType#PAGE_LINK}</td><td>{@link #MenuItem(Model, IPageLink)}</td></tr>
	  	<tr><td>{@link MenuItemType#LINK_LISTENER}</td><td>{@link #MenuItem(Model, ILinkListener)}</td></tr>
	  	<tr><td>{@link MenuItemType#EXTERNAL_URL}</td><td>{@link #MenuItem(Model, String)}</td></tr>
	   </table>
	 * 
	 * @return The {@link MenuItemType} of this {@code MenuItem}.
	 */
	public MenuItemType getMenuItemType() {
		return this.menuItemType;
	}

	/**
	 * Gets the {@link Model} representing the label for this {@code MenuItem}.
	 * @return The {@link Model}.
	 */
	public Model<String> getModel() {
		return this.model;
	}


	/**
	 * Gets the {@link ILinkListener} of this {@code MenuItem}.
	 * @return The {@link ILinkListener} if this MenuItem was constructed by {@link #MenuItem(Model, ILinkListener)}.
	 */
	public ILinkListener getLinkListener() {
		return this.linkLitener;
	}


	/**
	 * Gets the {@link IPageLink} of this {@code MenuItem}.
	 * @return The {@link IPageLink} if this MenuItem was constructed by {@link #MenuItem(Model, IPageLink)}.
	 */
	public IPageLink getPageLink() {
		return this.pageLink;
	}


	/**
	 * Gets the external URL of this {@code MenuItem}.
	 * @return The external URL as {@code String} if this MenuItem was constructed by {@link #MenuItem(Model, String)}.
	 */
	public String getExternalUrl() {
		return this.externalUrl;
	}


	/**
	 * Sets whether this {@code MenuItem} is enabled. An enabled  {@code MenuItem} is displayed normally
	 * and the associated link is active. A disabled {@code MenuItem} is displayed but it's HTML class
	 * attribute is set to {@code "disabled"}.
	 *
	 * @param enabled {@code true} if the {@code MenuItem} is enable, {@code false} if it is disabled.
	 * @return this
	 */
	public MenuItem setEnabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}


	/**
	 * Gets if this {@code MenuItem} is enabled
	 * @return {@code true} if the {@code MenuItem} is enable, {@code false} if it is disabled.
	 */
	public boolean isEnabled() {
		return this.enabled;
	}


	/**
	 * Sets whether this {@code MenuItem} is visible. An invisible {@code MenuItem} is not displayed at all.
	 *
	 * @param visible {@code true} if the {@code MenuItem} is visible, {@code false} if it is invisible.
	 * @return this
	 */
	public MenuItem setVisible(boolean visible) {
		this.visible = visible;
		return this;
	}


	/**
	 * Gets if this {@code MenuItem} is visible
	 * @return {@code true} if the {@code MenuItem} is visible, {@code false} if it is invisible.
	 */
	public boolean isVisible() {
		return this.visible;
	}


	private boolean escapeModelStrings = true;
	public MenuItem setEscapeModelStrings(final boolean value) {
		this.escapeModelStrings = value;
		return this;
	}
	public boolean getEscapeModelStrings() {
		return this.escapeModelStrings;
	}
}
