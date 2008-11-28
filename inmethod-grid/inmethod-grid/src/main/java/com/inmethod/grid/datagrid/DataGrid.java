package com.inmethod.grid.datagrid;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.inmethod.grid.IDataSource;
import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.IGridSortState;
import com.inmethod.grid.common.AbstractGrid;
import com.inmethod.grid.common.AbstractPageableView;

/**
 * Advanced grid component. Supports resizable and reorderable columns.
 * 
 * @author Matej Knopp
 */
public class DataGrid extends AbstractGrid implements IPageable {

	private static final long serialVersionUID = 1L;

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
	public DataGrid(String id, IModel model, List<IGridColumn> columns) {
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
	public DataGrid(String id, IDataSource dataSource, List<IGridColumn> columns) {
		this(id, new Model(dataSource), columns);
	}

	private class Body extends DataGridBody {

		private static final long serialVersionUID = 1L;

		private Body(String id) {
			super(id);
		}

		@Override
		protected Collection<IGridColumn> getActiveColumns() {
			return DataGrid.this.getActiveColumns();
		}

		@Override
		protected IDataSource getDataSource() {
			return DataGrid.this.getDataSource();
		}

		@Override
		protected int getRowsPerPage() {
			return DataGrid.this.getRowsPerPage();
		}

		@Override
		protected IGridSortState getSortState() {
			return DataGrid.this.getSortState();
		}

		@Override
		protected boolean isItemSelected(IModel itemModel) {
			return DataGrid.this.isItemSelected(itemModel);
		}

		@Override
		protected void rowPopulated(WebMarkupContainer rowItem) {
			DataGrid.this.onRowPopulated(rowItem);
		}

	};

	/**
	 * Returns the {@link IDataSource} instance this data grid uses to fetch the data.
	 * 
	 * @return {@link IDataSource} instance
	 */
	public IDataSource getDataSource() {
		return ((IDataSource) getDefaultModelObject());
	}

	private int rowsPerPage = 20;

	/**
	 * Sets the desired amount rows per page.
	 * 
	 * @param rowsPerPage
	 *            how many rows (max) should be displayed on one page
	 * @return <code>this</code> (useful for method chaining)
	 */
	public DataGrid setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
		return this;
	}

	/**
	 * Returns the maximal amount of rows shown on one page.
	 * 
	 * @return count of rows per page
	 */
	public int getRowsPerPage() {
		return rowsPerPage;
	}

	private void init() {
		((WebMarkupContainer) get("form:bodyContainer")).add(new Body("body"));
	};

	private Body getBody() {
		return (Body) get("form:bodyContainer:body");
	}

	/**
	 * Returns the total count of items (sum of count of items on all pages) or
	 * {@link AbstractPageableView#UNKOWN_COUNT} in case the count can't be determined.
	 * 
	 * @return total count of items or {@value AbstractPageableView#UNKOWN_COUNT}
	 */
	public int getTotalRowCount() {
		return getBody().getTotalRowCount();
	}

	/**
	 * @return The current page that is or will be rendered.
	 */
	public int getCurrentPage() {
		return getBody().getCurrentPage();
	}

	/**
	 * Gets the total number of pages this pageable object has.
	 * 
	 * @return The total number of pages this pageable object has
	 */
	public int getPageCount() {
		return getBody().getPageCount();
	}

	/**
	 * Sets the a page that should be rendered.
	 * 
	 * @param page
	 *            The page that should be rendered.
	 */
	public void setCurrentPage(int page) {
		if (getBody().getCurrentPage() != page) {
			getBody().setCurrentPage(page);
			if (isCleanSelectionOnPageChange()) {
				resetSelectedItems();
			}
		}
	}

	/**
	 * @return the amount of items on current page.
	 */
	public int getCurrentPageItemCount() {
		return getBody().getCurrentPageItemCount();
	}

	private final Set<IModel> selectedItems = new HashSet<IModel>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<IModel> getSelectedItems() {
		return Collections.unmodifiableSet(selectedItems);
	}

