package com.inmethod.grid.column;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import com.inmethod.grid.IRenderable;

/**
 * Column to display links in the DataGrid
 * Created by IntelliJ IDEA.
 * User: Tom Burton
 * Date: 1/3/12
 * Time: 2:07 PM
 *
 * @author Tom Burton
 */
public abstract class LinkColumn<M, I> extends AbstractColumn<M, I>
{
  private String propertyLabel;

  public LinkColumn(String columnId, String propertyLabel,
                    IModel<String> headerModel)
  {
    super(columnId, headerModel);
    this.propertyLabel = propertyLabel;
  }

  public LinkColumn(String columnId, String propertyLabel,
                    IModel<String> headerModel, String sortProperty)
  {
    super(columnId, headerModel, sortProperty);
    this.propertyLabel = propertyLabel;
  }

  /** {@inheritDoc} */
  @Override
  public IRenderable newCell(IModel rowModel)
  {
    return null;
    /*
    return new LinkPanel("panel",
                         new PropertyModel(rowModel.getObject(),
                                           getPropertyLabel())
                                     .getTarget().toString(),
                         rowModel)
    { @Override public void onClick() { LinkColumn.this.onClick(); } };
    */
  }

  /** {@inheritDoc} */
  @Override
  public Component newCell(WebMarkupContainer parent, String componentId,
                           final IModel<I> rowModel)
  {
    //return new Link("link",rowModel);
    return new LinkPanel<M, I>(componentId,
                               new PropertyModel(rowModel.getObject(),
                                                 getPropertyLabel())
                                           .getTarget().toString(),
                               rowModel)
               {
                  @Override
                  public void onClick()
                  { LinkColumn.this.onClick(rowModel); }
               };
  }

  public String getPropertyLabel() { return propertyLabel; }

  public void setPropertyLabel(String propertyLabel)
  { this.propertyLabel = propertyLabel; }

  //TODO: should this work Like Page link? instead?
  public abstract void onClick(IModel<I> rowModel);
}
