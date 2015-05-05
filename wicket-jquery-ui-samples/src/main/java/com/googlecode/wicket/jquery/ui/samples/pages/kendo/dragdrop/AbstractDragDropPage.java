package com.googlecode.wicket.jquery.ui.samples.pages.kendo.dragdrop;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;



abstract class AbstractDragDropPage extends SamplePage
{
	private static final long serialVersionUID = 1L;
	
	public AbstractDragDropPage()
	{
		
	}
	
	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(BehaviorDragDropPage.class, "Draggable & Droppable Behaviors"),
				new DemoLink(ComponentDragDropPage.class, "Draggable & Droppable Components")
			);
	}
}
