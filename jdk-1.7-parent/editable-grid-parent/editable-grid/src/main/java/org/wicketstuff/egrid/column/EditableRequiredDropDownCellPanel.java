package org.wicketstuff.egrid.column;

import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.FormComponent;
/**
 * 
 * @author Nadeem Mohammad
 *
 */
public class EditableRequiredDropDownCellPanel<T, S> extends EditableCellPanel
{

	private static final long serialVersionUID = 1L;

	public EditableRequiredDropDownCellPanel(final String id, final PropertyColumn<T, S> column, @SuppressWarnings("rawtypes") final List choices)
	{
		super(id);

		@SuppressWarnings("unchecked")
		DropDownChoice<T> field = new DropDownChoice<T>("dropdown", choices);
		field.setLabel(column.getDisplayModel());
		add(field);	
	}

	@Override
	public FormComponent<?> getEditableComponent()
	{
		return (FormComponent<?>) get("dropdown");
	}
}
