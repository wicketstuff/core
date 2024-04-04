package com.googlecode.wicket.jquery.ui.samples.jqueryui.progressbar;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.form.slider.AjaxSlider;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.progressbar.ProgressBar;

public class SliderProgressBarPage extends AbstractProgressBarPage
{
	private static final long serialVersionUID = 1L;

	public SliderProgressBarPage()
	{
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// ProgressBar //
		final ProgressBar progressbar = new ProgressBar("progress", Model.of(90)) {

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

		form.add(progressbar);

		// Slider //
		form.add(new AjaxSlider("slider", progressbar.getModel()) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onValueChanged(IPartialPageRequestHandler handler)
			{
				//no need to set the model object to the progressbar's model; they already share the same model
				//but we still need to inform the progressbar its model changed.
				progressbar.modelChanged();
				progressbar.refresh(handler);
			}
		});
	}
}
