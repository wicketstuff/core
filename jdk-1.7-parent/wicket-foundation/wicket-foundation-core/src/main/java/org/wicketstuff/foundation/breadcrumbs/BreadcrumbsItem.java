package org.wicketstuff.foundation.breadcrumbs;

import java.io.Serializable;

/**
 * Item for FoundationBreadcrumbs component.
 * @author ilkka
 *
 */
public class BreadcrumbsItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String title;
	private boolean disabled;
	private boolean current;

	public BreadcrumbsItem(String title) {
		this.title = title;
	}
	
	public BreadcrumbsItem(String title, boolean disabled, boolean current) {
		this.title = title;
		this.disabled = disabled;
		this.current = current;
	}

	public String getTitle() {
		return title;
	}

	public BreadcrumbsItem setTitle(String title) {
		this.title = title;
		return this;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public BreadcrumbsItem setDisabled(boolean disabled) {
		this.disabled = disabled;
		return this;
	}

	public boolean isCurrent() {
		return current;
	}

	public BreadcrumbsItem setCurrent(boolean current) {
		this.current = current;
		return this;
	}	
}
