package org.wicketstuff.foundation.button;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;

public class FoundationButtonTestPage extends WebPage {

	private static final long serialVersionUID = 1L;

	public FoundationButtonTestPage(FoundationAjaxButton btn) {
		Form<Void> form = new Form<>("form");
		add(form);
		form.add(btn);
	}

	public FoundationButtonTestPage(FoundationAjaxSubmitLink btn) {
		Form<Void> form = new Form<>("form");
		add(form);
		form.add(btn);
	}

}
