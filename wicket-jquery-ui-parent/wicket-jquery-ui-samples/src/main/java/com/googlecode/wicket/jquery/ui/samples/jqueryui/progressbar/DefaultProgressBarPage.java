package com.googlecode.wicket.jquery.ui.samples.jqueryui.progressbar;

import java.time.Duration;

import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.extensions.ajax.markup.html.AjaxIndicatorAppender;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.progressbar.ProgressBar;

public class DefaultProgressBarPage extends AbstractProgressBarPage implements IAjaxIndicatorAware
{
	private static final long serialVersionUID = 1L;

	private final ProgressBar progressBar;
	private final AjaxIndicatorAppender indicator = new AjaxIndicatorAppender();

	public DefaultProgressBarPage()
	{
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Timer //
		final AbstractAjaxTimerBehavior timer = new AbstractAjaxTimerBehavior(Duration.ofSeconds(1)) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onTimer(AjaxRequestTarget target)
			{
				progressBar.forward(target, 8);
			}
		};

		form.add(timer);

		// ProgressBar //
		this.progressBar = new ProgressBar("progress", Model.of(36)) {

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
				timer.stop(target); //wicket6

				info("completed!");
				target.add(feedback);
			}
		};

		form.add(this.progressBar);

		// Indicator //
		form.add(new EmptyPanel("indicator").add(this.indicator));

		// Initialize FeedbackPanel //
		this.info("value: " + this.progressBar.getDefaultModelObjectAsString());
	}

	@Override
	public String getAjaxIndicatorMarkupId()
	{
		return this.indicator.getMarkupId();
	}
}
