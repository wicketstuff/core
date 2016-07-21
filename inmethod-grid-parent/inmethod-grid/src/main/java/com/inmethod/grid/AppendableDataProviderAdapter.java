package com.inmethod.grid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.repeater.data.IDataProvider;

/**
 *  extends the functionality of {@link DataProviderAdapter} to
 *  make @{link IDataProvider} instances Appendable
 *
 * @author Tom Burton
 */
public class AppendableDataProviderAdapter<T, S>
	extends DataProviderAdapter<T, S> implements IAppendableDataSource<T>
{
	public AppendableDataProviderAdapter(IDataProvider<T> dataProvider)
	{
		super(dataProvider);
	}

	//do I need this?
	private long _newItemCount = 0;
	private long appendIndex;
	private List<T> items = null;

	public void insertRow(long index, T item)
	{
		++_newItemCount;
		if (null == items) {
			items = new ArrayList<T>();
		}
		items.add(item);
		appendIndex = index;
	}

	public void deleteRow(long index, T item)
	{
		if ( null != items && _newItemCount > 0)
		{
			if (items.remove(item))
			{
				--_newItemCount;
			}
		}
	}

	public void query(IQuery query, IQueryResult<T> result)
	{
		super.query(query, result);
		if ( _newItemCount > 0 )
		{
			result.setTotalCount(dataProvider.size() + _newItemCount);
			//TODO: THERE has GOT to be a better way to handle this
			long longSize = dataProvider.size() + _newItemCount;
			int intSize = 0;
			if ( longSize > Integer.MAX_VALUE  )
			{
				intSize = Integer.MAX_VALUE;
			}
			//TODO: should an error be thrown instead?
			List<T> allItems = new ArrayList<T>(intSize);
			for (Iterator<? extends T> it = dataProvider.iterator(query.getFrom(), query.getCount()); it.hasNext(); )
			{
				allItems.add(it.next());
			}

			//for(Object item : items ) { allItems.add(item); }
			//allItems.addAll(items);

			try
			{
				allItems.addAll((int) appendIndex, items);
			}
			catch ( Exception iob ) //will catch IndexOutOfBounds or ClassCast
			{
				allItems.addAll(items);
			} //add failed for index, add to end

			result.setItems(allItems.iterator());
		}
	}

}
