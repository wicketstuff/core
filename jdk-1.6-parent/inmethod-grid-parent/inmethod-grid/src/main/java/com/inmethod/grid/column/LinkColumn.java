package com.inmethod.grid.column;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import com.inmethod.grid.IRenderable;

/**
 * Column to display links in the DataGrid
 *
 * @author Tom Burton
 */
public abstract class LinkColumn<M, I, S> extends AbstractColumn<M, I, S>
{
	private String propertyLabel;

	public LinkColumn(String columnId, String propertyLabel, IModel<String> headerModel)
	{
		super(columnId, headerModel);
		this.propertyLabel = propertyLabel;
	}

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

	public String getPropertyLabel()
	{
		return propertyLabel;
	}

	public void setPropertyLabel(String propertyLabel)
	{
		this.propertyLabel = propertyLabel;
	}

	//should this work Like Pagelink instead?
	public abstract void onClick(IModel<I> rowModel);
}
