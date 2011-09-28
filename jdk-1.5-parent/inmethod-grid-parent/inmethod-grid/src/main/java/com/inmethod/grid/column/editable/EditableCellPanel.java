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
		if (target != null && isFocusComponent()) {
			target.focusComponent(getEditComponent());
		}		
	}
	
	/**
	 * @return true when the last clicked column is *this* column, false otherwise
	 */
	protected boolean isFocusComponent() {
		IGridColumn lastClickedColumn = getColumn().getGrid().getLastClickedColumn();				
		if (lastClickedColumn == getColumn()) {
			getColumn().getGrid().cleanLastClickedColumn();
			return true;
		} else {
			return false;
		}
	}

  /** @return boolean indicating visibility determined
   * by if the field has been edited or not
   */
	@Override
	public boolean isVisible() {
		return column.getGrid().isItemEdited(getDefaultModel());		
	}

  //TODO: javadoc comment this
	public abstract FormComponent getEditComponent();
	
}