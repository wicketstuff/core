package org.wicketstuff.html5.markup.html.form;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RangeTextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;
import org.wicketstuff.html5.BasePage;

public class RangeTextFieldDemo extends BasePage {

	private final Pojo pojo;
	
	public RangeTextFieldDemo() {
		
		this.pojo = new Pojo();
		pojo.range = 2.3d;
		
		Form<Pojo> form = new Form<Pojo>("form", new CompoundPropertyModel<Pojo>(pojo));
		add(form);
		
		RangeTextField rangeTextField = new RangeTextField("range");
		form.add(rangeTextField);
		
		rangeTextField.setMinimum(1.4d);
		rangeTextField.setMaximum(10.0d);
		
		final Label rangeLabel = new Label("rangeLabel", new Model<String>(pojo.range.toString()));
		rangeLabel.setOutputMarkupId(true);
		add(rangeLabel);
		
		rangeTextField.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				rangeLabel.setDefaultModelObject(pojo.range.toString());
				target.add(rangeLabel);
			}
		}.setThrottleDelay(Duration.milliseconds(500)));
	}
	
	private static class Pojo implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@SuppressWarnings("unused")
		private Double range;
	}


}
