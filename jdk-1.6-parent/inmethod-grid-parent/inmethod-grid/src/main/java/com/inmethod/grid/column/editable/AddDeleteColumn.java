package com.inmethod.grid.column.editable;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

import com.inmethod.grid.IAppendableDataSource;
import com.inmethod.grid.IDataSource;
import com.inmethod.grid.common.AbstractGrid;
import com.inmethod.grid.common.Icons;
import com.inmethod.grid.datagrid.DataGrid;
import com.inmethod.icon.Icon;

/**
 * Created by IntelliJ IDEA.
 * User: Tom Burton
 * Date: 12/27/11
 * Time: 1:15 PM
 *
 * @author Tom Burton
 *         To change this template use File | Settings | File Templates.
 */
public class AddDeleteColumn<M, I> extends SubmitCancelColumn<M, I>
{
  public AddDeleteColumn(String columnId, IModel<String> headerModel)
  {
    super(columnId, headerModel);
  }

  //TODO: check how much this idiom is used, look at refactoring
  @Override
	public Component newCell(WebMarkupContainer parent, String componentId,
                           final IModel<I> rowModel)
  {
		return new AddDeletePanel<M, I>(componentId, rowModel, getGrid())
    {
			private static final long serialVersionUID = 1L;

			private WebMarkupContainer getRowComponent()
      {	return getGrid().findParentRow(this); };

			@Override
			protected void onCancel(AjaxRequestTarget target)
      {
				AddDeleteColumn.this.onCancel(target, rowModel, getRowComponent());
			}

			@Override
			protected void onError(AjaxRequestTarget target)
      {
				AddDeleteColumn.this.onError(target, rowModel, getRowComponent());
			}

			@Override
			protected void onSubmitted(AjaxRequestTarget target)
      {
				AddDeleteColumn.this.onSubmitted(target, rowModel, getRowComponent());
			}

      @Override
      protected void onDelete(AjaxRequestTarget target)
      {
        AddDeleteColumn.this.onDelete(target,rowModel,getRowComponent());
      }

      @Override
			protected Icon getSubmitIcon()
      {
				return AddDeleteColumn.this.getSubmitIcon();
			}

			@Override
			protected Icon getCancelIcon()
      {
				return AddDeleteColumn.this.getCancelIcon();
			}

      @Override
      protected Icon getDeleteIcon()
      {
        return AddDeleteColumn.this.getDeleteIcon();
      }
    };
	}

  protected Icon getDeleteIcon() { return Icons.DELETE; }

  protected void onDelete(AjaxRequestTarget target, IModel<I> rowModel,
                          WebMarkupContainer rowComponent)
  {
    AbstractGrid ag = getGrid(); //check for only record on page
    if ( ag instanceof DataGrid )
    {
      DataGrid dg = (DataGrid)ag;
      long rows = dg.getRowsPerPage();
      IDataSource ds = dg.getDataSource();
      if ( ds instanceof IAppendableDataSource )
      {
        IAppendableDataSource ads = (IAppendableDataSource)ds;
        //TODO: get current row
        ads.DeleteRow(0,rowModel.getObject());
      }
      if ( dg.getCurrentPageItemCount() == 1) //only item on page
      {
        if ( 1 == dg.getCurrentPage() ) //only page
        { dg.markAllItemsDirty(); } //should trigger to show "no Items found"
        dg.setCurrentPage(dg.getCurrentPage()-1); //no items on page so back-up
      }
    }

    ag.markItemDirty(rowModel);
    ag.update();
  }

}
