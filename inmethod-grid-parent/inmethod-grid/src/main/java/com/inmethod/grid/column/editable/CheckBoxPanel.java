package com.inmethod.grid.column.editable;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;

import com.inmethod.grid.column.AbstractColumn;

/**
 * Backing Panel for {@link EditableCheckBoxColumn}
 * Panel with a Checkbox that updates the property of the row immediately after
 * user leaves the field.
 *
 * based on(read: copy-paste-modify) {@link TextFieldPanel} By Matej Knopp
 *
 * @author Tom Burton
 */
public class CheckBoxPanel<M, I, S> extends EditableCellPanel<M, I, Boolean, S>
{
	private static final long serialVersionUID = 1L;

  private static final String CheckBox_ID = "checkbox";

	protected class DefaultCheckBox extends CheckBox
  {
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor for DefaultCheckBox
		 * 
		 * @param id editable Field id
		 * @param object model object being edited
		 */
		protected DefaultCheckBox(String id, IModel<Boolean> object) { super(id, object); }

		@Override
		protected void onComponentTag(ComponentTag tag)
    {
			super.onComponentTag(tag);
			
			if (!isValid())
      {
				tag.put("class", "imxt-invalid");
				FeedbackMessage message = getFeedbackMessages().first();
				if (message != null)
        { tag.put("title", message.getMessage().toString()); }
			}
		}
	}

	/**
	 * Constructor
	 * @param id
	 * 		component id
	 * @param model
	 * 		model for the field
   * @param rowModel
   *    model for the whole data row
	 * @param column
	 * 		column to which this panel belongs
	 */
	public CheckBoxPanel(String id, final IModel<Boolean> model, IModel<I> rowModel,
                       AbstractColumn<M, I, S> column)
  {
		super(id, column, rowModel);
		
		CheckBox cb = newCheckBox(CheckBox_ID, model);
		cb.setOutputMarkupId(true);
		cb.setLabel(column.getHeaderModel());
		add(cb);
	}

	/** creates a new CheckBox to be added for editing
	 *  @param id editable component Id
	 *  @param model field model
	 *  @return checkbox to display
	 */
	protected CheckBox newCheckBox(final String id, final IModel<Boolean> model)
  {	return new DefaultCheckBox(id, model); }

  /** @return the FormComponent for editing */
	@Override
	public FormComponent<Boolean> getEditComponent()
	{
		return (FormComponent<Boolean>) get(CheckBox_ID);
	}
}
