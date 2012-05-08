package com.googlecode.wicket.jquery.ui.samples.pages.test;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.form.slider.RangeValue;
import com.googlecode.wicket.jquery.ui.samples.TemplatePage;

public class MyPage extends TemplatePage
{
	private static final long serialVersionUID = 1L;
	
	public MyPage()
	{
		this.init();
	}
	
	private void init()
	{
		// Form //
		final Form<RangeValue> form = new Form<RangeValue>("form", new Model<RangeValue>(new RangeValue(16, 64)));
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
		form.add(feedbackPanel.setOutputMarkupId(true));

		// Slider //
		form.add(new MySlider("slider", form.getModel(), "Please slide to the desired value:") {

			private static final long serialVersionUID = 1L;
			
			@Override
			protected void onValueChanged(AjaxRequestTarget target)
			{
				MyPage.this.info(this, form);
				target.add(feedbackPanel);
			}
		});
	}

	private void info(Component component, Form<RangeValue> form)
	{
		RangeValue value = form.getModelObject();

		this.info(component.getMarkupId() + " has been slided");
		this.info(String.format("lower value is %d and upper value is %d", value.getLower(), value.getUpper()));
	}
}
