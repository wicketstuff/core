package com.googlecode.wicket.jquery.ui.samples.jqueryui.progressbar;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.JQuerySamplePage;

abstract class AbstractProgressBarPage extends JQuerySamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(DefaultProgressBarPage.class, "Progress-bar Widget"), // lf
				new DemoLink(ButtonProgressBarPage.class, "Progress-bar: controlled by buttons"), // lf
				new DemoLink(SliderProgressBarPage.class, "Progress-bar: controlled by a slider") // lf
		);
	}
}
