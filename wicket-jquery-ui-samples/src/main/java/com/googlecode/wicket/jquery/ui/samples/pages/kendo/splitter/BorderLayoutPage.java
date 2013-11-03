package com.googlecode.wicket.jquery.ui.samples.pages.kendo.splitter;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import com.googlecode.wicket.jquery.ui.kendo.splitter.BorderLayout;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class BorderLayoutPage extends AbstractSplitterPage
{
	private static final long serialVersionUID = 1L;

	public BorderLayoutPage()
	{
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		this.add(feedback.setOutputMarkupId(true));

		this.add(new BorderLayout("layout") {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isExpandEventEnabled()
			{
				return true;
			}

			@Override
			public boolean isCollapseEventEnabled()
			{
				return true;
			}

			@Override
			public void onExpand(AjaxRequestTarget target, String paneId)
			{
				this.info(String.format("%s panel has been expanded", paneId));
				target.add(feedback);
			}

			@Override
			public void onCollapse(AjaxRequestTarget target, String paneId)
			{
				this.info(String.format("%s panel has been collapsed", paneId));
				target.add(feedback);
			}
		});
	}
}
