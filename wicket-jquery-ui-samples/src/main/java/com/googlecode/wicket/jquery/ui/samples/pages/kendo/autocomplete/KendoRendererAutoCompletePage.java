package com.googlecode.wicket.jquery.ui.samples.pages.kendo.autocomplete;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.renderer.TextRenderer;
import com.googlecode.wicket.jquery.core.template.IJQueryTemplate;
import com.googlecode.wicket.jquery.core.utils.ListUtils;
import com.googlecode.wicket.jquery.ui.samples.data.bean.Genre;
import com.googlecode.wicket.jquery.ui.samples.data.dao.GenresDAO;
import com.googlecode.wicket.kendo.ui.form.autocomplete.AbstractAutoCompleteTextField;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

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
						return Arrays.asList("name", "cover");
					}
				};
			}
		};

		form.add(autocomplete.setListWidth(350));

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				this.info("Value: " + autocomplete.getValue());

				target.add(feedback);
			}
		});
	}
}
