package com.googlecode.wicket.jquery.ui.samples.pages.sortable;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;



abstract class AbstractSortablePage extends SamplePage
{
	private static final long serialVersionUID = 1L;

	public AbstractSortablePage()
	{

	}

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(DefaultSortablePage.class, "Sortable"),
				new DemoLink(CustomSortablePage.class, "Sortable: custom beans"),
				new DemoLink(ConnectSortablePage.class, "Sortable: connect with"),
				new DemoLink(SelectableSortablePage.class, "Sortable, with Selectable")
			);
	}
}
