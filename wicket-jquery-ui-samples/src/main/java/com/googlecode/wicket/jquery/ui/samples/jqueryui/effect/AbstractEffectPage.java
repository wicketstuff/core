package com.googlecode.wicket.jquery.ui.samples.jqueryui.effect;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.JQuerySamplePage;

abstract class AbstractEffectPage extends JQuerySamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(DefaultEffectPage.class, "Effect Behavior"), // lf
				new DemoLink(DynamicEffectPage.class, "Effect Behavior: dynamic"), // lf
				new DemoLink(ContainerEffectPage.class, "Effect Container") // lf
		);
	}
}
