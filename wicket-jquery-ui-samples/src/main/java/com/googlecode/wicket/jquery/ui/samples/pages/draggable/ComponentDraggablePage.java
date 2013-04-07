package com.googlecode.wicket.jquery.ui.samples.pages.draggable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import com.googlecode.wicket.jquery.ui.interaction.draggable.Draggable;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class ComponentDraggablePage extends AbstractDraggablePage
{
	private static final long serialVersionUID = 1L;

	public ComponentDraggablePage()
	{
		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		this.add(feedback.setOutputMarkupId(true));

		this.add(new Draggable<Void>("draggable") {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isStopEventEnabled()
			{
				return true;
			}

			@Override
			public void onDragStart(AjaxRequestTarget target)
			{
				info("Drag started");
				target.add(feedback);
			}

			@Override
			public void onDragStop(AjaxRequestTarget target)
			{
				info("Drag stoped");
				target.add(feedback);
			}
		});
	}
}
