package com.googlecode.wicket.jquery.ui.samples.pages.effect;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;



abstract class AbstractEffectPage extends SamplePage
{
	private static final long serialVersionUID = 1L;
	
	public AbstractEffectPage()
	{
		
	}
	
	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(DefaultEffectPage.class, "Effect Behavior"),
				new DemoLink(DynamicEffectPage.class, "Effect Behavior: dynamic"),
				new DemoLink(ContainerEffectPage.class, "Effect Container")
			);
	}
}
