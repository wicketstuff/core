package com.googlecode.wicket.jquery.ui.samples.kendoui.progressbar;

import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.form.slider.AjaxSlider;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import com.googlecode.wicket.kendo.ui.widget.progressbar.ProgressBar;

public class SliderProgressBarPage extends AbstractProgressBarPage
{
	private static final long serialVersionUID = 1L;

	public SliderProgressBarPage()
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

		final ProgressBar progressbar = new ProgressBar("progress", Model.of(90), options) {

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

		form.add(progressbar);

		// Slider //
		form.add(new AjaxSlider("slider", progressbar.getModel()) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onValueChanged(IPartialPageRequestHandler handler)
			{
				// no need to set the model object to the progressbar's model; they already share the same model
				// but we still need to inform the progressbar its model changed.
				progressbar.modelChanged();
				progressbar.refresh(handler);
			}
		});
	}
}
