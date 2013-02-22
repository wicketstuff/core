package com.inmethod.grid.column;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import com.inmethod.grid.IRenderable;
import com.inmethod.grid.datagrid.DataGrid;

/**
 * Abstract column to display links in a {@link DataGrid}
 *
 * @author Tom Burton
 */
public abstract class LinkColumn<M, I, S> extends AbstractColumn<M, I, S>
{
	private String propertyLabel;

  /**
   *  Create a new Link Column
   * @param columnId column id (must be unique within the grid)
   * @param propertyLabel Label to display for the Link,
   *        gotten from the model object itself ala {@link PropertyModel}
   * @param headerModel model object for the column header
   */
	public LinkColumn(String columnId, String propertyLabel, IModel<String> headerModel)
	{
		super(columnId, headerModel);
		this.propertyLabel = propertyLabel;
	}

  /**
   * Create a new Link Column
   * @param columnId column id (must be unique within the grid)
   * @param propertyLabel Label to display for the Link,
   *        gotten from the model object itself ala {@link PropertyModel}
   * @param headerModel model object for the column header
   * @param sortProperty property to sort the column by
   */
	public LinkColumn(String columnId, String propertyLabel, IModel<String> headerModel, S sortProperty)
	{
		super(columnId, headerModel, sortProperty);
		this.propertyLabel = propertyLabel;
	}

	@Override
	public IRenderable<I> newCell(IModel<I> rowModel)
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

	@Override
	public Component newCell(WebMarkupContainer parent, String componentId, final IModel<I> rowModel)
	{
		//return new Link("link",rowModel);
		return new LinkPanel<M, I>(componentId,
				new PropertyModel(rowModel, getPropertyLabel()).getInnermostModelOrObject().toString(), rowModel)
				{
					@Override
					public void onClick()
					{
						LinkColumn.this.onClick(rowModel);
					}
				};
	}

  /** @return row model object property name for the link Text */
	public String getPropertyLabel()
	{
		return propertyLabel;
	}

  /** @param propertyLabel row model object property name for the link Text */
	public void setPropertyLabel(String propertyLabel)
	{
		this.propertyLabel = propertyLabel;
	}

	/**
   * Called when a link is clicked.
   * @see Link#onClick()
   * @param rowModel Model Object for the DataGridRow being passed to the link
   */
	public abstract void onClick(IModel<I> rowModel);
}
