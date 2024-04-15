package com.googlecode.wicket.jquery.ui.samples.kendoui.splitter;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.KendoSamplePage;

abstract class AbstractSplitterPage extends KendoSamplePage // NOSONAR
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(new DemoLink(DefaultSplitterPage.class, "Splitter Behavior"), // lf
				new DemoLink(BorderLayoutPage.class, "Border Layout") // lf
		// new DemoLink(DynamicEffectPage.class, "Effect Behavior: dynamic"),
		// new DemoLink(ContainerEffectPage.class, "Effect Container")
		);
	}
}
