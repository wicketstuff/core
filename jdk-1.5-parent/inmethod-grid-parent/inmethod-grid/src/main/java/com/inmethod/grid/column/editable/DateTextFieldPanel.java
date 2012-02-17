package com.inmethod.grid.column.editable;

import java.util.Date;

import org.apache.wicket.datetime.DateConverter;
import org.apache.wicket.datetime.StyleDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;

import com.inmethod.grid.column.AbstractColumn;

/**
 * Panel with a DateTextField that updates the property of the row immediately after
 * user leaves the field.
 * Based on (Read: Copy-Paste Modify) {@link EditablePropertyColumn}
 *
 * @author Tom Burton
 */
public class DateTextFieldPanel<M, I> extends EditableCellPanel<M, I, Date>
{
	private static final long serialVersionUID = 1L;

	private static final String DateTextField_ID = "dateTextField";

	protected class DefaultDateTextField extends DateTextField
  {
		private static final long serialVersionUID = 1L;

		/** Constructor for DefaultDateTextField
		 *  @param id component Id
		 *  @param object model to be edited
		 */
		protected DefaultDateTextField(String id, IModel<Date> object)
    { super(id, object, new StyleDateConverter(false)); }

    /** Constructor for DefaultDateTextField
		 *  @param id component Id
		 *  @param object model to be edited
     *  @param dc DateConverter to use
     *  @see DateTextField#DateTextField(String, IModel, DateConverter)
		 */
		protected DefaultDateTextField(String id, IModel<Date> object,
                                   DateConverter dc)
    { super(id, object, dc); }

    /** Constructor for DefaultDateTextField
		 *  @param id component Id
		 *  @param object model to be edited
     *  @param applyTimeZoneDifference whether or not to apply the Time zone difference
     *  @param dateStyle date style to use for date display
     *  @see DateTextField#DateTextField(String, IModel, DateConverter)
		 */
		protected DefaultDateTextField(String id, IModel object,
                                   boolean applyTimeZoneDifference, String dateStyle)
    { super(id, object, new StyleDateConverter(dateStyle, applyTimeZoneDifference)); }

		/** {@inheritDoc} */
    @Override
		protected void onComponentTag(ComponentTag tag)
    {
			super.onComponentTag(tag);
			
			if (!isValid())
      {
				tag.put("class", "imxt-invalid");
				FeedbackMessage message = getFeedbackMessage();
				if (message != null)
        {
          tag.put("title", message.getMessage().toString());
        }
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
   *    model for the data row
	 * @param column
	 * 		column to which this panel belongs
   * @param dc
   *    Converter for the data to display
	 */
	public DateTextFieldPanel(String id, final IModel model, IModel rowModel,
                            AbstractColumn column,
                            DateConverter dc)
  {
		super(id, column, rowModel);
		
		DateTextField tf = newDateTextField(DateTextField_ID, model, dc);
		tf.setOutputMarkupId(true);
		tf.setLabel(column.getHeaderModel());
		add(tf);		
	}

	/** newDateTextField
	 * 
	 *  @param id component Id
	 *  @param model model of the field to be edited
   *  @param dc DateConverter
   *  @see DateTextField#DateTextField(String, IModel, DateConverter)
	 *  @return DateTextField
	 */
	protected DateTextField newDateTextField(final String id, final IModel model,
                                           DateConverter dc)
  {
		return new DefaultDateTextField(id, model, dc);
	}


	@Override
	public FormComponent getEditComponent() {
		return (FormComponent) get(DateTextField_ID);
	}
}
