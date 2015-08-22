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
import org.apache.wicket.request.cycle.RequestCycle;

public abstract class EditableCellPanel<M, I, P, S> extends Panel
{

	private static final long serialVersionUID = 1L;
	private final AbstractColumn<M, I, S> column;

	public EditableCellPanel(String id, AbstractColumn<M, I, S> column, IModel<I> rowModel)
	{
		super(id, rowModel);
		this.column = column;
	}

	public AbstractColumn<M, I, S> getColumn()
	{
		return column;
	}

	@Override
	protected void onBeforeRender()
	{
		super.onBeforeRender();

		AjaxRequestTarget target = RequestCycle.get().find(AjaxRequestTarget.class);

		Component textField = get("textfield");

		if (target != null && isFocusTextField())
		{
			target.focusComponent(textField);
		}
	}

	protected boolean isFocusTextField()
	{
		IGridColumn<M, I, S> lastClickedColumn = getColumn().getGrid().getLastClickedColumn();
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

	@SuppressWarnings("unchecked")
	protected IModel<I> getDefaultRowModel()
	{
		return (IModel<I>)getDefaultModel();
	}

	@Override
	public boolean isVisible()
	{
		return column.getGrid().isItemEdited(getDefaultRowModel());
	}

	protected abstract FormComponent<P> getEditComponent();

}