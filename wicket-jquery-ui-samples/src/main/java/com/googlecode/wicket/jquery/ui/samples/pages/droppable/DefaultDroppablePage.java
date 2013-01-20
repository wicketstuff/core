package com.googlecode.wicket.jquery.ui.samples.pages.droppable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.interaction.Draggable;
import com.googlecode.wicket.jquery.ui.interaction.Droppable;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class DefaultDroppablePage extends AbstractDroppablePage
{
	private static final long serialVersionUID = 1L;
	final FeedbackPanel feedback;
	
	public DefaultDroppablePage()
	{
		// FeedbackPanel //
		this.feedback = new JQueryFeedbackPanel("feedback");
		this.add(this.feedback.setOutputMarkupId(true));
		
		this.add(this.newDroppable("droppable1", "green area"));
		this.add(this.newDroppable("droppable2", "blue area"));

		this.add(this.newDraggable("draggable1", "Draggable #1"));
		this.add(this.newDraggable("draggable2", "Draggable #2"));
	}
	
	/**
	 * Gets a new Draggable
	 * By default 'stop' event is disabled to minimize client/server round-trips.
	 */
	private Draggable<String> newDraggable(String id, String label)
	{
		return new Draggable<String>(id, new Model<String>(label)).setContainment("#wrapper-panel-frame");
	}

	/**
	 * Gets a new Droppable.
	 * By default 'over' and 'exit' ('out') events are disabled to minimize client/server round-trips.
	 */
	private Droppable<String> newDroppable(String id, String color)
	{
		return new Droppable<String>(id, new Model<String>(color)) {
	
			private static final long serialVersionUID = 1L;

			@Override
			protected void onDrop(AjaxRequestTarget target, Draggable<?> draggable)
			{
				if (draggable != null)
				{
					info(String.format("%s dropped in %s", draggable.getDefaultModelObjectAsString(), this.getDefaultModelObjectAsString()));
				}

				target.add(feedback);
			}

			@Override
			protected void onOver(AjaxRequestTarget target, Draggable<?> draggable)
			{
				// should override #isOverEventEnabled(), returning true, for this event to be triggered.
			}

			@Override
			protected void onExit(AjaxRequestTarget target, Draggable<?> draggable)
			{
				// should override #isExitEventEnabled(), returning true, for this event to be triggered.
			}
		};
	}
}
