package com.googlecode.wicket.jquery.ui.samples.pages.kendo.notification;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.pages.kendo.AbstractKendoPage;



abstract class AbstractNotificationPage extends AbstractKendoPage
{
	private static final long serialVersionUID = 1L;

	public AbstractNotificationPage()
	{
	}

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(DefaultNotificationPage.class, "Notification"),
				new DemoLink(FeedbackPanelPage.class, "FeedbackPanel")
			);
	}
}
