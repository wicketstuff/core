package com.googlecode.wicket.jquery.ui.samples.pages.slider;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.form.button.Button;
import com.googlecode.wicket.jquery.ui.form.slider.Slider;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class DefaultSliderPage extends AbstractSliderPage
{
	private static final long serialVersionUID = 1L;

	public DefaultSliderPage()
	{
		final Form<Integer> form = new Form<Integer>("form", Model.of(15));
		this.add(form);

		// FeedbackPanel //
		form.add(new JQueryFeedbackPanel("feedback"));

		// Sliders //
		final Label label = new Label("label", form.getModel()); //the supplied model allows the initial display
		form.add(label);

		form.add(new Slider("slider", form.getModel(), label)); //will use the html markupId

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				DefaultSliderPage.this.info(this, form);
			}
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				DefaultSliderPage.this.info(this, form);
				target.add(form);
			}
		});
	}

	private void info(Component component, Form<?> form)
	{
		this.info(component.getMarkupId() + " has been clicked");
		this.info("The model object is: " + form.getModelObject());
	}
}
