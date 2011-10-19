package com.inmethod.grid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.markup.repeater.data.IDataProvider;

import com.inmethod.grid.datagrid.DataGrid;

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
public class AppendableDataProviderAdapter
       extends DataProviderAdapter implements IAppendableDataSource
{
  public AppendableDataProviderAdapter(IDataProvider dataProvider)
  {
    super(dataProvider);
  }

  //do I need this?
  private int _newItemCount = 0;
  private int appendIndex;
  private List items = null;

  public void InsertRow(int index, Object item)
  {
    ++_newItemCount;
    if (null == items) { items = new ArrayList(); }
    items.add(item);
    appendIndex = index;
  }

  /**
	 * {@inheritDoc}
	 */
	public void query(IQuery query, IQueryResult result)
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

      try { AllItems.addAll(appendIndex,items); }
      catch( IndexOutOfBoundsException iob )
      { AllItems.addAll(items); } //add failed for index, add to end

      result.setItems(AllItems.iterator());
    }
	}

}
