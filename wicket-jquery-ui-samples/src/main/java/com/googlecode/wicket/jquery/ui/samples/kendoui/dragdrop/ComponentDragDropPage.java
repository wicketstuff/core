package com.googlecode.wicket.jquery.ui.samples.kendoui.dragdrop;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.resource.StyleSheetPackageHeaderItem;
import com.googlecode.wicket.kendo.ui.console.Console;
import com.googlecode.wicket.kendo.ui.interaction.draggable.Draggable;
import com.googlecode.wicket.kendo.ui.interaction.droppable.Droppable;

public class ComponentDragDropPage extends AbstractDragDropPage
{
	private static final long serialVersionUID = 1L;

	private final Console console;

	public ComponentDragDropPage()
	{
		// console //
		this.console = new Console("console");
		this.add(this.console);

		// draggable //
		this.add(new Draggable<String>("draggable", Model.of("Draggable")));

		// droppable //
		this.add(this.newDroppable("droppable1", "green area"));
		this.add(this.newDroppable("droppable2", "blue area"));
	}

	// Methods //

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(ComponentDragDropPage.class));
	}

	// Factories //

	/**
	 * Gets a new Droppable.<br>
	 * By default 'drag-enter' and 'drag-leave' events are disabled to minimize client/server round-trips.
	 */
	private Droppable<String> newDroppable(String id, String color)
	{
		return new Droppable<String>(id, Model.of(color)) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onDragEnter(AjaxRequestTarget target, Component component)
			{
				// should override #isDragEnterEventEnabled(), returning true, for this event to be triggered.
			}

			@Override
			public void onDragLeave(AjaxRequestTarget target, Component component)
			{
				// should override #isDragLeaveEventEnabled(), returning true, for this event to be triggered.
			}

			@Override
			public void onDrop(AjaxRequestTarget target, Component component)
			{
				if (component != null)
				{
					String message = String.format("%s dropped in %s", component.getDefaultModelObjectAsString(), this.getModelObject());
					console.info(target, message);
				}
			}
		};
	}
}
