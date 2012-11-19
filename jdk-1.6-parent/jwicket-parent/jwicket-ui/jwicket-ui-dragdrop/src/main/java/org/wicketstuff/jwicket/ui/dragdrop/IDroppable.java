package org.wicketstuff.jwicket.ui.dragdrop;


import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.jwicket.SpecialKeys;


/** This is a marker interfaece. You can mark any Wicket {@link Component}
 * with this interface and implement the interface's methods. iF the corresponding
 * {@link DroppableBehavior} has activated the callbacks for the events
 * then the droppable Wicket {@link Component}'s methods are called
 *
 */
public interface IDroppable {

	/**
	 * If the Wicket {@link Component} is marked as droppable by adding
	 * a {@link DroppableBehavior} to it,
	 * this method is called when a draggable {@link Component} is dropped
	 * onto this {@link Component} and  this {@link Component} accepts the draggable.
	 *
	 * @param target The {@link AjaxRequestTarget} associated with this
	 *			drop operation.
	 * @param draggedComponent The dragged component
	 * @param specialKeys the special keys that were pressed when the event occurs
	 */
	public void onDrop(final AjaxRequestTarget target, final Component draggedComponent, final SpecialKeys specialKeys);

	/**
	 * This method is called when a draggable {@link Component} is starting to
	 * drag and the dragging {@link Component}'s name is accepted to be
	 * dropped onto this {@link Component}.
	 *
	 * @param target The {@link AjaxRequestTarget} associated with this
	 *			drop operation.
	 * @param draggedComponent The dragged component
	 * @param specialKeys the special keys that were pressed when the event occurs
	 */
	public void onActivate(final AjaxRequestTarget target, final Component draggedComponent, final SpecialKeys specialKeys);

	/**
	 * This method is called when a draggable {@link Component} has stopped
	 * dragging and the dragging {@link Component}'s name was accepted to be
	 * dropped onto this {@link Component}.
	 *
	 * @param target The {@link AjaxRequestTarget} associated with this
	 * 			drop operation.
	 * @param draggedComponent The dragged component
	 * @param specialKeys the special keys that were pressed when the event occurs
	 */
	public void onDeactivate(final AjaxRequestTarget target, final Component draggedComponent, final SpecialKeys specialKeys);
}
