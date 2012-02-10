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

public abstract class EditableCellPanel<M, I, P> extends Panel
{
	private static final long serialVersionUID = 1L;

	private final AbstractColumn<M, I> column;

	public EditableCellPanel(String id, AbstractColumn<M, I> column, IModel<I> rowModel)
	{
		super(id, rowModel);
		this.column = column;
	}

	public AbstractColumn<M, I> getColumn()
	{
		return column;
	}

	@Override
	protected void onBeforeRender()
	{
		super.onBeforeRender();

		AjaxRequestTarget target = AjaxRequestTarget.get();

		Component textField = get("textfield");

		if (target != null && isFocusComponent())
		{
			target.focusComponent(textField);
		}
	}

	protected boolean isFocusTextField()
	{
		IGridColumn<M, I> lastClickedColumn = getColumn().getGrid().getLastClickedColumn();
		if (lastClickedColumn == getColumn())
		{
			getColumn().getGrid().cleanLastClickedColumn();
			return true;
		}
		else
		{
			return false;
		}
	}

  protected IModel<I> getDefaultRowModel()
	{
		return (IModel<I>)getDefaultModel();
	}

  /** @return boolean indicating visibility determined
   * by if the field has been edited or not
   */
	@Override
	public boolean isVisible()
	{
		return column.getGrid().isItemEdited(getDefaultRowModel());
	}

  //TODO: javadoc comment this
	protected abstract FormComponent<P> getEditComponent();

}