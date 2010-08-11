/**
 * 
 */
package com.inmethod.grid.column.editable;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.column.AbstractColumn;

public abstract class EditableCellPanel extends Panel {

	private final AbstractColumn column;			
	
	public EditableCellPanel(String id, AbstractColumn column, IModel rowModel) {
		super(id, rowModel);
		this.column = column;
	}
	
	public AbstractColumn getColumn() {
		return column;
	}
	
	@Override
	protected void onBeforeRender() {		
		super.onBeforeRender();
		
		AjaxRequestTarget target = AjaxRequestTarget.get();
		
		Component textField = get("textfield");
		
		if (target != null && isFocusTextField()) {
			target.focusComponent(textField);
		}		
	}
	
	protected boolean isFocusTextField() {
		IGridColumn lastClickedColumn = getColumn().getGrid().getLastClickedColumn();				
		if (lastClickedColumn == getColumn()) {
			getColumn().getGrid().cleanLastClickedColumn();
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean isVisible() {
		return column.getGrid().isItemEdited(getDefaultModel());		
	}
	
	protected abstract FormComponent getEditComponent();
	
}