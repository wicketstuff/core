package com.googlecode.wicket.jquery.ui.samples.kendoui.dragdrop;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.KendoSamplePage;

abstract class AbstractDragDropPage extends KendoSamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(BehaviorDragDropPage.class, "Draggable & Droppable Behaviors"), // lf
				new DemoLink(ComponentDragDropPage.class, "Draggable & Droppable Components") // lf
		);
	}
}
