package com.googlecode.wicket.jquery.ui.samples.pages.resizable;

import org.apache.wicket.ajax.AjaxRequestTarget;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.interaction.resizable.ResizableBehavior;

public class DefaultResizablePage extends AbstractResizablePage
{
	private static final long serialVersionUID = 1L;

	public DefaultResizablePage()
	{
		Options options = new Options();
		options.set("minWidth", 200);
		options.set("maxWidth", 720);
		options.set("minHeight", 100);
		options.set("maxHeight", 300);

		this.add(new ResizableBehavior("#resizable", options) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isResizeStartEventEnabled()
			{
				return false;
			}

			@Override
			public boolean isResizeStopEventEnabled()
			{
				return false;
			}

			@Override
			public void onResizeStart(AjaxRequestTarget target, int top, int left, int width, int height)
			{
			}

			@Override
			public void onResizeStop(AjaxRequestTarget target, int top, int left, int width, int height)
			{
			}
		});
	}
}
