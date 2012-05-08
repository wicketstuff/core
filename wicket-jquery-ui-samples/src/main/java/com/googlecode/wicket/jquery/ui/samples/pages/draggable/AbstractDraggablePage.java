package com.googlecode.wicket.jquery.ui.samples.pages.draggable;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;



abstract class AbstractDraggablePage extends SamplePage
{
	private static final long serialVersionUID = 1L;
	
	public AbstractDraggablePage()
	{
		
	}
	
	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(DefaultDraggablePage.class, "Draggable Behavior"),
				new DemoLink(BehaviorOptionDraggablePage.class, "Draggable Behavior, with options"),
				new DemoLink(ComponentDraggablePage.class, "Draggable Component"),
				new DemoLink(ComponentOptionDraggablePage.class, "Draggable Component, with options")
			);
	}
}
