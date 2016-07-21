package org.wicketstuff.yui.markup.html.sortable;

import org.apache.wicket.markup.html.WebMarkupContainer;

public class SortableItem extends WebMarkupContainer
{
	private static final long serialVersionUID = 1L;

	public SortableItem(String id)
	{
		super(id);
		add(new Draggable());
	}

}
