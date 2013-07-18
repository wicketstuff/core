package com.googlecode.wicket.jquery.ui.samples.pages.kendo.combobox;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.kendo.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.kendo.button.Button;
import com.googlecode.wicket.jquery.ui.kendo.combobox.ComboBox;
import com.googlecode.wicket.jquery.ui.kendo.combobox.ComboBoxRenderer;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.data.GenresDAO;
import com.googlecode.wicket.jquery.ui.samples.data.bean.Genre;

public class RendererComboBoxPage extends AbstractComboBoxPage
{
	private static final long serialVersionUID = 1L;

	public RendererComboBoxPage()
	{
		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// ComboBox //
		final ComboBox<Genre> dropdown = new ComboBox<Genre>("combobox", new Model<String>(), GenresDAO.all(), new ComboBoxRenderer<Genre>("name", "id"));
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
