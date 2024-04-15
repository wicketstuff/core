package com.googlecode.wicket.jquery.ui.samples.jqueryui.slider;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.resource.StyleSheetPackageHeaderItem;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.form.button.Button;
import com.googlecode.wicket.jquery.ui.form.slider.RangeSlider;
import com.googlecode.wicket.jquery.ui.form.slider.RangeValue;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class RangeSliderPage extends AbstractSliderPage
{
	private static final long serialVersionUID = 1L;

	// Models //
	public RangeSliderPage()
	{
		final Form<RangeValue> form = new Form<RangeValue>("form", Model.of(new RangeValue(-32, 64)));
		this.add(form);

		// FeedbackPanel //
		form.add(new JQueryFeedbackPanel("feedback"));

		// Sliders //
		final Label label = new Label("label", form.getModel()); // the supplied model allows the initial display
		form.add(label);

		form.add(new RangeSlider("slider", form.getModel(), label).setMin(-128).setMax(128));

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				RangeSliderPage.this.info(this, form);
			}
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				RangeSliderPage.this.info(this, form);
				target.add(form);
			}
		});
	}

	// Methods //

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(RangeSliderPage.class));
	}

	private void info(Component component, Form<?> form)
	{
		RangeValue value = (RangeValue) form.getModelObject(); // need to cast because 'form' argument is generic with ?

		this.info(component.getMarkupId() + " has been clicked");
		this.info(String.format("lower value is %d and upper value is %d", value.getLower(), value.getUpper()));
	}
}
