package com.inmethod.grid.column;

import org.apache.wicket.PageReference;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;

/**
 * Displays a Link in the DataGrid to an Internal Wicket Page
 * @author Tom Burton
 */
public class PageLinkColumn<M, I, S> extends LinkColumn<M, I, S>
{
	private PageReference pageRef;

	public PageLinkColumn(String columnId, String propertyLabel, IModel<String> headerModel, PageReference page)
	{
		super(columnId, propertyLabel, headerModel);
		this.pageRef = page;
	}

	public PageLinkColumn(String columnId, String propertyLabel, IModel<String> headerModel, S sortProperty, PageReference page)
	{
		super(columnId, propertyLabel, headerModel, sortProperty);
		this.pageRef = page;
	}

	@Override
	public void onClick(IModel<I> rowModel)
	{
		RequestCycle.get().setResponsePage(pageRef.getPage());
	}

}
