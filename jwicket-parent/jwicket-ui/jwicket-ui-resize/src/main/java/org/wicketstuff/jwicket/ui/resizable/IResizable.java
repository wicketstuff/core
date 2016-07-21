package org.wicketstuff.jwicket.ui.resizable;


import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.jwicket.SpecialKeys;


/** This is a marker interface. You can mark any Wicket {@link Component}
 * with this interface and implement the interface's methods to
 * react on resizing.
 *
 */
public interface IResizable {

	/**
	 * If the Wicket {@link Component} is marked as resizable by adding
	 * a {@link ResizableBehavior} to it and the {@link ResizableBehavior}
	 * has set {@link ResizableBehavior#setWantOnresizeStartNotification(boolean)}
	 * to true,
	 * this method is called when the resize operation starts.
	 *
	 * @param target The {@link AjaxRequestTarget} associated with this
	 * @param top the current top in px
	 * @param left the current left in px 
	 * @param width the current width in px
	 * @param height the current height in px
	 * @param specialKeys the special keys that were pressed when the event occurs
	 */
	public void onResizeStart(final AjaxRequestTarget target, final int top, final int left, final int width, final int height, final SpecialKeys specialKeys);

	
	/**
	 * If the Wicket {@link Component} is marked as resizable by adding
	 * a {@link ResizableBehavior} to it and the {@link ResizableBehavior}
	 * has set {@link ResizableBehavior#setWantOnResizeNotification(boolean)}
	 * to true,
	 * this method is called every time the mouse moves during the resize operation.
	 *
	 * @param target The {@link AjaxRequestTarget} associated with this
	 * @param top the current top in px
	 * @param left the current left in px 
	 * @param width the current width in px
	 * @param height the current height in px
	 * @param specialKeys the special keys that were pressed when the event occurs
	 */
	public void onResize(final AjaxRequestTarget target, final int top, final int left, final int width, final int height, final SpecialKeys specialKeys);


	/**
	 * If the Wicket {@link Component} is marked as resizable by adding
	 * a {@link ResizableBehavior} to it,
	 * this method is called after the resize operatioin has finished
	 *
	 * @param target The {@link AjaxRequestTarget} associated with this
	 * @param top the final top in px after resizing
	 * @param left the final left in px after resizing
	 * @param width the final width in px after resizing
	 * @param height the final height in px after resizing
	 * @param originalTop the original top in px before resizing
	 * @param originalLeft the final original left in px before resizing
	 * @param originalWidth the final original width in px before resizing
	 * @param originalHeight the final original height in px before resizing
	 * @param specialKeys the special keys that were pressed when the event occurs
	 */
	public void onResized(final AjaxRequestTarget target,
			final int top, final int left, final int width, final int height,
			final int originalTop, final int originalLeft, final int originalWidth,
			final int originalHeight, final SpecialKeys specialKeys);
}
