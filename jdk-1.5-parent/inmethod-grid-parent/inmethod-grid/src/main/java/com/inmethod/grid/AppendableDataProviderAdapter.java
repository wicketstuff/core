package com.inmethod.grid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.repeater.data.IDataProvider;

/**
 * Created by IntelliJ IDEA.
 * User: Tom B.
 * Date: 9/20/11
 * Time: 11:13 AM
 *
 * @author Tom B.
 *
 * TODO: make generic
 */
public class AppendableDataProviderAdapter<T>
       extends DataProviderAdapter<T> implements IAppendableDataSource<T>
{
  public AppendableDataProviderAdapter(IDataProvider<T> dataProvider)
  {
    super(dataProvider);
  }

  //do I need this?
  private int _newItemCount = 0;
  private int appendIndex;
  private List items = null;

  public void InsertRow(int index, T item)
  {
    ++_newItemCount;
    if (null == items) { items = new ArrayList(); }
    items.add(item);
    appendIndex = index;
  }

  public void DeleteRow(int index, T item)
  {
    if ( null != items && _newItemCount > 0)
    { if (items.remove(item)){ --_newItemCount; } }
  }

  /** {@inheritDoc} */
	public void query(IQuery query, IQueryResult<T> result)
  {
		super.query(query, result);
    if ( _newItemCount > 0 )
    {
      result.setTotalCount(dataProvider.size() + _newItemCount);
      //TODO: THERE has GOT to be a better way to handle this
      ArrayList AllItems = new ArrayList(dataProvider.size() + _newItemCount);
      for ( Iterator it = dataProvider.iterator(query.getFrom(), query.getCount());
            it.hasNext(); )
      { AllItems.add(it.next()); }

      //for(Object item : items ) { AllItems.add(item); }
      //AllItems.addAll(items);

      try { AllItems.addAll(appendIndex,items); }
      catch( IndexOutOfBoundsException iob )
      { AllItems.addAll(items); } //add failed for index, add to end

      result.setItems(AllItems.iterator());
    }
	}

}
