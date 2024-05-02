package org.wicketstuff.jquery.ui.samples.kendoui.progressbar;

import java.time.Duration;

import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.extensions.ajax.markup.html.AjaxIndicatorAppender;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.Model;
import org.wicketstuff.jquery.core.Options;
import org.wicketstuff.kendo.ui.panel.KendoFeedbackPanel;
import org.wicketstuff.kendo.ui.widget.progressbar.ProgressBar;

public class KendoProgressBarPage extends AbstractProgressBarPage implements IAjaxIndicatorAware
{
	private static final long serialVersionUID = 1L;

	private final ProgressBar progressbar;
	private final AjaxIndicatorAppender indicator = new AjaxIndicatorAppender();

	public KendoProgressBarPage()
	{
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Timer //
		final AbstractAjaxTimerBehavior timer = new AbstractAjaxTimerBehavior(Duration.ofSeconds(1)) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onTimer(AjaxRequestTarget target)
			{
				progressbar.forward(target);
			}
		};

		form.add(timer);

		// ProgressBar //
		final Options options = new Options();
		options.set("max", 20);

		this.progressbar = new ProgressBar("progress", Model.of(15), options) {

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
				timer.stop(handler); //wicket6

				info("completed!");
				handler.add(feedback);
			}
		};

		form.add(this.progressbar);

		// Indicator //
		form.add(new EmptyPanel("indicator").add(this.indicator));

		// Initialize FeedbackPanel //
		this.info("value: " + this.progressbar.getDefaultModelObjectAsString());
	}

	@Override
	public String getAjaxIndicatorMarkupId()
	{
		return this.indicator.getMarkupId();
	}
}
