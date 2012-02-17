package com.inmethod.grid.column.editable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.IModel;

import com.inmethod.grid.common.AbstractGrid;
import com.inmethod.icon.Icon;
import com.inmethod.icon.IconImage;

/**
 * Created by IntelliJ IDEA.
 * User: tfburton
 * Date: 12/27/11
 * Time: 12:01 PM
 *
 * @author tfburton
 *         To change this template use File | Settings | File Templates.
 */
public abstract class AddDeletePanel extends SubmitCancelPanel
{
  protected AddDeletePanel(String id,final IModel model, AbstractGrid grid)
  {
    super(id,model,grid);
    AjaxLink cancel = new AjaxLink("delete") {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				onDelete(target);
			}
	
			@Override
			public boolean isVisible() {
				return getGrid().isItemEdited(model);
			}			
		};
		
		add(cancel);
		
		cancel.add(new IconImage("icon", getDeleteIcon()));
  }

  protected abstract Icon getDeleteIcon();

  protected abstract void onDelete(AjaxRequestTarget target);
}
