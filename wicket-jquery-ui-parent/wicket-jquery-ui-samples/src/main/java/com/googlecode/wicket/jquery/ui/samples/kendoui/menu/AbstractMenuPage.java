package com.googlecode.wicket.jquery.ui.samples.kendoui.menu;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.KendoSamplePage;

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
