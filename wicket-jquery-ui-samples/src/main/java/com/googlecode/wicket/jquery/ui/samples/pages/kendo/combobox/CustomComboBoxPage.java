package com.googlecode.wicket.jquery.ui.samples.pages.kendo.combobox;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.samples.data.bean.Genre;
import com.googlecode.wicket.jquery.ui.samples.data.dao.GenresDAO;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.form.button.Button;
import com.googlecode.wicket.kendo.ui.form.combobox.ComboBox;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class CustomComboBoxPage extends AbstractComboBoxPage
{
	private static final long serialVersionUID = 1L;

	public CustomComboBoxPage()
	{
		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback);

		// ComboBox //
		final ComboBox<Genre> combobox = new ComboBox<Genre>("combobox", new Model<String>(), GenresDAO.all());
		form.add(combobox); //.setRequired(true)

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				CustomComboBoxPage.this.info(combobox);
			}
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				CustomComboBoxPage.this.info(combobox);
				target.add(feedback);
			}
		});
	}


	private void info(ComboBox<Genre> combobox)
	{
		String choice =  combobox.getModelObject();

		this.info(choice != null ? choice : "no choice");
	}
}
