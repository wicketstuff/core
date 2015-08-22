package org.wicketstuff.egrid.column;

import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
/**
 * 
 * @author Nadeem Mohammad
 *
 */
public class RequiredEditableTextFieldColumn<T, S> extends EditableTextFieldPropertyColumn<T, S>
{

	private static final long serialVersionUID = 1L;

	public RequiredEditableTextFieldColumn(final IModel<String> displayModel, final String propertyExpression)
	{
		super(displayModel, propertyExpression);
	}

	public RequiredEditableTextFieldColumn(final IModel<String> displayModel, final String propertyExpression, final boolean isEditable)
	{
		super(displayModel, propertyExpression, isEditable);
	}

	@Override
	protected void addBehaviors(final FormComponent<T> editorComponent)
	{
		editorComponent.setRequired(true);
	}
}
