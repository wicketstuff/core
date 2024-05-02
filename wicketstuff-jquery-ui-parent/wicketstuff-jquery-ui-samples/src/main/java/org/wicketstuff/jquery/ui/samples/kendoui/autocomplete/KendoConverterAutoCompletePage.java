package org.wicketstuff.jquery.ui.samples.kendoui.autocomplete;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Generics;
import org.wicketstuff.jquery.core.renderer.TextRenderer;
import org.wicketstuff.jquery.ui.samples.data.bean.Genre;
import org.wicketstuff.jquery.ui.samples.data.dao.GenresDAO;
import org.wicketstuff.kendo.ui.form.autocomplete.AutoCompleteTextField;
import org.wicketstuff.kendo.ui.form.button.AjaxButton;
import org.wicketstuff.kendo.ui.panel.KendoFeedbackPanel;

public class KendoConverterAutoCompletePage extends AbstractAutoCompletePage
{
	private static final long serialVersionUID = 1L;

	/** model object */
	private Genre genre = GenresDAO.get(0);

	public KendoConverterAutoCompletePage()
	{
		// Form //
		final Form<?> form = new Form<WebPage>("form", this.getModel());
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback);

		// Auto-complete //
		final AutoCompleteTextField<Genre> autocomplete = new AutoCompleteTextField<Genre>("genre", new TextRenderer<Genre>("name")) {

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

			@Override
			protected void onSelected(AjaxRequestTarget target)
			{
				this.info("#onSelected: " + this.getModelObject());
				target.add(feedback);
			}
		};

		form.add(autocomplete);

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target)
			{
				target.add(feedback);
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
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

	@Override
	protected IModel<?> initModel()
	{
		return new CompoundPropertyModel<WebPage>(this);
	}

	@SuppressWarnings("unchecked")
	private IModel<WebPage> getModel()
	{
		return (IModel<WebPage>) this.getDefaultModel();
	}
}
