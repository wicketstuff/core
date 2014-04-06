package com.googlecode.wicket.jquery.ui.samples.pages.kendo.notification;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class FeedbackPanelPage extends AbstractNotificationPage
{
	private static final long serialVersionUID = 1L;

	public FeedbackPanelPage()
	{
		// FeedbackConsole //
		Options options = new Options();
		options.set("button", true);

		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback", options);
		this.add(feedback);

		// Form //
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// Buttons //
		form.add(new AjaxButton("info") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				this.info("Sample info message");

				target.add(feedback);
			}
		});

		form.add(new AjaxButton("success") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				this.success("Sample success message");

				target.add(feedback);
			}
		});

		form.add(new AjaxButton("warning") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				this.warn("Sample warning message");

				target.add(feedback);
			}
		});

		form.add(new AjaxButton("error") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				this.error("Sample error message");

				target.add(feedback);
			}
		});

		form.add(new AjaxButton("all") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				this.info("Sample info message");
				this.success("Sample success message");
				this.warn("Sample warning message");
				this.error("Sample error message");

				target.add(feedback);
			}
		});
	}
}
