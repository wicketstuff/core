package com.googlecode.wicket.jquery.ui.samples.kendoui.splitter;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;

import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import com.googlecode.wicket.kendo.ui.widget.splitter.BorderLayout;

public class BorderLayoutPage extends AbstractSplitterPage
{
	private static final long serialVersionUID = 1L;

	public BorderLayoutPage()
	{
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		this.add(feedback);

		this.add(new MyBorderLayout("layout") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onExpand(AjaxRequestTarget target, String paneId)
			{
				super.onExpand(target, paneId);

				this.info(String.format("%s panel has been expanded", paneId));
				target.add(feedback);
			}

			@Override
			public void onCollapse(AjaxRequestTarget target, String paneId)
			{
				super.onCollapse(target, paneId);

				this.info(String.format("%s panel has been collapsed", paneId));
				target.add(feedback);
			}
		});
	}

	static class MyBorderLayout extends BorderLayout
	{
		private static final long serialVersionUID = 1L;

		private boolean collapsed = false;

		public MyBorderLayout(String id)
		{
			super(id);

			this.add(new AjaxLink<Void>("link") {

				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target)
				{
					String paneId = "bottom";

					if (collapsed)
					{
						expand(target, "#vertical", "#" + paneId);
						onExpand(target, paneId); // 'expand' event is not triggered if not initiated by the widget itself
					}
					else
					{
						collapse(target, "#vertical", "#" + paneId);
						onCollapse(target, paneId); // 'collapse' event is not triggered if not initiated by the widget itself
					}
				}
			});
		}

		// Properties //

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

		// Events //

		@Override
		public void onExpand(AjaxRequestTarget target, String paneId)
		{
			this.collapsed = false;
		}

		@Override
		public void onCollapse(AjaxRequestTarget target, String paneId)
		{
			this.collapsed = true;
		}
	}
}
