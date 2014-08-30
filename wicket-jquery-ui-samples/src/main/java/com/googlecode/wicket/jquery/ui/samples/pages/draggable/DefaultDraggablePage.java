package com.googlecode.wicket.jquery.ui.samples.pages.draggable;

import com.googlecode.wicket.jquery.ui.JQueryUIBehavior;

public class DefaultDraggablePage extends AbstractDraggablePage
{
	private static final long serialVersionUID = 1L;

	public DefaultDraggablePage()
	{
		this.add(new JQueryUIBehavior("#draggable", "draggable"));
	}
}
