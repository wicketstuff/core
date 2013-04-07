package com.googlecode.wicket.jquery.ui.samples.pages.slider;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.form.slider.AjaxSlider;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class AjaxSliderPage extends AbstractSliderPage
{
	private static final long serialVersionUID = 1L;

	// Model //
	private final Model<Integer> model = new Model<Integer>(15);

	public AjaxSliderPage()
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
		final Label label = new Label("label", this.model); //the supplied model allows the initial display
		form.add(label);

		form.add(new AjaxSlider("slider", this.model, label) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onValueChanged(AjaxRequestTarget target)
			{
				AjaxSliderPage.this.info(this);
				target.add(feedback); //do never add 'this' or the form here!
			}
		});
	}

	private void info(Component component)
	{
		this.info(component.getMarkupId() + " has been slided");
		this.info("The model object is: " + this.model.getObject());
	}
}
