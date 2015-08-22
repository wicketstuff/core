package com.inmethod.grid.common;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.panel.Panel;

import com.inmethod.grid.IGridSortState;

/**
 * Component that wraps it's child component with link that changes sort state of a grid column.
 * 
 * @author Matej Knopp
 */
public abstract class SortableHeaderLinkPanel<S> extends Panel
{

	private static final long serialVersionUID = 1L;
	private final S sortProperty;

	/**
	 * Creates new {@link SortableHeaderLinkPanel} instance.
	 * 
	 * @param id
	 *            component id
	 * @param sortProperty
	 *            sort property that will be reported by the {@link IGridSortState}
	 */
	public SortableHeaderLinkPanel(String id, S sortProperty)
	{
		super(id);

		this.sortProperty = sortProperty;

		add(new AjaxEventBehavior("click")
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected void onEvent(AjaxRequestTarget target)
			{
				// change the direction
				IGridSortState.Direction direction = getSortDirection();

				if (direction == IGridSortState.Direction.ASC)
				{
					// if the direction is already ascending, switch it to descending
					getSortState().setSortState(SortableHeaderLinkPanel.this.sortProperty,
						IGridSortState.Direction.DESC);
				}
				else
				{
					// otherwise set direction to ascending
					getSortState().setSortState(SortableHeaderLinkPanel.this.sortProperty,
						IGridSortState.Direction.ASC);
				}

				sortStateChanged(target);
			}
		});
	}

	abstract protected void sortStateChanged(AjaxRequestTarget target);

	@Override
	protected void onComponentTag(ComponentTag tag)
	{
		super.onComponentTag(tag);
		tag.setName("a");
		tag.put("href", "#");
		tag.put("class", getStyleClass());
	}

	private GridSortState<S> getSortState()
	{
		return (findParent(AbstractGrid.class)).getSortState();
	}

	/**
	 * Returns the sort direction for this panel.
	 * 
	 * @return
	 */
	private IGridSortState.Direction getSortDirection()
	{
		IGridSortState<S> state = getSortState();
		// we are interested only in the column with highest priority and it must match this panel's
// sort property
		if (state.getColumns().size() > 0 &&
			state.getColumns().get(0).getPropertyName().equals(sortProperty))
		{
			return state.getColumns().get(0).getDirection();
		}
		else
		{
			return null;
		}
	}

	/**
	 * Returns the CSS class for the link element depending on the sort state.
	 * 
	 * @return
	 */
	private String getStyleClass()
	{
		IGridSortState.Direction direction = getSortDirection();
		if (direction == IGridSortState.Direction.ASC)
		{
			return "imxt-sort-header imxt-sort-header-asc";
		}
		else if (direction == IGridSortState.Direction.DESC)
		{
			return "imxt-sort-header imxt-sort-header-desc";
		}
		else
		{
			return "imxt-sort-header imxt-sort-header-none";
		}
	};

	/**
	 * INTERNAL
	 */
	public static final String COMPONENT_ID = "header";
}
