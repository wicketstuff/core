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
 * Simple DataSource that load contacts.
 * 
 * @author Matej Knopp
 */
public class ContactDataSource implements IDataSource {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public ContactDataSource() {
		
	}

	/**
	 * {@inheritDoc}
	 */	
	public IModel model(Object object) {
		return new DetachableContactModel((Contact)object);
	}

	/**
	 * {@inheritDoc}
	 */
	public void query(IQuery query, IQueryResult result) {
		ContactsDatabase database = DatabaseLocator.getDatabase();
		
		String sortProperty = null;
		boolean sortAsc = true;
		
		// is there any sorting
		if (query.getSortState().getColumns().size() > 0) {
			// get the most relevant column 
			ISortStateColumn state = query.getSortState().getColumns().get(0);
			
			// get the column sort properties
			sortProperty = state.getPropertyName();
			sortAsc = state.getDirection() == IGridSortState.Direction.ASC;
		}
		
		// determine the total count
		result.setTotalCount(database.getCount());
		
		// get the actual items
		List<Contact> resultList = database.find(query.getFrom(), query.getCount(), sortProperty, sortAsc);
		result.setItems(resultList.iterator());
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void detach() {

	}

}
