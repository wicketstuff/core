/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.jqueryui.dropdown;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.wicketstuff.jquery.ui.form.button.AjaxButton;
import org.wicketstuff.jquery.ui.form.button.Button;
import org.wicketstuff.jquery.ui.form.dropdown.DropDownChoice;
import org.wicketstuff.jquery.ui.panel.JQueryFeedbackPanel;
import org.wicketstuff.jquery.ui.samples.data.bean.Genre;
import org.wicketstuff.jquery.ui.samples.data.dao.GenresDAO;

public class RendererSelectMenuPage extends AbstractSelectMenuPage
{
	private static final long serialVersionUID = 1L;

	public RendererSelectMenuPage()
	{
		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final JQueryFeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback);

		// SelectMenu //
		final DropDownChoice<Genre> dropdown = new DropDownChoice<Genre>("select", new Model<Genre>(), GenresDAO.all(), new ChoiceRenderer<Genre>("name", "id"));
		form.add(dropdown);

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				RendererSelectMenuPage.this.info(dropdown);
			}
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				RendererSelectMenuPage.this.info(dropdown);
				target.add(feedback);
			}
		});
	}

	private void info(DropDownChoice<Genre> dropdown)
	{
		Genre choice = dropdown.getModelObject();

		this.info(choice != null ? choice : "no choice");
	}
}
