/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.kendoui.multiselect;

import java.util.Collection;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Generics;
import org.wicketstuff.jquery.ui.samples.data.bean.Genre;
import org.wicketstuff.jquery.ui.samples.data.dao.GenresDAO;
import org.wicketstuff.kendo.ui.form.button.AjaxButton;
import org.wicketstuff.kendo.ui.form.button.Button;
import org.wicketstuff.kendo.ui.form.multiselect.MultiSelect;
import org.wicketstuff.kendo.ui.panel.KendoFeedbackPanel;

public class RendererMultiSelectPage extends AbstractMultiSelectPage
{
	private static final long serialVersionUID = 1L;

	public RendererMultiSelectPage()
	{
		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback);

		// MultiSelect //
		List<Genre> selected = Generics.newArrayList();
		selected.add(GenresDAO.get(2));
		selected.add(GenresDAO.get(4));

		final MultiSelect<Genre> multiselect = new MultiSelect<Genre>("select", Model.ofList(selected), GenresDAO.all(), new ChoiceRenderer<Genre>("name", "id"));
		form.add(multiselect.setOutputMarkupId(true));

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				RendererMultiSelectPage.this.info(multiselect);
			}
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				RendererMultiSelectPage.this.info(multiselect);
				target.add(feedback);
			}
		});
	}

	private void info(MultiSelect<Genre> multiselect)
	{
		Collection<Genre> choice =  multiselect.getModelObject();

		this.info(choice != null ? choice.toString() : "no choice");
	}
}
