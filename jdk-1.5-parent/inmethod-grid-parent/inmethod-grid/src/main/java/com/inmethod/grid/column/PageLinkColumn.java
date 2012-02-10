package com.inmethod.grid.column;

import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * Created by IntelliJ IDEA.
 * User: Tom Burton
 * Date: 1/3/12
 * Time: 3:50 PM
 *
 * @author Tom Burton
 *         To change this template use File | Settings | File Templates.
 */
public class PageLinkColumn<M, I> extends LinkColumn<M, I>
{
  private Page page;

  public PageLinkColumn(String columnId, String propertyLabel,
                        IModel<String> headerModel,
                        Page page)
  {
    super(columnId, propertyLabel, headerModel);
    this.page = page;
  }

  public PageLinkColumn(String columnId, String propertyLabel,
                        IModel<String> headerModel,
                        String sortProperty, Page page)
  {
    super(columnId, propertyLabel, headerModel, sortProperty);
    this.page = page;
  }

  @Override
  public void onClick(IModel<I> rowModel)
  {
    //To change body of implemented methods use File | Settings | File
    // Templates.
    RequestCycle.get().setResponsePage(page);
  }

}
