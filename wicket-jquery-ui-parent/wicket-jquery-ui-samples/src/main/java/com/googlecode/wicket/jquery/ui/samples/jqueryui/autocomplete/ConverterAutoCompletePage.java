package com.googlecode.wicket.jquery.ui.samples.jqueryui.autocomplete;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Generics;

import com.googlecode.wicket.jquery.core.renderer.TextRenderer;
import com.googlecode.wicket.jquery.core.resource.StyleSheetPackageHeaderItem;
import com.googlecode.wicket.jquery.ui.form.autocomplete.AutoCompleteTextField;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.data.bean.Genre;
import com.googlecode.wicket.jquery.ui.samples.data.dao.GenresDAO;

public class ConverterAutoCompletePage extends AbstractAutoCompletePage // NOSONAR
{
	private static final long serialVersionUID = 1L;

	public ConverterAutoCompletePage()
	{
		// Form //
		final Form<Genre> form = new Form<Genre>("form", Model.of(GenresDAO.get(0))); // test default value
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Auto-complete //
		final AutoCompleteTextField<Genre> autocomplete = new AutoCompleteTextField<Genre>("autocomplete", form.getModel(), new TextRenderer<Genre>("name"), Genre.class) { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			protected List<Genre> getChoices(String input)
			{
				List<Genre> choices = Generics.newArrayList();

				int count = 0;
				for (Genre genre : GenresDAO.all())
				{
					// Using ITextRenderer#match is not mandatory, it's just an helper
					if (this.getRenderer().match(genre, input, false))
					{
						choices.add(genre);

						// limits the number of results
						if (++count == 20)
						{
							break;
						}
					}
				}

				return choices;
			}
		};

		form.add(autocomplete.setRequired(true));

		// Ajax button //
		form.add(new AjaxButton("button") { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target)
			{
				target.add(feedback);
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				Genre genre = form.getModelObject();

				if (genre != null)
				{
					info(String.format("Your favorite rock genre is: %s (id #%d)", genre.getName(), genre.getId()));
				}
				else
				{
					warn("Unlisted genre");
					info("User input is: " + autocomplete.getInput());
				}

				target.add(feedback);
			}
		});
	}

	// Methods //

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);

		response.render(new StyleSheetPackageHeaderItem(ConverterAutoCompletePage.class));
	}
}
