package org.wicketstuff.egrid.toolbar;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NavigatorLabel;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.wicketstuff.egrid.component.EditableDataTable;
/**
 * 
 * @author Nadeem Mohammad
 *
 */
public class EditableGridNavigationToolbar extends AbstractEditableGridToolbar {

	private static final long serialVersionUID = 1L;

	public EditableGridNavigationToolbar(final EditableDataTable<?, ?> table) {
		super(table);

		WebMarkupContainer span = new WebMarkupContainer("span");
		add(span);
		span.add(AttributeModifier.replace("colspan", new AbstractReadOnlyModel<String>()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public String getObject()
			{
				return String.valueOf(table.getColumns().size());
			}
		}));

		span.add(newPagingNavigator("navigator", table));
		span.add(newNavigatorLabel("navigatorLabel", table));
	}
	
	/**
	 * Factory method used to create the paging navigator that will be used by the datatable
	 * 
	 * @param navigatorId
	 *            component id the navigator should be created with
	 * @param table
	 *            dataview used by datatable
	 * @return paging navigator that will be used to navigate the data table
	 */
	protected PagingNavigator newPagingNavigator(final String navigatorId,
		final EditableDataTable<?, ?> table)
	{
		return new PagingNavigator(navigatorId, table);
	}

	/**
	 * Factory method used to create the navigator label that will be used by the datatable
	 * 
	 * @param navigatorId
	 *            component id navigator label should be created with
	 * @param table
	 *            dataview used by datatable
	 * @return navigator label that will be used to navigate the data table
	 * 
	 */
	protected WebComponent newNavigatorLabel(final String navigatorId, final EditableDataTable<?, ?> table)
	{
		return new NavigatorLabel(navigatorId, table);
	}

	/** {@inheritDoc} */
	@Override
	protected void onConfigure()
	{
		super.onConfigure();
		setVisible(getTable().getPageCount() > 1);
	}

}
