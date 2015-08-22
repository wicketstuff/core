package org.wicketstuff.yui.markup.html.sortable;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;

public abstract class SortableList extends WebMarkupContainer
{
	private static final long serialVersionUID = 1L;

	public SortableList(String id)
	{
		super(id);
		add(new Droppable()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onDrop(AjaxRequestTarget target, Component draggable, int index)
			{
				SortableList.this.onDrop(target, draggable, index);
			}

		});
	}

	protected abstract void onDrop(AjaxRequestTarget target, Component draggable, int index);
}
