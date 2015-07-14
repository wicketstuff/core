package com.googlecode.wicket.jquery.ui.samples.pages.kendo.window;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.html.form.Form;

import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import com.googlecode.wicket.kendo.ui.widget.window.AbstractWindow;

public class DefaultWindowPage extends AbstractWindowPage
{
	private static final long serialVersionUID = 1L;

	public DefaultWindowPage()
	{
		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		this.add(feedback);

		// Form //
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// Window //
		final MyWindow window = new MyWindow("window", "My Window") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onOpen(IPartialPageRequestHandler handler)
			{
				super.onOpen(handler);

				handler.add(feedback); // clear previous messages
			}

			@Override
			public void onClose(IPartialPageRequestHandler handler)
			{
				this.info("Window has been closed");
				handler.add(feedback);
			}
		};

		this.add(window);

		// Buttons //
		form.add(new AjaxButton("open") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				window.open(target);
			}
		});
	}

	/**
	 * This window class is located here for convenience in this sample<br/>
	 * Associated markup file is DefaultWindowPage$MyWindow.html
	 */
	static class MyWindow extends AbstractWindow<Void>
	{
		private static final long serialVersionUID = 1L;

		public MyWindow(String id, String title)
		{
			super(id, title, true);
		}

		@Override
		public boolean isCloseEventEnabled()
		{
			return true;
		}
	}
}
