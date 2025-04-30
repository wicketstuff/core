/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.jqueryui.selectable;

import java.util.Arrays;
import java.util.List;

import org.wicketstuff.jquery.ui.samples.JQuerySamplePage;

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
