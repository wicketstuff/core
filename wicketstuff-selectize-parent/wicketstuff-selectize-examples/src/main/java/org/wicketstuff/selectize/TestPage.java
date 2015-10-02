package org.wicketstuff.selectize;

import java.util.HashMap;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.wicketstuff.selectize.SelectizeCssResourceReference.Theme;

public class TestPage extends WebPage
{

	private static final long serialVersionUID = 1L;

	public TestPage()
	{
		Form<Void> form = new Form<Void>("form");
		Selectize selectize1 = new Selectize("selectize1", Model.of("test"));
		selectize1.setTheme(Theme.BOOTSTRAP3);
		form.add(selectize1);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("1", "Brian Reavis");
		map.put("2", "Nikola Tesla");
		map.put("3", "Albert Einstein");
		Selectize selectize2 = new Selectize("selectize2",new Model<HashMap<String, String>>(map));
		selectize2.setPlaceholder("Select a person!");
		selectize2.setTheme(Theme.BOOTSTRAP3);
		form.add(selectize2);
		add(form);
	}
}
