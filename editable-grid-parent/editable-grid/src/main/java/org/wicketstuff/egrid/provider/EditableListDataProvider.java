package org.wicketstuff.egrid.provider;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
/**
 * 
 * @author Nadeem Mohammad
 *
 */
public class EditableListDataProvider<T extends Serializable, S> implements IEditableDataProvider<T, S>
{

	private static final long serialVersionUID = 1L;
	private final List<T> list;
	
	public EditableListDataProvider()
	{
		this(Collections.<T>emptyList());
	}

	public EditableListDataProvider(List<T> list)
	{
		if (list == null)
		{
			throw new IllegalArgumentException("argument [list] cannot be null");
		}
		this.list = list;
	}

	protected List<T> getData()
	{
		return list;
	}

	@Override
	public Iterator<? extends T> iterator(final long first, final long count)
	{
		List<T> list = getData();

		long toIndex = first + count;
		if (toIndex > list.size())
		{
			toIndex = list.size();
		}
		return list.subList((int)first, (int)toIndex).listIterator();
	}

	@Override
	public long size()
	{
		return getData().size();
	}

	@Override
	public IModel<T> model(T object)
	{
		return new Model<T>(object);
	}

	@Override
	public void detach()
	{
	}

	@Override
	public void add(T item)
	{
		list.add(item);		
	}

	@Override
	public void remove(T item)
	{
		list.remove(item);
	}

	@Override
	public ISortState<S> getSortState() {
		return null;
	}
}
