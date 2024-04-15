package com.googlecode.wicket.jquery.ui.samples.kendoui.notification;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.KendoSamplePage;

abstract class AbstractNotificationPage extends KendoSamplePage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList( // lf
				new DemoLink(DefaultNotificationPage.class, "Notification"), // lf
				new DemoLink(FeedbackPanelPage.class, "FeedbackPanel") // lf
		);
	}
}
