package com.googlecode.wicket.jquery.ui.samples.kendoui.tooltip;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.KendoSamplePage;

abstract class AbstractTooltipPage extends KendoSamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(DefaultKendoTooltipPage.class, "Tooltip Behavior, using strings"), // lf
				new DemoLink(ComponentKendoTooltipPage.class, "Tooltip Behavior, using Components") // lf
			);
	}
}
