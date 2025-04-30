/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.kendoui.combobox;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.wicketstuff.jquery.ui.samples.data.bean.Genre;
import org.wicketstuff.jquery.ui.samples.data.dao.GenresDAO;
import org.wicketstuff.kendo.ui.form.button.AjaxButton;
import org.wicketstuff.kendo.ui.form.button.Button;
import org.wicketstuff.kendo.ui.form.combobox.ComboBox;
import org.wicketstuff.kendo.ui.panel.KendoFeedbackPanel;
import org.wicketstuff.kendo.ui.renderer.ChoiceRenderer;

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
			protected void onSubmit(AjaxRequestTarget target)
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
