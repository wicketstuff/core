/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.kendoui.menu;

import java.util.Arrays;
import java.util.List;

import org.wicketstuff.jquery.ui.samples.KendoSamplePage;

abstract class AbstractMenuPage extends KendoSamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(KendoMenuPage.class, "Menu"), // lf
				new DemoLink(KendoContextMenuPage.class, "Context Menu") // lf
		// new DemoLink(AjaxMenuPage.class, "Ajax Menu") // lf
		);
	}
}
