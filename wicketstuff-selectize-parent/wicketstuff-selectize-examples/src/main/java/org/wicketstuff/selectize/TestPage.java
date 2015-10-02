package org.wicketstuff.selectize;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.wicketstuff.selectize.SelectizeCssResourceReference.Theme;

public class TestPage extends WebPage {

    private static final long serialVersionUID = 1L;

    public TestPage() {
	Form<Void> form = new Form<Void>("form");
	Selectize selectize = new Selectize("selectize", Model.of("test,test"));
	selectize.setTheme(Theme.DEFAULT);
	form.add(selectize);
	add(form);
    }
}
