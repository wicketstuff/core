package com.inmethod.grid.datagrid;

import java.util.Collection;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.model.IModel;

import com.inmethod.grid.IDataSource;
import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.IGridSortState;
import com.inmethod.grid.common.AbstractGridRow;
import com.inmethod.grid.common.AbstractPageableView;
import com.inmethod.grid.common.AttachPrelightBehavior;

/**
 * Contains data grid rows.
 * 
 * @param <D>
 *            datasource model object type = grid type
 * @param <T>
 *            row/item model object type
 * 
 * @author Matej Knopp
 */
public abstract class DataGridBody<D extends IDataSource<T>, T> extends Panel
       implements IPageable
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param id
	 *            component id
	 */
	public DataGridBody(String id)
	{
		super(id);
		setRenderBodyOnly(true);

		add(new Data("row"));
	}

	protected abstract D getDataSource();

	protected abstract int getRowsPerPage();

	protected abstract IGridSortState getSortState();

	protected abstract Collection<IGridColumn<D, T>> getActiveColumns();

	protected abstract boolean isItemSelected(IModel<T> itemModel);

	protected abstract void rowPopulated(WebMarkupContainer rowItem);

	private Data getData()
	{
		return (Data)get("row");
	}
  
  protected Item insertRow(final IModel<T> rowModel) 
  {
		Item item = getData().createItem(getCurrentPageItemCount() + 1, rowModel);
		getData().add(item);
		return item;
	}
  
  protected Item createItem(int index, final IModel<T> rowModel)
  { return getData().createItem(index, rowModel); }

	int getTotalRowCount()
	{
		return getData().getTotalRowCount();
	}

  void clearCache()
  {
    getData().clearCache();
  }

  int getCurrentPageItemCount()
	{
		return getData().getCurrentPageItemCount();
	}

	/**
	 * {@inheritDoc}
	 */
	public int getCurrentPage()
	{
		return getData().getCurrentPage();
	}

	/**
	 * {@inheritDoc}
	 */
	public int getPageCount()
	{
		return getData().getPageCount();
	}

	/**
	 * {@inheritDoc}
	 */
	public void setCurrentPage(int page)
	{
		getData().setCurrentPage(page);
	}

	class Data extends AbstractPageableView<T>
	{
		private static final long serialVersionUID = 1L;

		private Data(String id)
		{
			super(id);
			setItemReuseStrategy(ReuseIfModelsEqualStrategy.getInstance());
		}

		@Override
		protected D getDataSource()
		{
			return DataGridBody.this.getDataSource();
		}

		@Override
		protected int getRowsPerPage()
		{
			return DataGridBody.this.getRowsPerPage();
		}

		@Override
		protected IGridSortState getSortState()
		{
			return DataGridBody.this.getSortState();
		}

		//TODO: Should wrapQuery be removed?
    @Override
		protected IDataSource.IQuery wrapQuery(final IDataSource.IQuery original) 
    {
			return new IDataSource.IQuery() 
      {
				public int getCount() 
        {
					return original.getCount();
				}

				public int getFrom() 
        {
					return original.getFrom();
				}

				public IGridSortState getSortState() 
        {
					return original.getSortState();
				}

				public int getTotalCount() 
        {
					return original.getTotalCount();
				}

				public DataGrid getDataGrid() 
        {
					return (DataGrid) DataGridBody.this.findParent(DataGrid.class);
				}
			};
		}

		@Override
		protected void populateItem(final Item<T> item)
		{
			item.add(new AbstractGridRow<D, T>("item", (IModel<T>)item.getDefaultModel())
			{
				private static final long serialVersionUID = 1L;

				@Override
				protected Collection<IGridColumn<D, T>> getActiveColumns()
				{
					return DataGridBody.this.getActiveColumns();
				}

				@Override
				protected int getRowNumber()
				{
					return item.getIndex();
				}
			});
			item.add(new AttachPrelightBehavior());
			rowPopulated(item);
		}

		protected class RowItem extends Item<T>
		{
			private static final long serialVersionUID = 1L;

			protected RowItem(String id, int index, IModel<T> model)
			{
				super(id, index, model);
			}

			@Override
			protected void onComponentTag(ComponentTag tag)
			{
				super.onComponentTag(tag);

				CharSequence klass = tag.getAttribute("class");
				if (klass == null)
				{
					klass = "";
				}
				else if (klass.length() > 0)
        {
          klass = klass + " ";
        }

				if (getIndex() % 2 == 0)
				{
					klass = klass + "imxt-even";
				}
				else
				{
					klass = klass + "imxt-odd";
				}

				klass = klass + " imxt-want-prelight imxt-grid-row";

				if (isItemSelected((IModel<T>)getDefaultModel()))
				{
					klass = klass + " imxt-selected";
				}

				tag.put("class", klass);
			}
		}

		@Override
		protected Item<T> newItem(String id, final int index, final IModel<T> model)
		{
			Item<T> item = new RowItem(id, index, model);
			item.setOutputMarkupId(true);
			return item;
		}
		
		/**
		 * Create a new Item for this DataGrid.
		 * NOTE: The item has not been added to the grid.
		 * 
		 * @param index row number for insertion
		 * @param itemModel model of the data being inserted
		 * @return Item item inserted
		 */
		protected Item createItem(final int index, final IModel<T> itemModel)
    {	
      return newItemFactory().newItem(index, itemModel); 
    }
	}
  
}
