package com.googlecode.wicket.jquery.ui.samples.jqueryui.draggable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
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

		// Draggable //
		this.add(new Draggable<Void>("draggable") {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isStopEventEnabled()
			{
				return true;
			}

			@Override
			public void onDragStart(AjaxRequestTarget target, int top, int left)
			{
				this.info(String.format("Drag started - position: {%s, %s}", top, left));

				target.add(feedback);
			}

			@Override
			public void onDragStop(AjaxRequestTarget target, int top, int left)
			{
				double offsetTop = RequestCycleUtils.getQueryParameterValue("offsetTop").toDouble(-1);
				double offsetLeft = RequestCycleUtils.getQueryParameterValue("offsetLeft").toDouble(-1);

				this.info(String.format("Drag stoped - position: {%d, %d}, offset: {%.1f, %.1f}", top, left, offsetTop, offsetLeft));

				target.add(feedback);
			}
		});
	}
}
