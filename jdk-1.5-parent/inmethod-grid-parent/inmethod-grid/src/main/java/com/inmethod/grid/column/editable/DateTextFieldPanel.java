package com.inmethod.grid.column.editable;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.datetime.StyleDateConverter;

import com.inmethod.grid.column.AbstractColumn;

/**
 * Panel with a TextField that updates the property of the row immediately after
 * user leaves the field.
 * 
 * @author Matej Knopp
 */
public class DateTextFieldPanel extends EditableCellPanel
{
	private static final String TEXTFIELD_ID = "textfield";

	protected class DefaultDateTextField extends DateTextField
  {
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor for DefaultTextField
		 * 
		 * @param id
		 * @param object
		 */
		protected DefaultDateTextField(String id, IModel object)
    { super(id, object, new StyleDateConverter(false)); }

    /**
  	 * Constructor for DefaultTextField
		 *
		 * @param id
		 * @param object
		 */
		protected DefaultDateTextField(String id, IModel object,
                                   boolean applyTimeZoneDifference)
    { super(id, object, new StyleDateConverter(applyTimeZoneDifference)); }
    /**
  	 * Constructor for DefaultTextField
		 *
		 * @param id
		 * @param object
		 */
		protected DefaultDateTextField(String id, IModel object,
                                   boolean applyTimeZoneDifference, String dateStyle)
    { super(id, object, new StyleDateConverter(dateStyle, applyTimeZoneDifference)); }

		@Override
		protected void onComponentTag(ComponentTag tag)
    {
			super.onComponentTag(tag);
			
			if (isValid() == false) {
				tag.put("class", "imxt-invalid");
				FeedbackMessage message = getFeedbackMessage();
				if (message != null) {
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
	 * @param column
	 * 		column to which this panel belongs
	 */
	public DateTextFieldPanel(String id, final IModel model, IModel rowModel,
                            AbstractColumn column,
                            boolean applyTimeZoneDifference, String dateStyle)
  {
		super(id, column, rowModel);
		
		DateTextField tf = newDateTextField(TEXTFIELD_ID, model,
                                        applyTimeZoneDifference, dateStyle);
		tf.setOutputMarkupId(true);
		tf.setLabel(column.getHeaderModel());
		add(tf);		
	}

	/**
	 * newTextField
	 * 
	 * @param id
	 * @param model
	 * @return TextField
	 */
	protected DateTextField newDateTextField(final String id, final IModel model,
                                           boolean applyTimeZoneDifference,
                                           String dateStyle)
  {
		return new DefaultDateTextField(id, model,applyTimeZoneDifference,dateStyle);
	}
		
	@Override
	public FormComponent getEditComponent() {
		return (FormComponent) get(TEXTFIELD_ID);
	}
	
	private static final long serialVersionUID = 1L;
	
}
