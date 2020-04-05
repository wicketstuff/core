package com.googlecode.wicket.jquery.ui.samples.jqueryui.draggable;

import java.util.Arrays;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import com.googlecode.wicket.jquery.core.resource.StyleSheetPackageHeaderItem;
import com.googlecode.wicket.jquery.ui.interaction.draggable.Draggable;
import com.googlecode.wicket.jquery.ui.interaction.draggable.Draggable.Containment;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class ComponentOptionDraggablePage extends AbstractDraggablePage
{
	private static final long serialVersionUID = 1L;

	public ComponentOptionDraggablePage()
	{
		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		this.add(feedback.setOutputMarkupId(true));

		// Draggable #1 //
		Draggable<Void> draggable1 = new Draggable<Void>("draggable1") {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isStopEventEnabled()
			{
				return true;
			}

			@Override
			public void onDragStart(AjaxRequestTarget target, int top, int left)
			{
				ComponentOptionDraggablePage.this.info(this, "started");
				target.add(feedback);
			}

			@Override
			public void onDragStop(AjaxRequestTarget target, int top, int left)
			{
				ComponentOptionDraggablePage.this.info(this, "stopped");
				target.add(feedback);
			}
		};

		this.add(draggable1.setContainment(Containment.Parent).setGrid(Arrays.asList(75, 35)));

		// Draggable #2 //
		Draggable<Void> draggable2 = new Draggable<Void>("draggable2") {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isStopEventEnabled()
			{
				return true;
			}

			@Override
			public void onDragStart(AjaxRequestTarget target, int top, int left)
			{
				ComponentOptionDraggablePage.this.info(this, "started");
				target.add(feedback);
			}

			@Override
			public void onDragStop(AjaxRequestTarget target, int top, int left)
			{
				ComponentOptionDraggablePage.this.info(this, "stopped");
				target.add(feedback);
			}
		};

		this.add(draggable2.setContainment("#container"));
	}

	// Methods //

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(ComponentOptionDraggablePage.class));
	}

	private void info(Component component, String event)
	{
		this.info(String.format("%s: drag %s", component.getMarkupId(), event));

	}
}
