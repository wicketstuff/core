package com.googlecode.wicket.jquery.ui.samples.pages.slider;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.RangeValidator;

import com.googlecode.wicket.jquery.ui.form.slider.AjaxRangeSlider;
import com.googlecode.wicket.jquery.ui.form.slider.RangeValue;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class InputAjaxRangeSliderPage extends AbstractSliderPage
{
	private static final long serialVersionUID = 1L;

	public InputAjaxRangeSliderPage()
	{
		this.init();
	}

	private void init()
	{
		final Form<RangeValue> form = new Form<RangeValue>("form", new Model<RangeValue>(new RangeValue(16, 64)));
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Sliders //
		TextField<Integer> lower = new TextField<Integer>("lower", new PropertyModel<Integer>(form.getModelObject(), "lower"), Integer.class);
		form.add(lower);

		TextField<Integer> upper = new TextField<Integer>("upper", new PropertyModel<Integer>(form.getModelObject(), "upper"), Integer.class);
		form.add(upper);

		AjaxRangeSlider slider = new AjaxRangeSlider("slider", form.getModel(), lower, upper) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target)
			{
				target.add(feedback); //do never add 'this' or the form here!
			}

			@Override
			public void onValueChanged(AjaxRequestTarget target)
			{
				InputAjaxRangeSliderPage.this.info(this, form);
				target.add(feedback); //do never add 'this' or the form here!
			}
		};

		form.add(slider.setRangeValidator(new RangeValidator<Integer>(0, 100)));
	}

	private void info(Component component, Form<?> form)
	{
		RangeValue value = (RangeValue) form.getModelObject(); //need to cast because 'form' argument is generic with ?

		this.info(component.getMarkupId() + " has been slided");
		this.info(String.format("lower value is %d and upper value is %d", value.getLower(), value.getUpper()));
	}
}
