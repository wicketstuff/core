/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.kendoui.autocomplete;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.wicketstuff.jquery.core.renderer.TextRenderer;
import org.wicketstuff.jquery.core.template.IJQueryTemplate;
import org.wicketstuff.jquery.core.utils.ListUtils;
import org.wicketstuff.jquery.ui.samples.data.bean.Genre;
import org.wicketstuff.jquery.ui.samples.data.dao.GenresDAO;
import org.wicketstuff.kendo.ui.form.autocomplete.AbstractAutoCompleteTextField;
import org.wicketstuff.kendo.ui.form.button.AjaxButton;
import org.wicketstuff.kendo.ui.panel.KendoFeedbackPanel;

public class KendoRendererAutoCompletePage extends AbstractAutoCompletePage
{
	private static final long serialVersionUID = 1L;

	public KendoRendererAutoCompletePage()
	{
		// Form //
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Auto-complete //
		final AbstractAutoCompleteTextField<String, Genre> autocomplete = new AbstractAutoCompleteTextField<String, Genre>("autocomplete", Model.of(""), new TextRenderer<Genre>("cover")) {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<Genre> getChoices(String input)
			{
				return ListUtils.contains(input, GenresDAO.all());
			}

			@Override
			protected void onInitialize()
			{
				super.onInitialize();

				this.setListWidth(350);
			}

			@Override
			protected void onSelected(AjaxRequestTarget target, Genre choice)
			{
				// Caution: while using AbstractAutoCompleteTextField, the model object is not set
				// It should be set manually, ie: this.setModelObject(choice.getCover());
				this.info("Genre: " + choice);

				target.add(feedback);
			}

			@Override
			protected IJQueryTemplate newTemplate()
			{
				return new IJQueryTemplate() {

					private static final long serialVersionUID = 1L;

					@Override
					public String getText()
					{
						return "${ data.name } - ${ data.cover }";
					}

					@Override
					public List<String> getTextProperties()
					{
						return Arrays.asList("name"); // 'cover' already handled by the TextRenderer
					}
				};
			}
		};

		form.add(autocomplete);

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				this.info("Value: " + autocomplete.getValue());

				target.add(feedback);
			}
		});
	}
}
