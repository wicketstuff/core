package org.wicketstuff.egrid.toolbar;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.wicketstuff.egrid.component.EditableDataTable;
/**
 * 
 * @author Nadeem Mohammad
 *
 */
public class AbstractEditableGridToolbar extends Panel
{

	private static final long serialVersionUID = 1L;

	/** Counter used for generating unique component ids. */
	private static long counter = 0;

	private final EditableDataTable<?, ?> table;

	/**
	 * Constructor
	 * 
	 * @param model
	 *            model
	 * @param table
	 *            data table this toolbar will be attached to
	 */
	public AbstractEditableGridToolbar(final IModel<?> model, final EditableDataTable<?, ?> table)
	{
		super(String.valueOf(counter++).intern(), model);
		this.table = table;
	}

	/**
	 * Constructor
	 * 
	 * @param table
	 *            data table this toolbar will be attached to
	 */
	public AbstractEditableGridToolbar(final EditableDataTable<?, ?> table)
	{
		this(null, table);
	}

	/**
	 * @return DataTable this toolbar is attached to
	 */
	protected EditableDataTable<?, ?> getTable()
	{
		return table;
	}
}
