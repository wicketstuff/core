package com.inmethod.grid.common;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;

import com.inmethod.grid.IDataSource;
import com.inmethod.grid.IGridSortState;
import com.inmethod.grid.IDataSource.IQuery;
import com.inmethod.grid.IDataSource.IQueryResult;

/**
 * Wicket {@link org.apache.wicket.markup.repeater.AbstractPageableView} alternative that uses
 * {@link IDataSource} as data source. Compared to Wicket
 * {@link org.apache.wicket.markup.repeater.AbstractPageableView} this component allows paging
 * without knowing the total number of rows.
 * 
 * @author Matej Knopp
 */
public abstract class AbstractPageableView extends RefreshingView implements IPageable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor,
	 * 
	 * @param id
	 * @param model
	 */
	public AbstractPageableView(String id, IModel model) {
		super(id, model);
	}

	/**
	 * Constructor.
	 * 
	 * @param id
	 */
	public AbstractPageableView(String id) {
		super(id);
	}

	/**
	 * Constant for unknown count of rows.
	 */
	public static final int UNKOWN_COUNT = -1;

	/**
	 * Returns the total count of items (sum of count of items on all pages) or
	 * {@link #UNKOWN_COUNT} in case the count can't be determined.
	 * 
	 * @return total count of items or {@value #UNKOWN_COUNT}
	 */
	public int getTotalRowCount() {
		initialize();

		return realItemCount;
	}

	/**
	 * Returns the count of items on current page.
	 * 
	 * @return count of items on current page
	 */
	public int getCurrentPageItemCount() {
		initialize();

		return queryResult.itemCache.size();
	}

	/**
	 * Returns the total number of items. If the underlying {@link IDataSource} supports reporting
	 * total item count, the number of items is real, otherwise it's guessed.
	 * 
	 * @return total number of items
	 */
	private int getItemCount() {
		initialize();

		if (realItemCount == UNKOWN_COUNT) {
			return maxFirstItemReached + getRowsPerPage() + 1;
		} else {
			return realItemCount;
		}
	}

	/**
	 * @return The current page that is or will be rendered.
	 */
	public int getCurrentPage() {
		return getCurrentPageFirstItem() / getRowsPerPage();
	}

	@Override
	protected void onBeforeRender() {
		cachedPageCount = -1;
		
		super.onBeforeRender();				
	}
	
	// we cache page count because paging navigator needs it even when items are not loaded
	private int cachedPageCount = -1;
	
	/**
	 * Gets the total number of pages this pageable object has.
	 * 
	 * @return The total number of pages this pageable object has
	 */
	public int getPageCount() {
		if (cachedPageCount == -1)
		{
			int count = getItemCount();
			if (count == 0) {
				cachedPageCount = 0;
			} else {
				// if current item count is not dividable by the page size, subtract the mod
				int rowsPerPage = getRowsPerPage();
				int mod = count % rowsPerPage;
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
	public void setCurrentPage(int page) {

		int pageCount = getPageCount();
		if (page < 0 || (page >= pageCount && pageCount > 0)) {
			throw new IndexOutOfBoundsException("Argument page is out of bounds");
		}

		int currentItem = getRowsPerPage() * page;

		setCurrentPageFirstItem(currentItem);

		// todo: bump version (maybe)

	}

	/**
	 * We keep track of maximal first page item reached to be able to guess item count in case the
	 * {@link IDataSource} can't provide the real count
	 */
	private int maxFirstItemReached;

	/**
	 * The actual count of items. This is set by either passing actual count of items to
	 * {@link IQueryResult#setTotalCount(int)}, or by passing the
	 * {@link IQueryResult#NO_MORE_ITEMS} constant as item count.
	 */
	private int realItemCount = UNKOWN_COUNT;

	/**
	 * Index of the item which is at the beginning on current page
	 */
	private int currentPageFirstItem = 0;

	/**
	 * Cached query result for this request
	 */
	private transient QueryResult queryResult;

	/**
	 * Allows to wrap created query.
	 * 
	 * @param original
	 * @return
	 */
	protected IQuery wrapQuery(IQuery original) {
		return original;
	}

	/**
	 * Loads the {@link #queryResult} and initializes it if it's not already loaded.
	 */
	private void initialize() {
		if (queryResult == null) {
			queryResult = new QueryResult();
			Query query = new Query(queryResult);

			IDataSource dataSource = getDataSource();

			int oldItemCount = realItemCount;

			// query for items
			dataSource.query(wrapQuery(query), queryResult);

			// process the QueryResult
			queryResult.process(dataSource);

			// check for situation when we didn't get any items, but we know the real count
			// this is not a case when there are no items at all, just the case when there are no items on current page
			// but possible items on previous pages
			if (queryResult.itemCache.size() == 0 && realItemCount != UNKOWN_COUNT && 
				realItemCount != oldItemCount && realItemCount > 0) {
				
				// the data must have changed, the number of items has been reduced. try move to
				// last page
				int page = getPageCount() - 1;
				if (page < 0) {
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
				QueryResult tmp = this.queryResult;
				setCurrentPage(0);
				this.queryResult = tmp;
			}
		}
	}

	/**
	 * @see IQuery
	 * @author Matej Knopp
	 */
	private class Query implements IQuery {
		QueryResult result;

		private Query(QueryResult result) {
			this.result = result;
		}

		/**
		 * {@inheritDoc}
		 */
		public int getCount() {

			int totalCount = getTotalCount();
			int rowsPerPage = getRowsPerPage();

			// we try to get the real count (user might have called
			// IQueryResult.setTotalCount before calling getCount
			final int count;
			if (totalCount != UNKOWN_COUNT) {
				count = Math.min(totalCount - getFrom(), rowsPerPage);
			} else {
				// otherwise just return the number of rows per page
				count = rowsPerPage;
			}
			return count >= 0 ? count : 0;
		}

		/**
		 * {@inheritDoc}
		 */
		public int getFrom() {
			return getCurrentPageFirstItem();
		}

		/**
		 * {@inheritDoc}
		 */
		public IGridSortState getSortState() {
			return AbstractPageableView.this.getSortState();
		}

		/**
		 * {@inheritDoc}
		 */
		public int getTotalCount() {
			return result.totalCount;
		}
	};

	/**
	 * Convenience class representing an empty iterator
	 * 
	 * @author Matej Knopp
	 * 
	 * @param <T>
	 */
	private static class EmptyIterator<T> implements Iterator<T> {
		/**
		 * {@inheritDoc}
		 */
		public boolean hasNext() {
			return false;
		}

		/**
		 * {@inheritDoc}
		 */
		public T next() {
			throw new IndexOutOfBoundsException();
		}

		/**
		 * {@inheritDoc}
		 */
		public void remove() {
			throw new UnsupportedOperationException();
		}

		private static final EmptyIterator<?> INSTANCE = new EmptyIterator<Object>();
	};

	/**
	 * A {@link IQueryResult} implementation
	 * 
	 * @author Matej Knopp
	 */
	private class QueryResult implements IQueryResult {
		// start with empty items
		private Iterator<?> items = EmptyIterator.INSTANCE;

		// and actual total count (could be UNKNOWN)
		private int totalCount = AbstractPageableView.this.realItemCount;

		// process will put the actual item model's here
		private ArrayList<IModel> itemCache = new ArrayList<IModel>();

		/**
		 * @see IQueryResult#setItems(Iterator)
		 */
		public void setItems(Iterator<?> items) {
			this.items = items;
		}

		/**
		 * @see IQueryResult#setTotalCount(int)
		 */
		public void setTotalCount(int count) {
			this.totalCount = count;
		}

		/**
		 * Processes the result after the {@link IDataSource#query(IQuery, IQueryResult)} method is
		 * executed,
		 * 
		 * @param source
		 */
		public void process(IDataSource source) {
			// count the maximum number of items that should have been loaded
			int max = getRowsPerPage();
			if (totalCount > 0) {
				max = Math.min(max, totalCount - getCurrentPageFirstItem());
			}

			// wrap the loaded items as IModels and add them to itemCache
			while (max > 0 && items.hasNext()) {
				itemCache.add(source.model(items.next()));
				--max;
			}

			if (itemCache.size() == 0 && totalCount < 0) {
				// in case no items have been loaded
				// this is to have the last page displayed in paging navigator
				totalCount = getCurrentPageFirstItem() + 1;
			} else if (totalCount == IQueryResult.NO_MORE_ITEMS) {
				// if the reported count was NO_MORE_RESULT, these are all items
				// we can get, thus the totalCount can be counted properly
				totalCount = getCurrentPageFirstItem() + itemCache.size();
			}

			if (totalCount == IQueryResult.MORE_ITEMS && getCurrentPage() != getPageCount()
					&& AbstractPageableView.this.realItemCount != UNKOWN_COUNT) {
				// if we know the real item count and the page shown is not last page, we
				// don't allow MORE_ITEMS overwrite the real item count
			} else {
				// update the real item count
				AbstractPageableView.this.realItemCount = totalCount;
			}
		}
	};

	/**
	 * Cleanup
	 */
	@Override
	protected void onDetach() {
		super.onDetach();
		queryResult = null;
	}

	protected abstract IGridSortState getSortState();

	protected abstract IDataSource getDataSource();

	protected abstract int getRowsPerPage();

	/**
	 * @see RefreshingView#getItemModels()
	 */
	@Override
	protected Iterator<IModel> getItemModels() {
		initialize();
		return queryResult.itemCache.iterator();
	}

	/**
	 * Sets the item that determines the current page
	 * 
	 * @param currentItem
	 */
	private void setCurrentPageFirstItem(int currentItem) {
		if (this.currentPageFirstItem != currentItem) {

			if (maxFirstItemReached < currentItem) {
				maxFirstItemReached = currentItem;
			}

			this.currentPageFirstItem = currentItem;
			this.queryResult = null;
		}
	}

	private int getCurrentPageFirstItem() {
		int rowsPerPage = getRowsPerPage();
		return currentPageFirstItem - (currentPageFirstItem % rowsPerPage);
	}

}
