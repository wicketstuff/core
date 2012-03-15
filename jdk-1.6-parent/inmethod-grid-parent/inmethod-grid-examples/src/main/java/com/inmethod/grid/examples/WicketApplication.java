package com.inmethod.grid.examples;

import org.apache.wicket.Page;
import org.apache.wicket.core.request.mapper.MountedMapper;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;

import com.inmethod.grid.examples.pages.datagrid.*;
import com.inmethod.grid.examples.pages.treegrid.*;

/**
 *
 */
public class WicketApplication extends WebApplication
{
	/**
	 * Constructor
	 */
	public WicketApplication()
	{
	}

	@Override
	protected void init()
	{
		mount("/data-grid/simple", SimpleDataGridPage.class);
		mount("/data-grid/vertical-scrolling", VerticalScrollingDataGridPage.class);
		mount("/data-grid/item-selection", DataGridSelectionPage.class);
		mount("/data-grid/column-properties", DataGridColumnPropertiesPage.class);
		mount("/data-grid/editable", EditableDataGridPage.class);
		mount("/data-grid/editable-selection", EditableDataGridWithSelectionPage.class);
		mount("/data-grid/unknown-count", DataGridWithUnknownItemCount.class);

		mount("/tree-grid/simple", SimpleTreeGridPage.class);
		mount("/tree-grid/vertical-scrolling", VerticalScrollingTreeGridPage.class);
		mount("/tree-grid/item-selection", TreeGridSelectionPage.class);
		mount("/tree-grid/column-properties", TreeGridColumnPropertiesPage.class);
		mount("/tree-grid/editable", EditableTreeGridPage.class);
	}

	private void mount(String mountPath, Class<? extends WebPage> pageClass)
	{
		getRootRequestMapperAsCompound().add(new MountedMapper(mountPath, pageClass));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Session newSession(Request request, Response response)
	{
		return new Session(request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<? extends Page> getHomePage()
	{
		return SimpleDataGridPage.class;
	}

}
