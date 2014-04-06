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
import com.googlecode.wicket.kendo.ui.renderer.ChoiceRenderer;

public class RendererComboBoxPage extends AbstractComboBoxPage
{
	private static final long serialVersionUID = 1L;

	public RendererComboBoxPage()
	{
		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback);

		// ComboBox //
		final ComboBox<Genre> dropdown = new ComboBox<Genre>("combobox", new Model<String>(), GenresDAO.all(), new ChoiceRenderer<Genre>("name", "id"));
		form.add(dropdown); //.setRequired(true)

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				RendererComboBoxPage.this.info(dropdown);
			}
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				RendererComboBoxPage.this.info(dropdown);
				target.add(feedback);
			}
		});
	}


	private void info(ComboBox<Genre> dropdown)
	{
		String choice =  dropdown.getModelObject();

		this.info(choice != null ? choice : "no choice");
	}
}
