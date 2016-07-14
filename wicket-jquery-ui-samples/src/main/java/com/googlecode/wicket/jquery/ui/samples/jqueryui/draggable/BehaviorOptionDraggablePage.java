package com.googlecode.wicket.jquery.ui.samples.jqueryui.draggable;

import org.apache.wicket.markup.html.WebMarkupContainer;

import com.googlecode.wicket.jquery.ui.interaction.draggable.DraggableAdapter;
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

	// Factories //

	private DraggableBehavior newDraggableBehavior()
	{
		return new DraggableBehavior(new DraggableAdapter());
	}
}
