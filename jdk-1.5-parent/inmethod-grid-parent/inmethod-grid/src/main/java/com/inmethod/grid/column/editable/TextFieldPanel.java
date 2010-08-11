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
		
		TextField tf = new TextField("textfield", model) {
			
			private static final long serialVersionUID = 1L;

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
		};
		tf.setOutputMarkupId(true);
		tf.setLabel(column.getHeaderModel());
		add(tf);		
	}
		
	@Override
	protected FormComponent getEditComponent() {
		return (FormComponent) get("textfield");
	}
	
	private static final long serialVersionUID = 1L;
	
}