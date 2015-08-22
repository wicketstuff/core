package org.wicketstuff.jwicket.ui.dragdrop;


import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.jwicket.SpecialKeys;


/** This is a marker interface. You can mark any Wicket {@link Component}
 * with this interface and implement the interface's methods. iF the corresponding
 * {@link DraggableBehavior} has activated the callbacks for the events
 * then the dragged Wicket {@link Component}'s methods are called
 *
 */
public interface IDraggable {

	/**
	 * If the Wicket {@link Component} is marked as draggable by adding
	 * a {@link DraggableBehavior} to it and the {@link DraggableBehavior}
	 * has {@link DraggableBehavior#setWantOnDragStartNotification(boolean)} to true,
	 * this method is called when the user starts to drag this {@link Component}.
	 *
	 * @param target The {@link AjaxRequestTarget} associated with this
	 *			drag operation.
	 * @param specialKeys the special keys that were pressed when the event occurs
	 */
	void onDragStart(final AjaxRequestTarget target, final SpecialKeys specialKeys);


	/**
	 * If the Wicket {@link Component} is marked as draggable by adding
	 * a {@link DraggableBehavior} to it and the {@link DraggableBehavior}
	 * has {@link DraggableBehavior#setWantOnDragNotification(boolean)} to true,
	 * this method is called every time the mouse is moved while draging
	 * this {@link Component}. So be careful using this method!
	 *
	 * @param target The {@link AjaxRequestTarget} associated with this
	 *			drag operation.
	 * @param specialKeys the special keys that were pressed when the event occurs
	 */
	void onDrag(final AjaxRequestTarget target, final SpecialKeys specialKeys);
	

	/**
	 * If the Wicket {@link Component} is marked as draggable by adding
	 * a {@link DraggableBehavior} to it and the {@link DraggableBehavior}
	 * has {@link DraggableBehavior#setWantOnDragStopNotification(boolean)} to true,
	 * this method is called when the user stops to drag this {@link Component}.
	 * 
	 * The drag operation is stopped by releasing the mouse button and it
	 * is makes no difference if you stop the drag operation by dropping the 
	 * {@link Component} onto a droppable or somewhere around the page.
	 * 
	 * There is no guarantee that this method is called before the 
	 * droppable {@link Component}'s callback method {@code onDrop} is
	 * called.
	 * 
	 *
	 * @param target The {@link AjaxRequestTarget} associated with this
	 *			drag operation.
	 * @param specialKeys the special keys that were pressed when the event occurs
	 */
	void onDragStop(final AjaxRequestTarget target, final SpecialKeys specialKeys);
}
