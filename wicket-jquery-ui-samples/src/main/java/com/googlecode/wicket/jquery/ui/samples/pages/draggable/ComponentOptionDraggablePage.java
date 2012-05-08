package com.googlecode.wicket.jquery.ui.samples.pages.draggable;

import java.util.Arrays;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import com.googlecode.wicket.jquery.ui.interaction.Draggable;
import com.googlecode.wicket.jquery.ui.interaction.Draggable.Containment;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class ComponentOptionDraggablePage extends AbstractDraggablePage
{
	private static final long serialVersionUID = 1L;
	
	public ComponentOptionDraggablePage()
	{
		// FeedbackPanel //
		final FeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedback");
		this.add(feedbackPanel.setOutputMarkupId(true));		
		
		// Draggable #1 //
		Draggable<Void> draggable1 = new Draggable<Void>("draggable1") {

			private static final long serialVersionUID = 1L;
			
			@Override
			protected boolean isStopEventEnabled()
			{
				return true;
			}

			@Override
			protected void onDragStart(AjaxRequestTarget target)
			{
				ComponentOptionDraggablePage.this.info(this, "started");
				target.add(feedbackPanel);
			}

			@Override
			protected void onDragStop(AjaxRequestTarget target)
			{
				ComponentOptionDraggablePage.this.info(this, "stopped");
				target.add(feedbackPanel);
			}			
		};
		
		this.add(draggable1.setContainment(Containment.Parent).setGrid(Arrays.asList(75, 35)));

		// Draggable #2 //
		Draggable<Void> draggable2 = new Draggable<Void>("draggable2") {

			private static final long serialVersionUID = 1L;
			
			@Override
			protected boolean isStopEventEnabled()
			{
				return true;
			}

			@Override
			protected void onDragStart(AjaxRequestTarget target)
			{
				ComponentOptionDraggablePage.this.info(this, "started");
				target.add(feedbackPanel);
			}

			@Override
			protected void onDragStop(AjaxRequestTarget target)
			{
				ComponentOptionDraggablePage.this.info(this, "stopped");
				target.add(feedbackPanel);
			}
		};
		
		this.add(draggable2.setContainment("#container"));
	}
	
	private void info(Component component, String event)
	{
		this.info(String.format("%s: drag %s", component.getMarkupId(), event));

	}
}
