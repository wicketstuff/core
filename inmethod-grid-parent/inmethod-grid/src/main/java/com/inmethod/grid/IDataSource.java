package com.inmethod.grid;

import java.util.Iterator;

import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;

/**
 * Interface used to provide data to data views. This interface allows to create a paged DataGrid
 * without being able to know the exact amount of rows upfront.
 * <p>
 * An example of situation when the row count can be determined:
 *
 * <pre>
 * IDataSource&lt;User&gt; source = new IDataSource&lt;User&gt;() {
 * 		public void query(IQuery query, IQueryResult&lt;User&gt; result) {
 *
 * 			UserDao dao = DaoManager.getUserDao(); // code to get a DAO or service object
 *
 * 			result.setTotalCount(dao.getUserCount());
 *
 * 			Collection&lt;User&gt; users = dao.getUsers(query.getFrom(), query.getCount());
 * 			result.setItems(users.iterator());
 * 		}
 *
 * 		public IModel&lt;User&gt; model(User object) {
 * 			return new DetachableUserModel(object);
 * 		}
 * }
 * </pre>
 *
 * An example of situation when the row count can't be determined. The pagination then only allows
 * to advance by one page. The idea is to retrieve n + 1 rows when n rows are requested to decide
 * whether the next page should be available:
 *
 * <pre>
 * IDataSource&lt;User&gt; source = new IDataSource&lt;User&gt;() {
 * 		public void query(IQuery query, IQueryResult&lt;User&gt; result) {
 *
 * 			UserDao dao = DaoManager.getUserDao(); // code to get a DAO or service object
 * 			Collection&lt;User&gt; users = dao.getUsers(query.getFrom(), query.getCount() + 1);
 *
 * 			if (users.size() &lt; query.getCount() + 1) {
 * 				result.setTotalCount(IQueryResult.NO_MORE_ITEMS);
 * 			} else {
 * 				result.setTotalCount(IQueryResult.MORE_ITEMS);
 * 			}
 *
 * 			result.setItems(users.iterator());
 * 	}
 *
 * 	public IModel&lt;User&gt; model(User object) {
 * 			return new DetachableUserModel(object);
 * 		}
 * }
 * </pre>
 *
 * @param <T>
 *            row/item model object type
 *
 * @author Matej Knopp
 */
public interface IDataSource<T> extends IDetachable
{

	/**
	 * Implementation of this method should load subset of the data specified by
	 * <code>query.getFrom()</code> and <code>query.getCount()</code>. Also if the total item count
	 * can be determined, it should be passed to <code>result</code>.
	 *
	 * @param query
	 *            Specified the amount and position of items to be queried
	 * @param result
	 *            Allows to set the total item count and result items
	 */
	public void query(IQuery query, IQueryResult<T> result);

	/**
	 * Allows wrapping the object in a model which will be set as model of the appropriate row. In
	 * most cases the model should be detachable.
	 *
	 * @param object
	 * @return model that can be used to access the object
	 */
	public IModel<T> model(T object);

	/**
	 * Specifies the subset of data to be loaded.
	 *
	 * @author Matej Knopp
	 */
	public interface IQuery
	{
		/**
		 * Returns the index of first item to be loaded
		 *
		 * @return index of first item to be loaded
		 */
		public long getFrom();

		/**
		 * Returns the amount of items to be loaded. If the total amount is known (it was either set
		 * by {@link IQueryResult#setTotalCount(long)} before calling this method or the previous
		 * call to {@link IDataSource#query(IDataSource.IQuery, IDataSource.IQueryResult)} set
		 * {@link IQueryResult#NO_MORE_ITEMS} as total count), this method will return the exact
		 * amount of required rows. If the total amount of rows is not known, it will always return
		 * the number of items per page.
		 *
		 * @return amount of required rows (might be items per page if the total amount of rows is
		 *         not known)
		 */
		public long getCount();

		/**
		 * Constant that represents unknown row count returned by {@link #getTotalCount()}
		 */
		public static final long UNKNOWN_TOTAL_COUNT = -1L;

		/**
		 * Returns the total amount of rows or {@link #UNKNOWN_TOTAL_COUNT} if the amount of rows is
		 * not known yet.
		 *
		 * @return total amount of rows
		 */
		public long getTotalCount();

		/**
		 * Returns the {@link IGridSortState} that can be used to determine which sortable columns
		 * have been used to sort the table.
		 *
		 * @return sort state
		 */
		public <S> IGridSortState<S> getSortState();
	}

	/**
	 * Used to pass the total row count and the loaded item to the caller of
	 * {@link IDataSource#query(IDataSource.IQuery, IDataSource.IQueryResult)} method.
	 *
	 * @param <T>
	 *            row/item model object type
	 *
	 * @author Matej Knopp
	 */
	public interface IQueryResult<T>
	{
		/**
		 * Constant indicating that there are more items left.
		 */
		public static final int MORE_ITEMS = -1;

		/**
		 * Constant indicating that there are no more items left.
		 */
		public static final int NO_MORE_ITEMS = -2;

		/**
		 * Sets the total items count. Alternatively, if the total item count can't be determined,
		 * either {@link #MORE_ITEMS} or {@link #NO_MORE_ITEMS} constant can be used to indicate
		 * whether there are more items left or not.
		 *
		 * If the real items count is specified, it might affect the result of
		 * {@link IQuery#getCount()}, so it is preferred to call this method before calling
		 * {@link #setItems(Iterator)}.
		 *
		 * @param count
		 *            the total count of items
		 */

		public void setTotalCount(long count);

		/**
		 * Sets the actual loaded items.
		 *
		 * @param items
		 *            iterator able to iterate through the loaded items.
		 */
		public void setItems(Iterator<? extends T> items);
	};
}
