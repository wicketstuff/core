package com.googlecode.wicket.jquery.ui.samples.pages.kendo.dragdrop;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;

import com.googlecode.wicket.kendo.ui.console.Console;
import com.googlecode.wicket.kendo.ui.interaction.draggable.DraggableBehavior;
import com.googlecode.wicket.kendo.ui.interaction.droppable.DroppableBehavior;

public class BehaviorDragDropPage extends AbstractDragDropPage
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
		container.add(this.newDraggableBehavior());
		this.add(container);

		// droppable //
		this.add(this.newDroppableBehavior("#wrapper-panel-frame"));
	}

	private DraggableBehavior newDraggableBehavior()
	{
		return new DraggableBehavior() {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCancelEventEnabled()
			{
				// not enabled to prevent unnecessary server round-trips.
				return false;
			}

			@Override
			public void onDragStart(AjaxRequestTarget target, int top, int left)
			{
				info(target, String.format("Draggable: onDragStart, position [%d, %d]", top, left));
			}

			@Override
			public void onDragStop(AjaxRequestTarget target, int top, int left)
			{
				info(target, String.format("Draggable: onDragStop, position [%d, %d]", top, left));
			}

			@Override
			public void onDragCancel(AjaxRequestTarget target, int top, int left)
			{
				// noop
			}
		};
	}

	private DroppableBehavior newDroppableBehavior(String selector)
	{
		return new DroppableBehavior(selector) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isDragEnterEventEnabled()
			{
				// not enabled to prevent unnecessary server round-trips.
				return false;
			}

			@Override
			public boolean isDragLeaveEventEnabled()
			{
				// not enabled to prevent unnecessary server round-trips.
				return false;
			}

			@Override
			public void onDragEnter(AjaxRequestTarget target, Component component)
			{
				// noop 
			}

			@Override
			public void onDragLeave(AjaxRequestTarget target, Component component)
			{
				// noop
			}

			@Override
			public void onDrop(AjaxRequestTarget target, Component component)
			{
				info(target, "Droppable: onDrop", component);
			}
		};
	}

	protected void info(AjaxRequestTarget target, String message)
	{
		this.console.info(target, message);
	}

	protected void info(AjaxRequestTarget target, String message, Component component)
	{
		this.console.info(target, String.format("%s - %s", message, component.getMarkupId()));
	}
}
