package org.wicketstuff.html5.markup.html.form;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.ThrottlingSettings;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RangeTextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;
import org.wicketstuff.html5.BasePage;

public class RangeTextFieldDemo extends BasePage
{
	private static final long serialVersionUID = -5715148572580111439L;

	public RangeTextFieldDemo()
	{

		Model<Double> model = Model.of(2.3d);

		Form<Void> form = new Form<Void>("form");
		add(form);

		RangeTextField<Double> rangeTextField = new RangeTextField<Double>("range", model,
			Double.class);
		form.add(rangeTextField);

		rangeTextField.setMinimum(1.4d);
		rangeTextField.setMaximum(10.0d);

		final Label rangeLabel = new Label("rangeLabel", model);
		rangeLabel.setOutputMarkupId(true);
		add(rangeLabel);

		rangeTextField.add(new AjaxFormComponentUpdatingBehavior("change")
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target)
			{
				target.add(rangeLabel);
			}

			@Override
			protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
			{
				super.updateAjaxAttributes(attributes);

				ThrottlingSettings throttleSettings = new ThrottlingSettings("rangeTextFieldDemoId", Duration.milliseconds(500));
				attributes.setThrottlingSettings(throttleSettings);
			}
		});
	}

}
