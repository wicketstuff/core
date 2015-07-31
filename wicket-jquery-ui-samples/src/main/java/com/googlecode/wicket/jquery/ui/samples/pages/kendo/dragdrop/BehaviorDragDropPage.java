package com.googlecode.wicket.jquery.ui.samples.pages.kendo.dragdrop;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;

import com.googlecode.wicket.kendo.ui.console.Console;
import com.googlecode.wicket.kendo.ui.interaction.draggable.DraggableBehavior;
import com.googlecode.wicket.kendo.ui.interaction.draggable.IDraggableListener;
import com.googlecode.wicket.kendo.ui.interaction.droppable.DroppableAdaper;
import com.googlecode.wicket.kendo.ui.interaction.droppable.DroppableBehavior;
import com.googlecode.wicket.kendo.ui.interaction.droppable.IDroppableListener;

public class BehaviorDragDropPage extends AbstractDragDropPage implements IDraggableListener
{
	private static final long serialVersionUID = 1L;

	private final Console console;

	public BehaviorDragDropPage()
	{
		// console //
		this.console = new Console("console");
		this.add(this.console);

		// draggable //
		WebMarkupContainer container = new WebMarkupContainer("draggable");
		container.add(new DraggableBehavior(this));
		this.add(container);

		// droppable //
		this.add(new DroppableBehavior("#wrapper-panel-frame", this.newDroppableListener()));
	}

	// Methods //

	protected void info(AjaxRequestTarget target, String message)
	{
		this.console.info(target, message);
	}

	protected void info(AjaxRequestTarget target, String message, Component component)
	{
		this.console.info(target, String.format("%s - %s", message, component.getMarkupId()));
	}

	// IDraggableListener //

	@Override
	public boolean isCancelEventEnabled()
	{
		// not enabled to prevent unnecessary server round-trips.
		return false;
	}

	@Override
	public void onDragStart(AjaxRequestTarget target, int top, int left)
	{
		this.info(target, String.format("Draggable: onDragStart, position [%d, %d]", top, left));
	}

	@Override
	public void onDragStop(AjaxRequestTarget target, int top, int left)
	{
		this.info(target, String.format("Draggable: onDragStop, position [%d, %d]", top, left));
	}

	@Override
	public void onDragCancel(AjaxRequestTarget target, int top, int left)
	{
		// noop
	}

	// IDroppableListener //

	private IDroppableListener newDroppableListener()
	{
		return new DroppableAdaper() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public void onDrop(AjaxRequestTarget target, Component component)
			{
				info(target, "Droppable: onDrop", component);
			}
		};
	}
}
