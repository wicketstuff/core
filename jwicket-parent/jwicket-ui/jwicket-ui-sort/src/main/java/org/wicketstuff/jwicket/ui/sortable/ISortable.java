package org.wicketstuff.jwicket.ui.sortable;


import org.apache.wicket.ajax.AjaxRequestTarget;


public interface ISortable {

	public void onSorted(final AjaxRequestTarget target, final int newPosition);

	public void onRemoved(final AjaxRequestTarget target);

	public void onReceived(final AjaxRequestTarget target, final int newPosition);
	
}
