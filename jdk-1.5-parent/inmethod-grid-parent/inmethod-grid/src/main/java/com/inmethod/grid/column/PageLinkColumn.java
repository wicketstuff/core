package com.inmethod.grid.column;

import org.apache.wicket.Page;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.model.IModel;

/**
 * Created by IntelliJ IDEA.
 * User: tfburton
 * Date: 1/3/12
 * Time: 3:50 PM
 *
 * @author tfburton
 *         To change this template use File | Settings | File Templates.
 */
public class PageLinkColumn extends LinkColumn
{
  private Page page;

  public PageLinkColumn(String columnId, String propertyLabel,
                        IModel headerModel,
                        Page page)
  {
    super(columnId, propertyLabel, headerModel);
    this.page = page;
  }

  public PageLinkColumn(String columnId, String propertyLabel,
                        IModel headerModel,
                        String sortProperty, Page page)
  {
    super(columnId, propertyLabel, headerModel, sortProperty);
    this.page = page;
  }

  @Override
  public void onClick() { RequestCycle.get().setResponsePage(page); }

}
