package com.googlecode.wicket.jquery.ui.samples.pages.draggable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import com.googlecode.wicket.jquery.ui.interaction.Draggable;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class ComponentDraggablePage extends AbstractDraggablePage
{
	private static final long serialVersionUID = 1L;
	
	public ComponentDraggablePage()
	{
		// FeedbackPanel //
		final FeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedback");
		this.add(feedbackPanel.setOutputMarkupId(true));		
		
		this.add(new Draggable<Void>("draggable") {

			private static final long serialVersionUID = 1L;
			
			@Override
			protected boolean isStopEventEnabled()
			{
				return true;
			}
			
			@Override
			protected void onDragStart(AjaxRequestTarget target)
			{
				info("Drag started");
				target.add(feedbackPanel);
			}
			
			@Override
			protected void onDragStop(AjaxRequestTarget target)
			{
				info("Drag stoped");
				target.add(feedbackPanel);
			}
		});
	}
}
