package com.inmethod.grid.column;

import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * Concrete example implementation of {@link LinkColumn}
 * that links to a specific page
 *
 * @author Tom Burton
 *
 * TODO: should PageParameters support be added?
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
