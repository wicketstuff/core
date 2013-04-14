package com.googlecode.wicket.jquery.ui.samples.pages.kendo.combobox;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.kendo.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.kendo.button.Button;
import com.googlecode.wicket.jquery.ui.kendo.combobox.ComboBox;
import com.googlecode.wicket.jquery.ui.kendo.combobox.ComboBoxRenderer;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
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
		final ComboBox<Genre> dropdown = new ComboBox<Genre>("combobox", new Model<String>(), GENRES, new ComboBoxRenderer<Genre>("name", "id"));
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


	// List of Genre(s) //
	private static final List<Genre> GENRES = Arrays.asList(
			new Genre(1, "Black Metal"),
			new Genre(2, "Death Metal"),
			new Genre(3, "Doom Metal"),
			new Genre(4, "Folk Metal"),
			new Genre(5, "Gothic Metal"),
			new Genre(6, "Heavy Metal"),
			new Genre(7, "Power Metal"),
			new Genre(8, "Symphonic Metal"),
			new Genre(9, "Trash Metal"),
			new Genre(10, "Vicking Metal"));
}
