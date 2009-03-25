package com.inmethod.grid.examples;

import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.target.coding.HybridUrlCodingStrategy;

import com.inmethod.grid.examples.pages.datagrid.DataGridColumnPropertiesPage;
import com.inmethod.grid.examples.pages.datagrid.DataGridSelectionPage;
import com.inmethod.grid.examples.pages.datagrid.DataGridWithUnknownItemCount;
import com.inmethod.grid.examples.pages.datagrid.EditableDataGridPage;
import com.inmethod.grid.examples.pages.datagrid.EditableDataGridWithSelectionPage;
import com.inmethod.grid.examples.pages.datagrid.SimpleDataGridPage;
import com.inmethod.grid.examples.pages.datagrid.VerticalScrollingDataGridPage;
import com.inmethod.grid.examples.pages.treegrid.EditableTreeGridPage;
import com.inmethod.grid.examples.pages.treegrid.SimpleTreeGridPage;
import com.inmethod.grid.examples.pages.treegrid.TreeGridColumnPropertiesPage;
import com.inmethod.grid.examples.pages.treegrid.TreeGridSelectionPage;
import com.inmethod.grid.examples.pages.treegrid.VerticalScrollingTreeGridPage;

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
	protected void init() {
		mount(new HybridUrlCodingStrategy("/data-grid/simple", SimpleDataGridPage.class));
		mount(new HybridUrlCodingStrategy("/data-grid/vertical-scrolling", VerticalScrollingDataGridPage.class));
		mount(new HybridUrlCodingStrategy("/data-grid/item-selection", DataGridSelectionPage.class));
		mount(new HybridUrlCodingStrategy("/data-grid/column-properties", DataGridColumnPropertiesPage.class));
		mount(new HybridUrlCodingStrategy("/data-grid/editable", EditableDataGridPage.class));
		mount(new HybridUrlCodingStrategy("/data-grid/editable-selection", EditableDataGridWithSelectionPage.class));
		mount(new HybridUrlCodingStrategy("/data-grid/unknown-count", DataGridWithUnknownItemCount.class));
		
		mount(new HybridUrlCodingStrategy("/tree-grid/simple", SimpleTreeGridPage.class));
		mount(new HybridUrlCodingStrategy("/tree-grid/vertical-scrolling", VerticalScrollingTreeGridPage.class));
		mount(new HybridUrlCodingStrategy("/tree-grid/item-selection", TreeGridSelectionPage.class));
		mount(new HybridUrlCodingStrategy("/tree-grid/column-properties", TreeGridColumnPropertiesPage.class));
		mount(new HybridUrlCodingStrategy("/tree-grid/editable", EditableTreeGridPage.class));		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Session newSession(Request request, Response response) {
		return new Session(request);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Class<? extends Page> getHomePage()
	{
		return (Class<? extends Page>) SimpleDataGridPage.class;
	}
  
}
