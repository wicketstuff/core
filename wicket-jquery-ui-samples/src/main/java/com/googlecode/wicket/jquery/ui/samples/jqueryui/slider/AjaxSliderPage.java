package com.googlecode.wicket.jquery.ui.samples.jqueryui.slider;

import org.apache.wicket.Component;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.resource.StyleSheetPackageHeaderItem;
import com.googlecode.wicket.jquery.ui.form.slider.AjaxSlider;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class AjaxSliderPage extends AbstractSliderPage
{
	private static final long serialVersionUID = 1L;

	// Model //
	private final Model<Integer> model = Model.of(15);

	public AjaxSliderPage()
	{
		this.initialize();
	}

	// Methods //

	private void initialize()
	{
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Sliders //
		final Label label = new Label("label", this.model); // the supplied model allows the initial display
		form.add(label);

		form.add(new AjaxSlider("slider", this.model, label) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onValueChanged(IPartialPageRequestHandler handler)
			{
				AjaxSliderPage.this.info(this);
				handler.add(feedback); // do never add 'this' or the form here!
			}
		});
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(AjaxSliderPage.class));
	}

	private void info(Component component)
	{
		this.info(component.getMarkupId() + " has been slided");
		this.info("The model object is: " + this.model.getObject());
	}
}
