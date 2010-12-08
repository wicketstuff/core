package org.wicketstuff.html5.markup.html.form;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.html5.BasePage;

public class NumberFieldDemo extends BasePage {

	private Double d;
	
	public NumberFieldDemo(PageParameters parameters) {
		super(parameters);
		
		Form<Void> form = new Form<Void>("form");
		add(form);
		
		form.add(new NumberField("number", new PropertyModel<Double>(this, "d")));
		
		add(new Label("numberLabel", new PropertyModel<Double>(this, "d")));
	}
}
