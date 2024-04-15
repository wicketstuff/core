package com.googlecode.wicket.jquery.ui.samples.kendoui.console;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.KendoSamplePage;

abstract class AbstractConsolePage extends KendoSamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(DefaultConsolePage.class, "Console"), // lf
				new DemoLink(FeedbackConsolePage.class, "FeedbackConsole") // lf
		);
	}
}
