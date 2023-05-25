package org.wicketstuff.foundation.button;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

public class FoundationButtonConstructorTestPage extends WebPage {

	private static final long serialVersionUID = 1L;

	public FoundationButtonConstructorTestPage(PageParameters params) {
		Form<Void> form = new Form<>("form");
		add(form);

		StringValue sv = params.get("buttonType");
		if (sv.toString().equals("FoundationAjaxButton")) {
			form.add(new FoundationAjaxButton("btn1", form, new ButtonOptions()));
			form.add(new FoundationAjaxButton("btn2", Model.of("foobar"), new ButtonOptions()));
			form.add(new FoundationAjaxButton("btn3", Model.of("foobar"), form, new ButtonOptions()));
		} else {
			form.add(new FoundationSubmitLink("btn1", form, new ButtonOptions()));
			form.add(new FoundationSubmitLink("btn2", Model.of("foobar"), new ButtonOptions()));
			form.add(new FoundationSubmitLink("btn3", Model.of("foobar"), form, new ButtonOptions()));
		}
	}
}
