package com.inmethod.grid.examples.pages.datagrid;

import java.util.List;

import org.apache.wicket.model.IModel;

import com.inmethod.grid.IDataSource;
import com.inmethod.grid.IGridSortState;
import com.inmethod.grid.IGridSortState.ISortStateColumn;
import com.inmethod.grid.examples.contact.Contact;
import com.inmethod.grid.examples.contact.ContactsDatabase;
import com.inmethod.grid.examples.contact.DatabaseLocator;
import com.inmethod.grid.examples.contact.DetachableContactModel;

/**
 * Simple DataSource that load contacts without knowing the actual item count.
 * 
 * @author Matej Knopp
 */
public class ContactDataSourceWithUnknownItemCount implements IDataSource<Contact>
{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public ContactDataSourceWithUnknownItemCount()
	{

	}

	/**
	 * {@inheritDoc}
	 */
	public IModel<Contact> model(Contact object)
	{
		return new DetachableContactModel(object);
	}

	/**
	 * {@inheritDoc}
	 */
	public void query(IQuery query, IQueryResult<Contact> result)
	{
		ContactsDatabase database = DatabaseLocator.getDatabase();

		String sortProperty = null;
		boolean sortAsc = true;

		// is there any sorting
		if (query.getSortState().getColumns().size() > 0)
		{
			// get the most relevant column
			ISortStateColumn<Object> state = (ISortStateColumn<Object>) query.getSortState().getColumns().get(0);

			// get the column sort properties
			sortProperty = state.getPropertyName().toString();
			sortAsc = state.getDirection() == IGridSortState.Direction.ASC;
		}

		// since we don't know the actual item count we try to load one item more than requested
		// if there are n+1 items we know there will be something on the next page

		// get the actual items
		List<Contact> resultList = database.find(query.getFrom(), query.getCount() + 1,
			sortProperty, sortAsc);
		result.setItems(resultList.iterator());

		if (resultList.size() == query.getCount() + 1)
		{
			// if we managed to load n + 1 items (thus there will be another page)
			result.setTotalCount(IQueryResult.MORE_ITEMS);
		}
		else
		{
			// no more items - this is the latest page
			result.setTotalCount(IQueryResult.NO_MORE_ITEMS);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public void detach()
	{

	}

}
