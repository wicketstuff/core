package com.googlecode.wicket.jquery.ui.samples.pages.autocomplete;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.io.IClusterable;

import com.googlecode.wicket.jquery.core.renderer.TextRenderer;
import com.googlecode.wicket.jquery.ui.form.autocomplete.AutoCompleteTextField;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class ConverterAutoCompletePage extends AbstractAutoCompletePage
{
	private static final long serialVersionUID = 1L;

	public ConverterAutoCompletePage()
	{
		this.init();
	}

	private void init()
	{
		// Form //
		final Form<Genre> form = new Form<Genre>("form", new Model<Genre>(GENRES.get(0))); //test default value
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Auto-complete (note that Genre does not overrides #toString()) //
		final AutoCompleteTextField<Genre> autocomplete = new AutoCompleteTextField<Genre>("autocomplete", form.getModel(), new TextRenderer<Genre>("name"), Genre.class) {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<Genre> getChoices(String input)
			{
				List<Genre> choices = new ArrayList<Genre>();

				int count = 0;
				for (Genre genre: GENRES)
				{
					if (genre.getName().toLowerCase().contains(input.toLowerCase()))
					{
						choices.add(genre);

						if (++count == 20) { break; } //limits the number of results
					}
				}

				return choices;
			}
		};

		form.add(autocomplete.setRequired(true));

		// Ajax button //
		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
				target.add(feedback);
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> unused)
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

	// List of Genre(s) //
	static final List<Genre> GENRES = Arrays.asList(
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

	// Bean //
	static class Genre implements IClusterable
	{
		private static final long serialVersionUID = 1L;

		public static Genre emptyGenre()
		{
			return new Genre(0, "");
		}

		private final int id;
		private final String name;

		public Genre(final int id, final String name)
		{
			this.id = id;
			this.name = name;
		}

		public int getId()
		{
			return this.id;
		}

		public String getName()
		{
			return this.name;
		}
	}
}
