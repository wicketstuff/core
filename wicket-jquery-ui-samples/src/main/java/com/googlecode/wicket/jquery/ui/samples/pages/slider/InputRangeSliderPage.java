package com.googlecode.wicket.jquery.ui.samples.pages.slider;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.RangeValidator;

import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.form.button.Button;
import com.googlecode.wicket.jquery.ui.form.slider.RangeSlider;
import com.googlecode.wicket.jquery.ui.form.slider.RangeValue;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class InputRangeSliderPage extends AbstractSliderPage
{
	private static final long serialVersionUID = 1L;
	
	// Models //
	public InputRangeSliderPage()
	{
		this.init();
	}
	
	private void init()
	{
		final Form<RangeValue> form = new Form<RangeValue>("form", new Model<RangeValue>(new RangeValue(-32, 64)));
		this.add(form);

		// FeedbackPanel //
		form.add(new JQueryFeedbackPanel("feedback"));

		// Sliders //
		TextField<Integer> lower = new TextField<Integer>("lower", new PropertyModel<Integer>(form.getModelObject(), "lower"), Integer.class);
		form.add(lower);

		TextField<Integer> upper = new TextField<Integer>("upper", new PropertyModel<Integer>(form.getModelObject(), "upper"), Integer.class);
		form.add(upper);

		RangeSlider slider = new RangeSlider("slider", form.getModel(), lower, upper);
		slider.setMin(-128);
		slider.setMax(128);
		slider.setRangeValidator(new RangeValidator<Integer>(-128, 128));
		form.add(slider);
	
		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				InputRangeSliderPage.this.info(this, form);
			}			
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
				target.add(form);
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				InputRangeSliderPage.this.info(this, form);
				target.add(form);
			}
		});
	}

	private void info(Component component, Form<?> form)
	{
		RangeValue value = (RangeValue) form.getModelObject(); //need to cast because 'form' argument is generic with ?
		
		this.info(component.getMarkupId() + " has been clicked");
		this.info(String.format("lower value is %d and upper value is %d", value.getLower(), value.getUpper()));
	}
}
