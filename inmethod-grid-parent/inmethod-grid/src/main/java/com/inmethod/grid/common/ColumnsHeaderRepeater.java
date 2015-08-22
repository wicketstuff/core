package com.inmethod.grid.common;

import java.util.Collection;
import java.util.Iterator;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.IMarkupFragment;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.string.Strings;

import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.IGridSortState;

/**
 * Repeater that contains column header components.
 * 
 * @param <M>
 *            grid model object type
 * @param <I>
 *            row/item model object type
 * 
 * @author Matej Knopp
 */
public abstract class ColumnsHeaderRepeater<M, I, S> extends WebMarkupContainer
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param id
	 */
	public ColumnsHeaderRepeater(String id)
	{
		super(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onBeforeRender()
	{
		// make sure that the child components match the columns

		// remove unneeded components
		for (Iterator<?> i = iterator(); i.hasNext();)
		{
			Component component = (Component)i.next();
			if (isComponentNeeded(component.getId()) == false)
			{
				i.remove();
			}
		}

		// create components that might be needed
		for (IGridColumn<M, I, S> column : getActiveColumns())
		{
			String componentId = componentId(column.getId());

			if (get(componentId) == null)
			{ // if there is no component for given column, create it

				if (column.getSortProperty() == null)
				{	// for non sortable properties just add the component
					Component component = column.newHeader(componentId);
					if (component.getId().equals(componentId) == false)
					{
						throw new IllegalStateException("Invalid header component ID.");
					}
					add(component);
				}
				else
				{	// for sortable properties place the component inside SortableHeaderLinkPanel
					SortableHeaderLinkPanel<S> panel =
                  new SortableHeaderLinkPanel<S>(componentId, column.getSortProperty())
					{
						private static final long serialVersionUID = 1L;

						@Override
						protected void sortStateChanged(AjaxRequestTarget target)
						{
							ColumnsHeaderRepeater.this.sortStateChanged(target);
						}
					};
					Component component = column.newHeader(SortableHeaderLinkPanel.COMPONENT_ID);
					if (component.getId().equals(SortableHeaderLinkPanel.COMPONENT_ID) == false)
					{
						throw new IllegalStateException("Invalid header component ID.");
					}
					panel.add(component);
					add(panel);
				}
			}
      if (column.getWrapText() )
      {
        get(componentId)
          .add(new AttributeAppender("style",
                                     Model.of("white-space: normal;"), "; "));
      }
		}
		super.onBeforeRender();
	}

	abstract protected void sortStateChanged(AjaxRequestTarget target);

	private GridSortState<S> getSortState()
	{
		return findParent(AbstractGrid.class).getSortState();
	}

	/**
	 * Returns the sort direction for this panel.
	 * 
	 * @return
	 */
	private IGridSortState.Direction getSortDirection(IGridColumn<M, I, S> column)
	{
		IGridSortState<S> state = getSortState();
		// we are interested only in the column with highest priority and it must match this panel's
    // sort property
		if (state.getColumns().size() > 0 &&
			state.getColumns().get(0).getPropertyName().equals(column.getSortProperty()))
		{
			return state.getColumns().get(0).getDirection();
		}
		else
		{
			return null;
		}
	}

	@Override
	protected void onRender()
	{
		Response response = RequestCycle.get().getResponse();

		Collection<IGridColumn<M, I, S>> columns = getActiveColumns();

		for (IGridColumn<M, I, S> column : columns)
		{
			// render the table header opening tag with proper width
			response.write("<th style=\"width:");
			response.write("" + getColumnWidth(column));
			response.write(column.getSizeUnit().getValue());

			// render the css classes
			response.write("\" class=\"imxt-want-prelight");
			if (!Strings.isEmpty(column.getHeaderCssClass()))
			{
				response.write(" ");
				response.write(column.getHeaderCssClass());
			}
			if (getSortDirection(column) != null)
			{
				response.write(" imxt-sorted");
			}
			response.write("\">");

			// render the inner divs
			response.write("<div class=\"imxt-a\">");
			response.write("<div class=\"imxt-b\">");

			// render the actual component
			Component component = get(componentId(column.getId()));
			if (component == null)
			{
				throw new IllegalStateException("Column ID has changed during rendering");
			}
			IMarkupFragment componentMarkup = getParent().getParent().getMarkup();
			component.setMarkup(componentMarkup);
			component.render();

			// render closing tag
			response.write("</div>");

			// if resizable, render the handle
			if (column.isResizable())
			{
				response.write("<a class=\"imxt-handle\" href=\"#\" onclick=\"return false\"></a>");
			}
			response.write("</div></th>");
		}
	}

	/**
	 * Returns whether a component with given is needed for any of the columns.
	 * 
	 * @param componentId
	 * @return
	 */
	private boolean isComponentNeeded(final String componentId)
	{
		for (IGridColumn<M, I, S> column : getActiveColumns())
		{
			if (componentId(column.getId()).equals(componentId))
			{
				return true;
			}
		}
		return false;
	}

	private String componentId(String columnId)
	{
		return columnId.replace(".", "-");
	}

	abstract Collection<IGridColumn<M, I, S>> getActiveColumns();

	abstract int getColumnWidth(IGridColumn<M, I, S> column);
}