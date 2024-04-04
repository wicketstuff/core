package com.googlecode.wicket.jquery.ui.samples.kendoui.progressbar;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.kendo.ui.KendoIcon;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import com.googlecode.wicket.kendo.ui.widget.progressbar.ProgressBar;

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
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// ProgressBar //
		final Options options = new Options();
		options.set("type", Options.asString("percent"));
		options.set("animation", false);

		final ProgressBar progressBar = new ProgressBar("progress", Model.of(90), options) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onValueChanged(IPartialPageRequestHandler handler)
			{
				info("value: " + this.getDefaultModelObjectAsString());
				handler.add(feedback);
			}

			@Override
			public void onComplete(IPartialPageRequestHandler handler)
			{
				info("completed!");
				handler.add(feedback);
			}
		};

		form.add(progressBar);

		// Buttons //
		form.add(new AjaxButton("backward") {

			private static final long serialVersionUID = 1L;

			@Override
			protected String getIcon()
			{
				return KendoIcon.ARROW_60_LEFT;
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				progressBar.backward(target);
			}
		});

		form.add(new AjaxButton("forward") {

			private static final long serialVersionUID = 1L;

			@Override
			protected String getIcon()
			{
				return KendoIcon.ARROW_60_RIGHT;
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				progressBar.forward(target);
			}
		});
	}
}
