package com.googlecode.wicket.jquery.ui.samples.kendoui.window;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.resource.CssResourceReference;

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

		this.add(window); // attached to the page

		// Buttons //
		form.add(new AjaxButton("open") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				window.open(target);
			}
		});
	}

	// Classes //

	/**
	 * This window class is located here for convenience in this sample<br>
	 * Associated markup file is ActionWindowPage$MyWindow.html
	 */
	abstract static class MyWindow extends AbstractWindow<Void>
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
				protected void onSubmit(AjaxRequestTarget target)
				{
					MyWindow.this.close(target);
				}
			});
		}

		// Methods //

		@Override
		public void renderHead(IHeaderResponse response)
		{
			super.renderHead(response);

			response.render(CssReferenceHeaderItem.forReference(new CssResourceReference(ActionWindowPage.class, "ActionWindowPage$MyWindow.css")));
		}

		// Properties //

		@Override
		public boolean isCloseEventEnabled()
		{
			return true;
		}

		@Override
		public boolean isActionEventEnabled()
		{
			return true;
		}

		// Events //

		@Override
		public void onConfigure(JQueryBehavior behavior)
		{
			super.onConfigure(behavior);

			behavior.setOption("actions", "['Custom', 'Pin', 'Maximize', 'Minimize', 'Close']");
		}

		@Override
		protected void onOpen(IPartialPageRequestHandler handler)
		{
			handler.add(this.feedback); // clear previous messages
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
	}
}
