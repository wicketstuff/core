package com.googlecode.wicket.jquery.ui.samples.pages.slider;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.form.slider.AjaxRangeSlider;
import com.googlecode.wicket.jquery.ui.form.slider.RangeValue;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class AjaxRangeSliderPage extends AbstractSliderPage
{
	private static final long serialVersionUID = 1L;

	public AjaxRangeSliderPage()
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
		final Label label = new Label("label", form.getModel()); //the supplied model allows the initial display
		form.add(label);

		form.add(new AjaxRangeSlider("slider", form.getModel(), label) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onValueChanged(AjaxRequestTarget target)
			{
				AjaxRangeSliderPage.this.info(this, form);
				target.add(feedback); //do never add 'this' or the form here!
			}
		});
	}

	private void info(Component component, Form<?> form)
	{
		RangeValue value = (RangeValue) form.getModelObject(); //need to cast because 'form' argument is generic with ?

		this.info(component.getMarkupId() + " has been slided");
		this.info(String.format("lower value is %d and upper value is %d", value.getLower(), value.getUpper()));
	}
}
