package org.wicketstuff.htmlcompressor.examples;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * A simple homepage with a basic form.
 * 
 * @author akiraly
 */
public class HomePage extends WebPage
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void onInitialize()
	{
		super.onInitialize();
		add(new Label("label", "Hello World!"));

		add(new FeedbackPanel("feedback"));

		Form<Void> form = new Form<Void>("form");
		add(form);

		form.add(new TextField<String>("field").setRequired(true));
		form.add(new TextArea<String>("area").setRequired(true));
	}
}
