package com.googlecode.wicket.jquery.ui.samples.pages.slider;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.RangeValidator;

import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.form.button.Button;
import com.googlecode.wicket.jquery.ui.form.slider.Slider;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class InputSliderPage extends AbstractSliderPage
{
	private static final long serialVersionUID = 1L;
	
	public InputSliderPage()
	{
		this.init();
	}
	
	private void init()
	{
		Form<Integer> form = new Form<Integer>("form");
		this.add(form);

		// FeedbackPanel //
		form.add(new JQueryFeedbackPanel("feedback").setOutputMarkupId(true));

		// Sliders //
		TextField<Integer> input = new TextField<Integer>("input", new Model<Integer>(15), Integer.class);
		form.add(input);

		final Slider slider = new Slider("slider", input.getModel(), input); //input is responsible of the model object
		slider.setRangeValidator(new RangeValidator<Integer>(0, 200));
		form.add(slider.setMin(0).setMax(200));
		
		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				InputSliderPage.this.info(this, slider);
			}			
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				InputSliderPage.this.info(this, slider);
				target.add(form);
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
				target.add(form.get("feedback"));
			}
		});
	}

	private void info(Component component, Slider slider)
	{
		this.info(component.getMarkupId() + " has been clicked");
		this.info("The model object is: " + slider.getModelObject());
	}
}
