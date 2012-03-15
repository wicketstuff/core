package com.inmethod.grid.column.editable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.IModel;

import com.inmethod.grid.common.AbstractGrid;
import com.inmethod.icon.Icon;
import com.inmethod.icon.IconImage;

/**
 * Panel for the AddDeleteColumn displays: Add, Cancel, and Delete buttons
 *
 * Created by IntelliJ IDEA.
 * User: Tom Burton
 * Date: 12/27/11
 * Time: 12:01 PM
 *
 * @author Tom Burton
 */
public abstract class AddDeletePanel<M, I> extends SubmitCancelPanel<M, I>
{
  protected AddDeletePanel(String id, final IModel<I> model,
                           AbstractGrid<M, I> grid)
  {
    super(id,model,grid);
    AjaxLink<Void> cancel = new AjaxLink<Void>("delete")
    {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target)
      {
				onDelete(target);
			}
	
			@Override
			public boolean isVisible()
      {
				return getGrid().isItemEdited(model);
			}			
		};
		
		add(cancel);
		
		cancel.add(new IconImage("icon", getDeleteIcon()));
  }

  protected abstract Icon getDeleteIcon();

  protected abstract void onDelete(AjaxRequestTarget target);
}
