package com.googlecode.wicket.jquery.ui.samples.kendoui.datatable;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.KendoSamplePage;

abstract class AbstractDataTablePage extends KendoSamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
			new DemoLink(DefaultDataTablePage.class, "DataTable"), // lf
			new DemoLink(CommandsDataTablePage.class, "DataTable, with commands"), // lf
			new DemoLink(InlineDataTablePage.class, "DataTable, with inline editing"), // lf
			new DemoLink(InfiniteDataTablePage.class, "DataTable, with infinite scroll"), // lf
			new DemoLink(CheckboxDataTablePage.class, "DataTable with checkbox column") // lf
		);
	}
}
