package com.inmethod.grid.datagrid;

import java.util.*;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;

import com.inmethod.grid.IAppendableDataSource;
import com.inmethod.grid.IDataSource;
import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.IGridSortState;
import com.inmethod.grid.common.AbstractGrid;
import com.inmethod.grid.common.AbstractPageableView;

/**
 * Advanced grid component. Supports resizable and reorderable columns.
 * 
 * @param <D>
 *            datasource model object type = grid type
 * @param <T>
 *            row/item model object type
 * 
 * @author Matej Knopp
 */
public class DataGrid<D extends IDataSource<T>, T, S> extends AbstractGrid<D, T, S>
       implements IPageable
{
	private static final long serialVersionUID = 1L;
  //private static final Logger log = LoggerFactory.getLogger(DataGrid.class);

	/**
	 * Crates a new {@link DataGrid} instance.
	 * 
	 * @param id
	 *            component id
	 * @param model
	 *            model to access the {@link IDataSource} instance used to fetch the data
	 * @param columns
	 *            list of grid columns
	 */
	public DataGrid(String id, IModel<D> model, List<IGridColumn<D, T, S>> columns)
	{
		super(id, model, columns);
		init();
	}

	/**
	 * Crates a new {@link DataGrid} instance.
	 * 
	 * @param id
	 *            component id
	 * @param dataSource
	 *            data source used to fetch the data
	 * @param columns
	 *            list of grid columns
	 */
	public DataGrid(String id, D dataSource, List<IGridColumn<D, T, S>> columns)
	{
		this(id, Model.of(dataSource), columns);
	}

	private class Body extends DataGridBody<D, T, S>
	{
		private static final long serialVersionUID = 1L;

		private Body(String id)
		{
			super(id);
		}

		@Override
		protected Collection<IGridColumn<D, T, S>> getActiveColumns()
		{
			return DataGrid.this.getActiveColumns();
		}

		@Override
		protected D getDataSource()
		{
			return DataGrid.this.getDataSource();
		}

		@Override
		protected long getRowsPerPage()
		{
			return DataGrid.this.getRowsPerPage();
		}

		@Override
		protected IGridSortState<S> getSortState()
		{
			return DataGrid.this.getSortState();
		}

		@Override
		protected boolean isItemSelected(IModel<T> itemModel)
		{
			return DataGrid.this.isItemSelected(itemModel);
		}

		@Override
		protected void rowPopulated(WebMarkupContainer rowItem)
		{
			onRowPopulated(rowItem);
		}

	}

	/**
	 * Returns the {@link IDataSource} instance this data grid uses to fetch the data.
	 * 
	 * @return {@link IDataSource} instance
	 */
	public D getDataSource()
	{
		return (D)getDefaultModelObject();
	}

	private long rowsPerPage = 20;

	/**
	 * Sets the desired amount rows per page.
	 * 
	 * @param rowsPerPage
	 *            how many rows (max) should be displayed on one page
	 * @return <code>this</code> (useful for method chaining)
	 */
	public DataGrid<D, T, S> setRowsPerPage(int rowsPerPage)
	{
		this.rowsPerPage = rowsPerPage;
		return this;
	}

	/**
	 * Returns the maximal amount of rows shown on one page.
	 * 
	 * @return count of rows per page
	 */
	public long getRowsPerPage()
	{
		return rowsPerPage;
	}

	private void init()
	{
		((WebMarkupContainer)get("form:bodyContainer")).add(new Body("body"));
  }

	private Body getBody()
	{
		return (Body)get("form:bodyContainer:body");
	}

	/**
	 * Returns the total count of items (sum of count of items on all pages) or
	 * {@link AbstractPageableView#UNKNOWN_COUNT} in case the count can't be determined.
	 * 
	 * @return total count of items or {@value AbstractPageableView#UNKNOWN_COUNT}
	 */
	public long getTotalRowCount()
	{
		return getBody().getTotalRowCount();
	}

	/**
	 * @return The current page that is or will be rendered.
	 */
	public long getCurrentPage()
	{
		return getBody().getCurrentPage();
	}

	/**
	 * Gets the total number of pages this pageable object has.
	 * 
	 * @return The total number of pages this pageable object has
	 */
	public long getPageCount()
	{
		return getBody().getPageCount();
	}

	/**
	 * Sets the a page that should be rendered.
	 * 
	 * @param page
	 *            The page that should be rendered.
	 */
	public void setCurrentPage(long page)
	{
		if (getBody().getCurrentPage() != page)
		{
			getBody().setCurrentPage(page);
			if (isCleanSelectionOnPageChange())
			{
				resetSelectedItems();
			}
		}
	}

	/**
	 * @return the amount of items on current page.
	 */
	public long getCurrentPageItemCount()
	{
		return getBody().getCurrentPageItemCount();
	}

	private final Set<IModel<T>> selectedItems = new HashSet<IModel<T>>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<IModel<T>> getSelectedItems()
	{
		return Collections.unmodifiableSet(selectedItems);
	}

	private boolean allowSelectMultiple = true;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAllowSelectMultiple()
	{
		return allowSelectMultiple;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAllowSelectMultiple(boolean value)
	{
		allowSelectMultiple = value;
	}

	private boolean cleanSelectionOnPageChange = true;

	/**
	 * Sets whether the change of current page should clear all selected items. This means that only
	 * items on current page can be selected, i.e. items from multiple pages can not be selected at
	 * the same time.
	 * 
	 * @param cleanSelectionOnPageChange
	 *            whether the current page change should deselect all selected items
	 * @return <code>this</code> (useful for method chaining)
	 */
	public DataGrid<D, T, S> setCleanSelectionOnPageChange(boolean cleanSelectionOnPageChange)
	{
		this.cleanSelectionOnPageChange = cleanSelectionOnPageChange;
		return this;
	}

	/**
	 * @return whether the current page change cleans the selection
	 */
	public boolean isCleanSelectionOnPageChange()
	{
		return cleanSelectionOnPageChange;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isItemSelected(IModel<T> itemModel)
	{
		return selectedItems.contains(itemModel);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resetSelectedItems()
	{
		markAllItemsDirty();
		Set<IModel<T>> oldSelected = new HashSet<IModel<T>>(selectedItems);
		selectedItems.clear();
		for (IModel<T> model : oldSelected)
		{
			onItemSelectionChanged(model, false);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void selectAllVisibleItems()
	{
		WebMarkupContainer body = (WebMarkupContainer)get("form:bodyContainer:body:row");
		if (body != null)
		{
			for (Component component : body)
			{
				IModel<T> model = (IModel<T>)component.getDefaultModel();
				selectItem(model, true);
			}
		}
		markAllItemsDirty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected WebMarkupContainer findRowComponent(IModel<T> rowModel)
	{
		if (rowModel == null)
		{
			throw new IllegalArgumentException("rowModel may not be null");
		}
		WebMarkupContainer body = (WebMarkupContainer)get("form:bodyContainer:body:row");
		if (body != null)
		{
			for (Component component : body)
			{
				IModel<T> model = (IModel<T>)component.getDefaultModel();
				if (rowModel.equals(model))
				{
					return (WebMarkupContainer)component;
				}
			}
		}
		return null;
	}

	private transient Set<IModel<T>> dirtyItems = null;

	private transient boolean allDirty = false;

	@Override
	protected void onBeforeRender()
	{
		super.onBeforeRender();
		dirtyItems = null;
		allDirty = false;
	}

	/**
	 * Marks the item from the given model as dirty. Dirty items are updated during Ajax requests
	 * when {@link AbstractGrid#update()} method is called.
	 * 
	 * @param itemModel
	 *            model used to access the item
	 */
	@Override
	public void markItemDirty(IModel<T> itemModel)
	{
		if (!allDirty)
		{
			if (dirtyItems == null)
			{
				dirtyItems = new HashSet<IModel<T>>();
			}
			dirtyItems.add(itemModel);
		}
	}

	/**
	 * Makes the next call to {@link #update()} refresh the entire grid.
	 */
	public void markAllItemsDirty()
	{
		allDirty = true;
		dirtyItems = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onItemSelectionChanged(IModel<T> item, boolean newValue)
	{
		markItemDirty(item);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update()
	{
		AjaxRequestTarget target = getRequestCycle().find(AjaxRequestTarget.class).get();
		if (allDirty)
		{
			target.add(this);
		}
		else if (dirtyItems != null)
		{
			WebMarkupContainer body = (WebMarkupContainer)get("form:bodyContainer:body:row");
			if (body != null)
			{
				for (Component component : body)
				{
					IModel<T> model = (IModel<T>)component.getDefaultModel();
					if (dirtyItems.contains(model))
					{
						target.add(component);
					}
				}
			}
		}
		dirtyItems = null;
	}
	
	/**
  * Insert the rowData into the grid
  *
  * @param rowData data to insert into the new row
  * @return Item inserted Item
  */
  public Item<T> insertRow(final T rowData)
  {
     IAppendableDataSource<T> ADS;
     try { ADS = ((IAppendableDataSource<T>)getDataSource()); }
     catch (ClassCastException cce)
     { //TODO: localize this string
        //log.error( "Error BAD Data Source type. "
        //         + "IAppendableDataSource REQUIRED for addition");
        throw new WicketRuntimeException("Error BAD Data Source type. "
                  + "IAppendableDataSource REQUIRED for addition", cce);
     }
     ADS.insertRow(getCurrentPageItemCount(), rowData);
     Item<T> item = getBody().createItem(getCurrentPageItemCount(),
                                      getDataSource().model(rowData));

     //make sure the datagrid knows the rows need to be refreshed
     getBody().clearCache(); //clears the cache, to make sure the data is reloaded

     //Commented out because the list updates but is not editable after.
     //both of these functions are "cached"
     //markAllItemsDirty();
     //update();
                               
     Optional<AjaxRequestTarget> targetOptional = RequestCycle.get().find(AjaxRequestTarget.class);
     targetOptional.ifPresent(target -> target.add(this.getParent()));
     return item;
  }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void selectItem(IModel<T> itemModel, boolean selected)
	{
		if (isAllowSelectMultiple() == false && selectedItems.size() > 0)
		{
			for (Iterator<IModel<T>> i = selectedItems.iterator(); i.hasNext();)
			{
				IModel<T> current = i.next();
				if (current.equals(itemModel) == false)
				{
					i.remove();
					onItemSelectionChanged(current, false);
				}
			}
		}

		if (selected && !selectedItems.contains(itemModel))
		{
			selectedItems.add(itemModel);
			onItemSelectionChanged(itemModel, selected);
		}
		else if (!selected && selectedItems.contains(itemModel))
		{
			selectedItems.remove(itemModel);
			onItemSelectionChanged(itemModel, selected);
		}
	}

	/**
	 * Extended query interface that makes it possible to obtain the {@link DataGrid} instance.
	 * 
	 * @author Matej Knopp
	 */
	public interface IGridQuery extends IDataSource.IQuery {
		
		/**
		 * @return data grid issuing the query
		 */
		public DataGrid<?, ?, ?> getDataGrid();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebMarkupContainer findParentRow(Component child)
	{
		return child.findParent(DataGridBody.Data.RowItem.class);
	}
}
