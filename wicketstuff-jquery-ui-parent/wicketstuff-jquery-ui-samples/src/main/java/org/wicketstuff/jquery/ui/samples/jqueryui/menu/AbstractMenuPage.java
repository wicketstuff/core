/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.jqueryui.menu;

import java.util.Arrays;
import java.util.List;

import org.wicketstuff.jquery.ui.samples.JQuerySamplePage;

abstract class AbstractMenuPage extends JQuerySamplePage // NOSONAR
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(DefaultMenuPage.class, "Menu"), // lf
				new DemoLink(ContextMenuPage.class, "Context Menu") // lf
		);
	}
}
