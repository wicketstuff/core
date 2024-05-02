package com.googlecode.wicket.jquery.ui.samples.jqueryui.draggable;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.JQuerySamplePage;

abstract class AbstractDraggablePage extends JQuerySamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(DefaultDraggablePage.class, "Draggable Behavior"), // lf
				new DemoLink(BehaviorOptionDraggablePage.class, "Draggable Behavior, with options"), // lf
				new DemoLink(ComponentDraggablePage.class, "Draggable Component"), // lf
				new DemoLink(ComponentOptionDraggablePage.class, "Draggable Component, with options") // lf
		);
	}
}
