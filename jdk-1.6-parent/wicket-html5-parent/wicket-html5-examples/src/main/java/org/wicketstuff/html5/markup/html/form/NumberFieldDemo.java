package org.wicketstuff.html5.markup.html.form;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.html5.BasePage;

public class NumberFieldDemo extends BasePage {

	@SuppressWarnings("unused")
	private Double d;
	
	public NumberFieldDemo(PageParameters parameters) {
		super(parameters);
		
		FeedbackPanel feedback = new FeedbackPanel("feedback");
		add(feedback);
		
		Form<Void> form = new Form<Void>("form");
		add(form);
		
		NumberTextField numberField = new NumberTextField("number", new PropertyModel<Double>(this, "d"));
		numberField.setRequired(false);
		form.add(numberField);
		numberField.setMinimum(4.0d);
		
		add(new Label("numberLabel", new PropertyModel<Double>(this, "d")));
	}
}
