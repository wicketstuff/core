/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.jqueryui.progressbar;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.wicketstuff.jquery.ui.form.button.AjaxButton;
import org.wicketstuff.jquery.ui.panel.JQueryFeedbackPanel;
import org.wicketstuff.jquery.ui.widget.progressbar.ProgressBar;

public class ButtonProgressBarPage extends AbstractProgressBarPage
{
	private static final long serialVersionUID = 1L;

	public ButtonProgressBarPage()
	{
		this.initialize();
	}

	private void initialize()
	{
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// ProgressBar //
		final ProgressBar progressBar = new ProgressBar("progress", Model.of(90)) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onValueChanged(IPartialPageRequestHandler handler)
			{
				info("value: " + this.getDefaultModelObjectAsString());
				handler.add(feedback);
			}

			@Override
			protected void onComplete(AjaxRequestTarget target)
			{
				info("completed!");
				target.add(feedback);
			}
		};

		form.add(progressBar);

		// Buttons //
		form.add(new AjaxButton("backward") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				progressBar.backward(target);
			}
		});

		form.add(new AjaxButton("forward") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				progressBar.forward(target);
			}
		});
	}
}
