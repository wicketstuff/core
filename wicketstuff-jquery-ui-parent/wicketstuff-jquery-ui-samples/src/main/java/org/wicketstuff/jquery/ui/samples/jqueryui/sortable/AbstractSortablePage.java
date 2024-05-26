package org.wicketstuff.jquery.ui.samples.jqueryui.sortable;

import java.util.Arrays;
import java.util.List;

import org.wicketstuff.jquery.ui.samples.JQuerySamplePage;

abstract class AbstractSortablePage extends JQuerySamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(new DemoLink(DefaultSortablePage.class, "Sortable"), new DemoLink(CustomSortablePage.class, "Sortable: custom beans"), new DemoLink(ConnectSortablePage.class, "Sortable: connect with"),
				new DemoLink(SelectableSortablePage.class, "Sortable, with Selectable"));
	}
}
