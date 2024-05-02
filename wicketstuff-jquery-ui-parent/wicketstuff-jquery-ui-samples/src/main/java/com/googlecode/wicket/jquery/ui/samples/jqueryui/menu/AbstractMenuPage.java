package com.googlecode.wicket.jquery.ui.samples.jqueryui.menu;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.JQuerySamplePage;

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
