package com.googlecode.wicket.jquery.ui.samples.pages.resizable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.interaction.resizable.ResizablePanel;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class ResizablePanelPage extends AbstractResizablePage
{
	private static final long serialVersionUID = 1L;

	public ResizablePanelPage()
	{
		// Feedback Panel//
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		this.add(feedback);

		// ResizablePanel //
		Options options = new Options();
		options.set("minWidth", 200);
		options.set("maxWidth", 700);
		options.set("minHeight", 100);
		options.set("maxHeight", 300);

		this.add(new MyResizablePanel("resizable", options) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onResizeStart(AjaxRequestTarget target, int top, int left, int width, int height)
			{
				this.info(String.format("resize started - position: [%d, %d], size: [%d, %d]", left, top, width, height));

				target.add(feedback);
			}

			@Override
			public void onResizeStop(AjaxRequestTarget target, int top, int left, int width, int height)
			{
				this.info(String.format("resize stoped - position: [%d, %d], size: [%d, %d]", left, top, width, height));

				target.add(feedback);
			}
		});
	}

	class MyResizablePanel extends ResizablePanel
	{
		private static final long serialVersionUID = 1L;

		public MyResizablePanel(String id, Options options)
		{
			super(id, options);
		}

		@Override
		public boolean isResizeStartEventEnabled()
		{
			return true;
		}

		@Override
		public boolean isResizeStopEventEnabled()
		{
			return true;
		}
	}
}
