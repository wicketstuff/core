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
import com.googlecode.wicket.jquery.ui.form.slider.Orientation;
import com.googlecode.wicket.jquery.ui.form.slider.Slider;
import com.googlecode.wicket.jquery.ui.form.slider.Slider.Range;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class OptionSliderPage extends AbstractSliderPage
{
	private static final long serialVersionUID = 1L;

	// Models //
	private final Model<Integer> model1 = Model.of(16);
	private final Model<Integer> model2 = Model.of(4);

	public OptionSliderPage()
	{
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		form.add(new JQueryFeedbackPanel("feedback"));

		// Sliders //
		final Label label1 = new Label("label1", this.model1); // the supplied model allows the initial display
		form.add(label1.setOutputMarkupId(true));

		form.add(new Label("model1", this.model1));
		form.add(new Slider("slider1", this.model1, label1).setRange(Range.MIN).setMax(120).setStep(16));

		final Label label2 = new Label("label2", this.model2); // the supplied model allows the initial display
		form.add(label2.setOutputMarkupId(true));

		form.add(new Label("model2", this.model2));
		form.add(new Slider("slider2", this.model2, label2).setOrientation(Orientation.VERTICAL).setMin(1).setMax(10).setStep(3));

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				OptionSliderPage.this.info(this);
			}
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				OptionSliderPage.this.info(this);
				target.add(form);
			}
		});
	}

	// Methods //

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(OptionSliderPage.class));
	}

	private void info(Component component)
	{
		this.info(component.getMarkupId() + " has been clicked");
	}
}
