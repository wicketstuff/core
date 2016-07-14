package com.googlecode.wicket.jquery.ui.samples.kendoui.window;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.KendoSamplePage;

abstract class AbstractWindowPage extends KendoSamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(DefaultWindowPage.class, "Window"), // lf
				new DemoLink(ActionWindowPage.class, "Window: actions"), // lf
				new DemoLink(MessageWindowPage.class, "Message Window"), // lf
				new DemoLink(InputWindowPage.class, "Input Window") // lf
		);
	}
}
