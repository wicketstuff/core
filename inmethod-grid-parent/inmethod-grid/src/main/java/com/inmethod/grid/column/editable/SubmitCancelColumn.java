package com.inmethod.grid.column.editable;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

import com.inmethod.grid.SizeUnit;
import com.inmethod.grid.column.AbstractColumn;
import com.inmethod.grid.common.Icons;
import com.inmethod.icon.Icon;

public class SubmitCancelColumn<M, I, S> extends AbstractColumn<M, I, S>
{

	private static final long serialVersionUID = 1L;

	public SubmitCancelColumn(String columnId, IModel<String> headerModel)
	{
		super(columnId, headerModel);

		setResizable(false);
		setSizeUnit(SizeUnit.PX);
		setInitialSize(44);
	}

	@Override
	public Component newCell(WebMarkupContainer parent, String componentId, final IModel<I> rowModel)
	{
		return new SubmitCancelPanel<M, I>(componentId, rowModel, getGrid())
		{

			private static final long serialVersionUID = 1L;

			private WebMarkupContainer getRowComponent()
			{
				return getGrid().findParentRow(this);
			};

			@Override
			protected void onCancel(AjaxRequestTarget target)
			{
				SubmitCancelColumn.this.onCancel(target, rowModel, getRowComponent());
			}

			@Override
			protected void onError(AjaxRequestTarget target)
			{
				SubmitCancelColumn.this.onError(target, rowModel, getRowComponent());
			}

			@Override
			protected void onSubmitted(AjaxRequestTarget target)
			{
				SubmitCancelColumn.this.onSubmitted(target, rowModel, getRowComponent());
			}

			@Override
			protected Icon getSubmitIcon()
			{
				return SubmitCancelColumn.this.getSubmitIcon();
			}

			@Override
			protected Icon getCancelIcon()
			{
				return SubmitCancelColumn.this.getCancelIcon();
			}
		};
	}

	protected Icon getSubmitIcon()
	{
		return Icons.OK;
	}

	protected Icon getCancelIcon()
	{
		return Icons.CANCEL;
	}

	protected void onCancel(AjaxRequestTarget target, IModel<I> rowModel,
		WebMarkupContainer rowComponent)
	{
		getGrid().setItemEdit(rowModel, false);
		getGrid().update();
	}

	protected void onError(AjaxRequestTarget target, IModel<I> rowModel,
		WebMarkupContainer rowComponent)
	{
		// just update the row
		target.add(rowComponent);
	}

	protected void onSubmitted(AjaxRequestTarget target, IModel<I> rowModel,
		WebMarkupContainer rowComponent)
	{
		getGrid().setItemEdit(rowModel, false);
		getGrid().update();
	}

	@Override
	public boolean cellClicked(IModel<I> rowModel)
	{
		if (getGrid().isClickRowToSelect() && getGrid().isSelectToEdit())
		{
			return false;
		}
		else
		{
			getGrid().setItemEdit(rowModel, true);
			getGrid().update();
			return true;
		}
	}

	@Override
	public String getCellCssClass(IModel<I> rowModel, int rowNum)
	{
		return getGrid().isItemEdited(rowModel) ? "imxt-edit" : "imxt-want-prelight imxt-edit";
	}

}
