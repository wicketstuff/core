package com.googlecode.wicket.jquery.ui.samples.pages.kendo.splitter;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;



abstract class AbstractSplitterPage extends SamplePage
{
	private static final long serialVersionUID = 1L;

	public AbstractSplitterPage()
	{

	}

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(DefaultSplitterPage.class, "Splitter Behavior"),
				new DemoLink(BorderLayoutPage.class, "Border Layout")
//				new DemoLink(DynamicEffectPage.class, "Effect Behavior: dynamic"),
//				new DemoLink(ContainerEffectPage.class, "Effect Container")
			);
	}
}
