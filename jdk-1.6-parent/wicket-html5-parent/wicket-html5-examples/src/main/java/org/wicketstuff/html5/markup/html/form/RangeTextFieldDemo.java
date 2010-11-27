package org.wicketstuff.html5.markup.html.form;

import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CompressedResourceReference;
import org.apache.wicket.util.time.Duration;
import org.wicketstuff.html5.BasePage;

public class RangeTextFieldDemo extends BasePage {

	private static final CompressedResourceReference WICKET_AJAX_HTML5_JS = 
			new CompressedResourceReference(RangeTextFieldDemo.class, "wicket-ajax-html5.js");

	private final Pojo pojo;
	
	public RangeTextFieldDemo() {
		
		this.pojo = new Pojo();
		pojo.range = 2.0d;
		
		Form<Pojo> form = new Form<Pojo>("form", new CompoundPropertyModel<Pojo>(pojo));
		add(form);
		
		RangeTextField rangeTextField = new RangeTextField("range");
		form.add(rangeTextField);
		
		rangeTextField.setMinimum(0.0d);
		rangeTextField.setMaximum(10.0d);
		rangeTextField.setStep(0.5d);
		
		final Label rangeLabel = new Label("rangeLabel", new Model<String>(pojo.range.toString()));
		rangeLabel.setOutputMarkupId(true);
		add(rangeLabel);
		
		rangeTextField.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				rangeLabel.setDefaultModelObject(pojo.range.toString());
				target.addComponent(rangeLabel);
			}
			
			public void renderHead(Component c, IHeaderResponse response) {
				super.renderHead(c, response);
				// override Wicket.Form.serializeInput so that input[type=range] is serialized too
				response.renderJavascriptReference(WICKET_AJAX_HTML5_JS);
			}
		}.setThrottleDelay(Duration.milliseconds(500)));
	}
	
	private static class Pojo implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@SuppressWarnings("unused")
		private Double range;
	}


}
