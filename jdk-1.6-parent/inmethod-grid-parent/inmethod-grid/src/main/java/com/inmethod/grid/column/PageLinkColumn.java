package com.inmethod.grid.column;

import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * Displays a Link in the DataGrid to an Internal Wicket Page
 * @author Tom Burton
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
    RequestCycle.get().setResponsePage(page);
  }

}
