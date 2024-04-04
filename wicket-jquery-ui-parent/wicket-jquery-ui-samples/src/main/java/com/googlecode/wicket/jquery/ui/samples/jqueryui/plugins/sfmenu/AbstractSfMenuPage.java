package com.googlecode.wicket.jquery.ui.samples.jqueryui.plugins.sfmenu;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.JQuerySamplePage;

abstract class AbstractSfMenuPage extends JQuerySamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(DefaultSfMenuPage.class, "Horizontal Menu"), // lf
				new DemoLink(VerticalSfMenuPage.class, "Vertical Menu") // lf
		);
	}
}
