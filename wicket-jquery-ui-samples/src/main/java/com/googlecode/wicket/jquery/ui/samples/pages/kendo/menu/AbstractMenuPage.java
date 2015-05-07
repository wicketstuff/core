package com.googlecode.wicket.jquery.ui.samples.pages.kendo.menu;

import java.util.Arrays;
import java.util.List;

import com.googlecode.wicket.jquery.ui.samples.SamplePage;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;


abstract class AbstractMenuPage extends SamplePage
{
	private static final long serialVersionUID = 1L;

	protected final KendoFeedbackPanel feedback;

	public AbstractMenuPage()
	{
		// FeedbackPanel //
		this.feedback = new KendoFeedbackPanel("feedback");
		this.add(feedback);
	}

	@Override
	protected List<DemoLink> getDemoLinks()
	{
		return Arrays.asList(
				new DemoLink(KendoMenuPage.class, "Menu"),
				new DemoLink(KendoContextMenuPage.class, "Context Menu")
//				new DemoLink(AjaxMenuPage.class, "Ajax Menu")
			);
	}
}
