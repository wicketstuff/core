package com.googlecode.wicket.jquery.ui.samples.pages.kendo.window;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import com.googlecode.wicket.kendo.ui.widget.window.AbstractWindow;

public class ActionWindowPage extends AbstractWindowPage
{
	private static final long serialVersionUID = 1L;

	public ActionWindowPage()
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
			protected void onOpen(AjaxRequestTarget target)
			{
				super.onOpen(target);

				target.add(feedback); // clear previous messages
			}

			@Override
			public void onClose(AjaxRequestTarget target)
			{
				this.info("Window has been closed");
				target.add(feedback);
			}
		};

		this.add(window); // attached to the page

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
	 * Associated markup file is EventWindowPage$MyWindow.html
	 */
	static abstract class MyWindow extends AbstractWindow<Void>
	{
		private static final long serialVersionUID = 1L;

		private KendoFeedbackPanel feedback;

		public MyWindow(String id, String title)
		{
			super(id, title, true);

			// Feedback //
			this.feedback = new KendoFeedbackPanel("feedback");
			this.add(feedback.setOutputMarkupId(true));

			// Form //
			final Form<Void> form = new Form<Void>("form");
			this.add(form);

			// Buttons //
			form.add(new AjaxButton("close") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form)
				{
					MyWindow.this.close(target);
				}
			});
		}

		@Override
		public void onConfigure(JQueryBehavior behavior)
		{
			super.onConfigure(behavior);

			behavior.setOption("actions", "['Custom', 'Pin', 'Maximize', 'Minimize', 'Close']");
		}

		@Override
		protected void onOpen(AjaxRequestTarget target)
		{
			target.add(this.feedback); // clear previous messages
		}

		@Override
		public void onAction(AjaxRequestTarget target, String action)
		{
			this.info("Clicked " + action);

			if (ACTION_CUSTOM.equals(action))
			{
				this.info("Performing custom action...");
			}

			target.add(this.feedback);
		}

		@Override
		public boolean isCloseEventEnabled()
		{
			return true; // Caution: as 'Close' action as been specified
		}

		@Override
		public boolean isActionEventEnabled()
		{
			return true;
		}
	}
}
