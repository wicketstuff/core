package org.wicketstuff.html5.markup.html.form;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.html5.BasePage;

public class NumberFieldDemo extends BasePage
{
	private static final long serialVersionUID = -1817876321582710022L;

	public NumberFieldDemo(PageParameters parameters)
	{
		super(parameters);

		FeedbackPanel feedback = new FeedbackPanel("feedback");
		add(feedback);

		Form<Void> form = new Form<Void>("form");
		add(form);

		Model<Double> model = Model.of();
		NumberTextField<Double> numberField = new NumberTextField<Double>("number", model,
			Double.class);
		numberField.setRequired(false);
		form.add(numberField);
		numberField.setMinimum(4.0d);
		numberField.setMaximum(10.0d);

		add(new Label("numberLabel", model));
	}
}