	private boolean allowSelectMultiple = true;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAllowSelectMultiple() {
		return allowSelectMultiple;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAllowSelectMultiple(boolean value) {
		this.allowSelectMultiple = value;
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
	public DataGrid setCleanSelectionOnPageChange(boolean cleanSelectionOnPageChange) {
		this.cleanSelectionOnPageChange = cleanSelectionOnPageChange;
		return this;
	}

	/**
	 * @return whether the current page change cleans the selection
	 */
	public boolean isCleanSelectionOnPageChange() {
		return cleanSelectionOnPageChange;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isItemSelected(IModel itemModel) {
		return selectedItems.contains(itemModel);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resetSelectedItems() {
		markAllItemsDirty();		
		Set<IModel> oldSelected = new HashSet<IModel>(selectedItems);
		selectedItems.clear();
		for (IModel model : oldSelected) {
			onItemSelectionChanged(model, false);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void selectAllVisibleItems() {
		WebMarkupContainer body = (WebMarkupContainer) get("form:bodyContainer:body:row");
		if (body != null) {
			for (Iterator<?> i = body.iterator(); i.hasNext();) {
				Component component = (Component) i.next();
				IModel model = component.getDefaultModel();
				selectItem(model, true);
			}
		}
		markAllItemsDirty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected WebMarkupContainer findRowComponent(IModel rowModel) {
		if (rowModel == null) {
			throw new IllegalArgumentException("rowModel may not be null");
		}
		WebMarkupContainer body = (WebMarkupContainer) get("form:bodyContainer:body:row");
		if (body != null) {
			for (Iterator<?> i = body.iterator(); i.hasNext();) {
				Component component = (Component) i.next();
				IModel model = component.getDefaultModel();
				if (rowModel.equals(model)) {
					return (WebMarkupContainer) component;
				}
			}
		}			
		return null;
	}
	
	private transient Set<IModel> dirtyItems = null;

	private static final Set<IModel> DIRTY_ALL = new HashSet<IModel>();

	@Override
	protected void onBeforeRender() {
		super.onBeforeRender();
		dirtyItems = null;
	}

	/**
	 * Marks the item from the given model as dirty. Dirty items are updated during Ajax requests
	 * when {@link AbstractGrid#update()} method is called.
	 * 
	 * @param itemModel
	 *            model used to access the item
	 */
	public void markItemDirty(IModel itemModel) {
		if (dirtyItems != DIRTY_ALL) {
			if (this.dirtyItems == null) {
				this.dirtyItems = new HashSet<IModel>();
			}
			this.dirtyItems.add(itemModel);
		}
	}

	/**
	 * Makes the next call to {@link #update()} refresh the entire grid.
	 */
	public void markAllItemsDirty() {
		dirtyItems = DIRTY_ALL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onItemSelectionChanged(IModel item, boolean newValue) {
		markItemDirty(item);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update() {
		AjaxRequestTarget target = AjaxRequestTarget.get();
		if (dirtyItems == DIRTY_ALL) {
			target.addComponent(this);
		} else if (dirtyItems != null) {
			WebMarkupContainer body = (WebMarkupContainer) get("form:bodyContainer:body:row");
			if (body != null) {
				for (Iterator<?> i = body.iterator(); i.hasNext();) {
					Component component = (Component) i.next();
					IModel model = component.getDefaultModel();
					if (dirtyItems.contains(model)) {
						target.addComponent(component);
					}
				}
			}
		}
		dirtyItems = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void selectItem(IModel itemModel, boolean selected) {
		if (isAllowSelectMultiple() == false && selectedItems.size() > 0) {
			for (Iterator<IModel> i = selectedItems.iterator(); i.hasNext();) {
				IModel current = i.next();
				if (current.equals(itemModel) == false) {
					i.remove();
					onItemSelectionChanged(current, false);
				}
			}
		}

		if (selected == true && selectedItems.contains(itemModel) == false) {
			selectedItems.add(itemModel);
			onItemSelectionChanged(itemModel, selected);
		} else if (selected == false && selectedItems.contains(itemModel) == true) {
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
		public DataGrid getDataGrid();
	};
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebMarkupContainer findParentRow(Component child) {	
		return (WebMarkupContainer) child.findParent(DataGridBody.Data.RowItem.class);
	}
}
