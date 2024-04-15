package com.googlecode.wicket.jquery.ui.samples.jqueryui.droppable;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.resource.StyleSheetPackageHeaderItem;
import com.googlecode.wicket.jquery.ui.interaction.draggable.Draggable;
import com.googlecode.wicket.jquery.ui.interaction.droppable.Droppable;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class DefaultDroppablePage extends AbstractDroppablePage
{
	private static final long serialVersionUID = 1L;

	/**
	 * Gets a new Draggable
	 * By default 'stop' event is disabled to minimize client/server round-trips.
	 */
	private static Draggable<String> newDraggable(String id, String label)
	{
		return new Draggable<String>(id, Model.of(label)).setContainment("#wrapper-panel-frame");
	}

	private final FeedbackPanel feedback;

	public DefaultDroppablePage()
	{
		// FeedbackPanel //
		this.feedback = new JQueryFeedbackPanel("feedback");
		this.add(this.feedback.setOutputMarkupId(true));

		this.add(this.newDroppable("droppable1", "green area"));
		this.add(this.newDroppable("droppable2", "blue area"));

		this.add(newDraggable("draggable1", "Draggable #1"));
		this.add(newDraggable("draggable2", "Draggable #2"));
	}

	// Methods //

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(DefaultDroppablePage.class));
	}

	// Factories //

	/**
	 * Gets a new Droppable.
	 * By default 'over' and 'exit' ('out') events are disabled to minimize client/server round-trips.
	 */
	private Droppable<String> newDroppable(String id, String color)
	{
		return new Droppable<String>(id, Model.of(color)) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onDrop(AjaxRequestTarget target, Component component)
			{
				if (component != null)
				{
					info(String.format("%s dropped in %s", component.getDefaultModelObjectAsString(), this.getDefaultModelObjectAsString()));
				}

				target.add(feedback);
			}

			@Override
			public void onOver(AjaxRequestTarget target, Component component)
			{
				// should override #isOverEventEnabled(), returning true, for this event to be triggered.
			}

			@Override
			public void onExit(AjaxRequestTarget target, Component component)
			{
				// should override #isExitEventEnabled(), returning true, for this event to be triggered.
			}
		};
	}
}
