package com.googlecode.wicket.jquery.ui.samples.pages.draggable;

import com.googlecode.wicket.jquery.ui.JQueryUIBehavior;

public class BehaviorOptionDraggablePage extends AbstractDraggablePage
{
	private static final long serialVersionUID = 1L;

	public BehaviorOptionDraggablePage()
	{
		this.add(new JQueryUIBehavior("#draggable", "draggable").setOption("axis", "'x'").setOption("containment", "'#wrapper-panel-frame'"));
	}
}
