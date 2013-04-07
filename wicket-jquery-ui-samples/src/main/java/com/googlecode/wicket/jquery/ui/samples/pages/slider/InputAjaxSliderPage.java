package com.googlecode.wicket.jquery.ui.samples.pages.slider;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.RangeValidator;

import com.googlecode.wicket.jquery.ui.form.slider.AjaxSlider;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class InputAjaxSliderPage extends AbstractSliderPage
{
	private static final long serialVersionUID = 1L;

	// Model //
	private final Model<Integer> model = new Model<Integer>(15);

	public InputAjaxSliderPage()
	{
		this.init();
	}

	private void init()
	{
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Sliders //
		TextField<Integer> input = new TextField<Integer>("input", this.model, Integer.class);
		form.add(input);

		AjaxSlider slider = new AjaxSlider("slider", this.model, input) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target)
			{
				target.add(feedback);
			}

			@Override
			public void onValueChanged(AjaxRequestTarget target)
			{
				InputAjaxSliderPage.this.info(this);
				target.add(feedback); //do never add 'this' or the form here!
			}
		};

		form.add(slider.setRangeValidator(new RangeValidator<Integer>(0, 100)));
	}

	private void info(Component component)
	{
		this.info(component.getMarkupId() + " has been slided");
		this.info("The model object is: " + this.model.getObject());
	}
}
