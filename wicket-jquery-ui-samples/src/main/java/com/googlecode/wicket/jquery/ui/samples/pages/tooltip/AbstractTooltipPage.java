package com.googlecode.wicket.jquery.ui.samples.pages.tooltip;

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
				new DemoLink(DefaultTooltipPage.class, "Tooltip behavior"),
				new DemoLink(CustomTooltipPage.class, "Custom tooltips")
			);
	}
}
