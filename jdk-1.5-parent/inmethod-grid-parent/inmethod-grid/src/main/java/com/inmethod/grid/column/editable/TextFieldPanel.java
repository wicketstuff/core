/**
 * 
 */
package com.inmethod.grid.column.editable;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

import com.inmethod.grid.column.AbstractColumn;

/**
 * Panel with a TextField that updates the property of the row immediately after
 * user leaves the field.
 * 
 * @author Matej Knopp
 */
public class TextFieldPanel extends EditableCellPanel {
	private static final String TEXTFIELD_ID = "textfield";

	protected class DefaultTextField extends TextField {
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor for DefaultTextField
		 * 
		 * @param id
		 * @param object
		 */
		protected DefaultTextField(String id, IModel object) {
			super(id, object);
		}

		@Override
		protected void onComponentTag(ComponentTag tag) {
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
	public TextFieldPanel(String id, final IModel model, IModel rowModel, AbstractColumn column) {
		super(id, column, rowModel);
		
		TextField tf = newTextField(TEXTFIELD_ID, model);
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
	protected TextField newTextField(final String id, final IModel model) {
		return new DefaultTextField(id, model);
	}
		
	@Override
	public FormComponent getEditComponent() {
		return (FormComponent) get(TEXTFIELD_ID);
	}
	
	private static final long serialVersionUID = 1L;
	
}
