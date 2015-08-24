package com.googlecode.wicket.jquery.ui.samples.pages.kendo.datatable;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;

abstract class AbstractDataTablePage extends SamplePage
{
	private static final long serialVersionUID = 1L;

	public AbstractDataTablePage()
	{
	}

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(DefaultDataTablePage.class, "DataTable"),
				new DemoLink(CommandsDataTablePage.class, "DataTable, with commands"),
				new DemoLink(InlineDataTablePage.class, "DataTable, with inline editing"),
				new DemoLink(InfiniteDataTablePage.class, "DataTable, with infinite scroll")
			);
	}
}
