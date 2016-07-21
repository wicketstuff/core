package com.inmethod.grid.common;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;

import com.inmethod.grid.IDataSource;
import com.inmethod.grid.IDataSource.IQuery;
import com.inmethod.grid.IDataSource.IQueryResult;
import com.inmethod.grid.IGridSortState;

/**
 * Wicket {@link org.apache.wicket.markup.repeater.AbstractPageableView} alternative that uses
 * {@link IDataSource} as data source. Compared to Wicket
 * {@link org.apache.wicket.markup.repeater.AbstractPageableView} this component allows paging
 * without knowing the total number of rows.
 * 
 * @param <T>
 *            row/item model object type
 * 
 * @author Matej Knopp
 */
public abstract class AbstractPageableView<T> extends RefreshingView<T>
       implements IPageable
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor,
	 * 
	 * @param id
	 * @param model
	 */
	public AbstractPageableView(String id, IModel<T> model)
	{
		super(id, model);
	}

	/**
	 * Constructor.
	 * 
	 * @param id
	 */
	public AbstractPageableView(String id)
	{
		super(id);
	}

	/**
	 * Constant for unknown count of rows.
	 */
	public static final long UNKNOWN_COUNT = -1;

	/**
	 * Returns the total count of items (sum of count of items on all pages) or
	 * {@link #UNKNOWN_COUNT} in case the count can't be determined.
	 * 
	 * @return total count of items or {@value #UNKNOWN_COUNT}
	 */
	public long getTotalRowCount()
	{
		initialize();
		return realItemCount;
	}

	/**
	 * Returns the count of items on current page.
	 * 
	 * @return count of items on current page
	 */
	public int getCurrentPageItemCount()
	{
		initialize();
		return queryResult.itemCache.size();
	}

	/**
	 * Returns the total number of items. If the underlying {@link IDataSource} supports reporting
	 * total item count, the number of items is real, otherwise it's guessed.
	 * 
	 * @return total number of items
	 */
	private long getItemCount()
	{
		initialize();

		if (realItemCount == UNKNOWN_COUNT)
		{
			return maxFirstItemReached + getRowsPerPage() + 1;
		}
		else
		{
			return realItemCount;
		}
	}

	/**
	 * @return The current page that is or will be rendered.
	 */
	public long getCurrentPage()
	{
		return getCurrentPageFirstItem() / getRowsPerPage();
	}

	@Override
	protected void onBeforeRender()
	{
		cachedPageCount = -1;

		super.onBeforeRender();
	}

	// we cache page count because paging navigator needs it even when items are not loaded
	private long cachedPageCount = -1;

	/**
	 * Gets the total number of pages this pageable object has.
	 * 
	 * @return The total number of pages this pageable object has
	 */
	public long getPageCount()
	{
		if (cachedPageCount == -1)
		{
			long count = getItemCount();
			if (count == 0)
			{
				cachedPageCount = 0;
			}
			else
			{
				// if current item count is not dividable by the page size, subtract the mod
				long rowsPerPage = getRowsPerPage();
				long mod = count % rowsPerPage;
				count -= mod;

				// get the actual page count
				cachedPageCount = (count / rowsPerPage) + (mod > 0 ? 1 : 0);
			}
		}
		return cachedPageCount;
	}

	/**
	 * Sets the a page that should be rendered.
	 * 
	 * @param page
	 *            The page that should be rendered.
	 */
	public void setCurrentPage(long page)
	{
		long pageCount = getPageCount();
		if (page < 0 || page >= pageCount && pageCount > 0)
		{
			throw new IndexOutOfBoundsException("Argument page is out of bounds");
		}

		long currentItem = getRowsPerPage() * page;

		setCurrentPageFirstItem(currentItem);

		// todo: bump version (maybe)

	}

	/**
	 * We keep track of maximal first page item reached to be able to guess item count in case the
	 * {@link IDataSource} can't provide the real count
	 */
	private long maxFirstItemReached;

	/**
	 * You should never need this method unless your pagingNavigator uses pageParametres 
	 * and dataSource is with unknown item count
	 * 
	 * @param maxFirstItemReached
	 */
	public void setMaxFirstItemReached(long maxFirstItemReached) {
		this.maxFirstItemReached = maxFirstItemReached;
	}
	/**
	 * The actual count of items. This is set by either passing actual count of items
   * to {@link IQueryResult#setTotalCount(long)},
   * or by passing the {@link IQueryResult#NO_MORE_ITEMS} constant as item count.
	 */
	private long realItemCount = UNKNOWN_COUNT;

	/**
	 * Index of the item which is at the beginning on current page
	 */
	private long currentPageFirstItem = 0;

	/**
	 * Cached query result for this request
	 */
	private transient QueryResult queryResult;

  /** clears the queryResult so  the next use will be forced to re-initialize */
  public void clearCache() { queryResult = null; }

	/**
	 * Allows to wrap created query.
	 * 
	 * @param original
	 * @return
	 */
	protected IQuery wrapQuery(IQuery original)
	{
		return original;
	}

	/**
	 * Loads the {@link #queryResult} and initializes it if it's not already loaded.
	 */
	private void initialize()
	{
		if (queryResult == null)
		{
			queryResult = new QueryResult();
			Query query = new Query(queryResult);

			IDataSource<T> dataSource = getDataSource();

			long oldItemCount = realItemCount;

			// query for items
			dataSource.query(wrapQuery(query), queryResult);

			// process the QueryResult
			queryResult.process(dataSource);

			// check for situation when we didn't get any items,
			// but we know the real count
			// this is not a case when there are no items at all,
			// just the case when there are no items on current page
			// but possible items on previous pages
			if ( queryResult.itemCache.size() == 0 && realItemCount != UNKNOWN_COUNT 
        && realItemCount != oldItemCount && realItemCount > 0 )
			{
				// the data must have changed, the number of items has been reduced. 
				// try move to the last page
				long page = getPageCount() - 1;
				if (page < 0)
				{
					page = 0;
				}
				setCurrentPage(page);

				// try reloading the items
				queryResult = new QueryResult();
				query = new Query(queryResult);

				// query for items
				dataSource.query(query, queryResult);

				// process the QueryResult
				queryResult.process(dataSource);
			}
			else if (realItemCount == 0)
			{
				// this is the case with no items at all
				QueryResult tmp = queryResult;
				setCurrentPage(0);
				queryResult = tmp;
			}
		}
	}

	/**
	 * @see IQuery
	 * @author Matej Knopp
	 */
	private class Query implements IQuery
	{
		QueryResult result;

		private Query(QueryResult result)
		{
			this.result = result;
		}

		/**
		 * {@inheritDoc}
		 */
		public long getCount()
		{
			long totalCount = getTotalCount();
			long rowsPerPage = getRowsPerPage();

			// we try to get the real count (user might have called
			// IQueryResult.setTotalCount before calling getCount
			final long count;
			if (totalCount != UNKNOWN_COUNT)
			{
				count = Math.min(totalCount - getFrom(), rowsPerPage);
			}
			else
			{
				// otherwise just return the number of rows per page
				count = rowsPerPage;
			}
			return count >= 0 ? count : 0;
		}

		/**
		 * {@inheritDoc}
		 */
		public long getFrom()
		{
			return getCurrentPageFirstItem();
		}

		/**
		 * {@inheritDoc}
		 */
		public IGridSortState getSortState()
		{
			return AbstractPageableView.this.getSortState();
		}

		/**
		 * {@inheritDoc}
		 */
		public long getTotalCount()
		{
			return result.totalCount;
		}
	}

	/**
	 * Convenience class representing an empty iterator
	 * 
	 * @author Matej Knopp
	 * 
	 * @param <T>
	 */
	private static class EmptyIterator<T> implements Iterator<T>
	{
		/**
		 * {@inheritDoc}
		 */
		public boolean hasNext()
		{
			return false;
		}

		/**
		 * {@inheritDoc}
		 */
		public T next()
		{
			throw new IndexOutOfBoundsException();
		}

		/**
		 * {@inheritDoc}
		 */
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * A {@link IQueryResult} implementation
	 * 
	 * @author Matej Knopp
	 */
	private class QueryResult implements IQueryResult<T>
	{
		// start with empty items
    //TODO: wouldn't the Collections.Empty List constant be better ex follows? - Tom Burton(Raystorm)
    //private Iterator<? extends T> altItems = Collections.<T>emptyList().iterator();
		private Iterator<? extends T> items = new EmptyIterator<T>();

		// and actual total count (could be UNKNOWN)
		private long totalCount = realItemCount;

		// process will put the actual item model's here
		private final ArrayList<IModel<T>> itemCache = new ArrayList<IModel<T>>();

		/**
		 * @see IQueryResult#setItems(Iterator)
		 */
		public void setItems(Iterator<? extends T> items)
		{
			this.items = items;
		}

		/**
		 * @see IQueryResult#setTotalCount(long)
		 */
		public void setTotalCount(long count)
		{
			totalCount = count;
		}

		/**
		 * Processes the result after the {@link IDataSource#query(IQuery, IQueryResult)} method is
		 * executed,
		 * 
		 * @param source
		 */
		public void process(IDataSource<T> source)
		{
			// count the maximum number of items that should have been loaded
			long max = getRowsPerPage();
			if (totalCount > 0)
			{
				max = Math.min(max, totalCount - getCurrentPageFirstItem());
			}

			// wrap the loaded items as IModels and add them to itemCache
			while (max > 0 && items.hasNext())
			{
				itemCache.add(source.model(items.next()));
				--max;
			}

			if (itemCache.size() == 0 && totalCount < 0)
			{
				// in case no items have been loaded
				// this is to have the last page displayed in paging navigator
				if (totalCount == IQueryResult.NO_MORE_ITEMS)
				{
					totalCount = 0;
				}
				else
				{
					totalCount = getCurrentPageFirstItem() + 1;
				}
			}
			else if (totalCount == IQueryResult.NO_MORE_ITEMS)
			{
				// if the reported count was NO_MORE_RESULT, these are all items
				// we can get, thus the totalCount can be counted properly
				totalCount = getCurrentPageFirstItem() + itemCache.size();
			}

			if ( totalCount == IQueryResult.MORE_ITEMS && getCurrentPage() != getPageCount() 
        && realItemCount != UNKNOWN_COUNT )
			{
				// if we know the real item count and the page shown is not last page, we
				// don't allow MORE_ITEMS overwrite the real item count
			}
			else
			{
				// update the real item count
				realItemCount = totalCount;
			}
		}
	}

	/**
	 * Cleanup
	 */
	@Override
	protected void onDetach()
	{
		super.onDetach();
		queryResult = null;
	}

	protected abstract IGridSortState getSortState();

	protected abstract IDataSource<T> getDataSource();

	protected abstract long getRowsPerPage();

	/**
	 * @see RefreshingView#getItemModels()
	 */
	@Override
	protected Iterator<IModel<T>> getItemModels()
	{
		initialize();
		return queryResult.itemCache.iterator();
	}

	/**
	 * Sets the item that determines the current page
	 * 
	 * @param currentItem
	 */
	private void setCurrentPageFirstItem(long currentItem)
	{
		if (currentPageFirstItem != currentItem)
		{
			if (maxFirstItemReached < currentItem)
			{
				maxFirstItemReached = currentItem;
			}
			currentPageFirstItem = currentItem;
			queryResult = null;
		}
	}

	private long getCurrentPageFirstItem()
	{
		long rowsPerPage = getRowsPerPage();
		return currentPageFirstItem - currentPageFirstItem % rowsPerPage;
	}

}
