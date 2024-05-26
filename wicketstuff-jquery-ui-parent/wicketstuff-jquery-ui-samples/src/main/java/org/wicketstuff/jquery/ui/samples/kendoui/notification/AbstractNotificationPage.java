package org.wicketstuff.jquery.ui.samples.kendoui.notification;

import java.util.Arrays;
import java.util.List;

import org.wicketstuff.jquery.ui.samples.KendoSamplePage;

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
