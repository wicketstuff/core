package com.googlecode.wicket.jquery.ui.samples.jqueryui.selectable;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.JQuerySamplePage;

abstract class AbstractSelectablePage extends JQuerySamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(DefaultSelectablePage.class, "Selectable"), // lf
				new DemoLink(DraggableSelectablePage.class, "Selectable, with draggable (on list items)"), // lf
				new DemoLink(TableDraggableSelectablePage.class, "Selectable, with customized draggable (on table rows)") // lf
		);
	}
}
