package com.googlecode.wicket.jquery.ui.samples.pages.draggable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;

import com.googlecode.wicket.jquery.ui.interaction.draggable.DraggableBehavior;

public class BehaviorOptionDraggablePage extends AbstractDraggablePage
{
	private static final long serialVersionUID = 1L;

	public BehaviorOptionDraggablePage()
	{
		DraggableBehavior behavior = this.newDraggableBehavior();
		behavior.setOption("axis", "'x'");
		behavior.setOption("containment", "'#wrapper-panel-frame'");

		WebMarkupContainer container = new WebMarkupContainer("draggable");
		container.add(behavior);

		this.add(container);
	}

	private DraggableBehavior newDraggableBehavior()
	{
		return new DraggableBehavior() {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isStopEventEnabled()
			{
				// not enabled to prevent unnecessary server round-trips.
				return false;
			}

			@Override
			public void onDragStart(AjaxRequestTarget target, int top, int left)
			{
				// noop
			}

			@Override
			public void onDragStop(AjaxRequestTarget target, int top, int left)
			{
				// noop
			}
		};
	}
}
