package com.googlecode.wicket.jquery.ui.samples.pages.kendo.tooltip;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;


abstract class AbstractTooltipPage extends SamplePage
{
	private static final long serialVersionUID = 1L;

	public AbstractTooltipPage()
	{
	}

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(DefaultKendoTooltipPage.class, "Tooltip Behavior, using strings"),
				new DemoLink(ComponentKendoTooltipPage.class, "Tooltip Behavior, using Components")
			);
	}
}
