package com.googlecode.wicket.jquery.ui.samples.pages.draggable;

import com.googlecode.wicket.jquery.core.JQueryBehavior;

public class DefaultDraggablePage extends AbstractDraggablePage
{
	private static final long serialVersionUID = 1L;

	public DefaultDraggablePage()
	{
		this.add(new JQueryBehavior("#draggable", "draggable"));
	}
}
