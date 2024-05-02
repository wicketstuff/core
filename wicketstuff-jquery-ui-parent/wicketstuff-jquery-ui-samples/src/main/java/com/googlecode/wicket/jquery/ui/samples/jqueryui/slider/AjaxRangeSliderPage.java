package com.googlecode.wicket.jquery.ui.samples.jqueryui.slider;

import org.apache.wicket.Component;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.resource.StyleSheetPackageHeaderItem;
import com.googlecode.wicket.jquery.ui.form.slider.AjaxRangeSlider;
import com.googlecode.wicket.jquery.ui.form.slider.RangeValue;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class AjaxRangeSliderPage extends AbstractSliderPage
{
	private static final long serialVersionUID = 1L;

	public AjaxRangeSliderPage()
	{
		this.initialize();
	}

	// Methods //

	private void initialize()
	{
		final Form<RangeValue> form = new Form<RangeValue>("form", Model.of(new RangeValue(16, 64)));
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Sliders //
		final Label label = new Label("label", form.getModel()); // the supplied model allows the initial display
		form.add(label);

		form.add(new AjaxRangeSlider("slider", form.getModel(), label) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onValueChanged(IPartialPageRequestHandler handler)
			{
				AjaxRangeSliderPage.this.info(this, form);
				handler.add(feedback); // do never add 'this' or the form here!
			}
		});
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(AjaxRangeSliderPage.class));
	}

	private void info(Component component, Form<?> form)
	{
		RangeValue value = (RangeValue) form.getModelObject(); // need to cast because 'form' argument is generic with ?

		this.info(component.getMarkupId() + " has been slided");
		this.info(String.format("lower value is %d and upper value is %d", value.getLower(), value.getUpper()));
	}
}
