package org.wicketstuff.egrid.column;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
/**
 * 
 * @author Nadeem Mohammad
 *
 */
public class EditableGridActionsColumn<T, S> extends PropertyColumn<T, S>
{

	public EditableGridActionsColumn(IModel<String> displayModel)
	{
		super(displayModel, "");		
	}

	private static final long serialVersionUID = 1L;

	@Override
	public void populateItem(final Item<ICellPopulator<T>> item, final String componentId, final IModel<T> rowModel)
	{

		item.add(new EditableGridActionsPanel<T>(componentId, item)
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSave(AjaxRequestTarget target) {
				EditableGridActionsColumn.this.onSave(target, rowModel);				
			}

			@Override
			protected void onError(AjaxRequestTarget target) {				
				EditableGridActionsColumn.this.onError(target, rowModel);				
			}

			@Override
			protected void onCancel(AjaxRequestTarget target) {
				EditableGridActionsColumn.this.onCancel(target);		
			}

			@Override
			protected void onDelete(AjaxRequestTarget target) {				
				EditableGridActionsColumn.this.onDelete(target, rowModel);		
			}
			@Override
			protected boolean allowDelete(Item<T> rowItem) {
				return EditableGridActionsColumn.this.allowDelete(rowItem);
			}
			
		});		
	}
	
	protected boolean allowDelete(Item<T> rowItem) {
		return true;
	}

	protected void onDelete(AjaxRequestTarget target, IModel<T> rowModel)
	{				
		
	}

	protected void onSave(AjaxRequestTarget target, IModel<T> rowModel)
	{
				
	}

	protected void onError(AjaxRequestTarget target, IModel<T> rowModel)
	{
				
	}
	protected void onCancel(AjaxRequestTarget target) {

	}
}
