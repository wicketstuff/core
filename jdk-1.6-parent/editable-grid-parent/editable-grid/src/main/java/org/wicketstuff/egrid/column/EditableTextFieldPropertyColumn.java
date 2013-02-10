package org.wicketstuff.egrid.column;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
/**
 * 
 * @author Nadeem Mohammad
 *
 */
public class EditableTextFieldPropertyColumn<T, S> extends AbstractEditablePropertyColumn<T, S>
{

	private static final long serialVersionUID = 1L;

	public EditableTextFieldPropertyColumn(IModel<String> displayModel, String propertyExpression)
	{
		super(displayModel, propertyExpression);
	}
	
	
	public EditableTextFieldPropertyColumn(IModel<String> displayModel, String propertyExpression, boolean isEditable)
	{
		super(displayModel, propertyExpression, isEditable);
	}

	@Override
	public EditableCellPanel getEditableCellPanel(String componentId)
	{
		return new EditableTextFieldCellPanel<T, S>(componentId, this)
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected void addBehaviors(FormComponent<T> formComponent)
			{
				EditableTextFieldPropertyColumn.this.addBehaviors(formComponent);
			}
		};
	}
}
