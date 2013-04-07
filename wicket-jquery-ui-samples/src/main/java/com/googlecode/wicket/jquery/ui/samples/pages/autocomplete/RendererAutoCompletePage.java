package com.googlecode.wicket.jquery.ui.samples.pages.autocomplete;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.io.IClusterable;

import com.googlecode.wicket.jquery.core.renderer.TextRenderer;
import com.googlecode.wicket.jquery.ui.form.autocomplete.AutoCompleteTextField;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class RendererAutoCompletePage extends AbstractAutoCompletePage
{
	private static final long serialVersionUID = 1L;

	public RendererAutoCompletePage()
	{
		this.init();
	}

	private void init()
	{
		// Model //
		final IModel<Genre> model = new Model<Genre>();

		// Form //
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Auto-complete (note that Genre does not overrides #toString()) //
		form.add(new AutoCompleteTextField<Genre>("autocomplete", model, new TextRenderer<Genre>("fullName")) {

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

			@Override
			protected void onSelected(AjaxRequestTarget target)
			{
				Genre genre = this.getModelObject();

				info("Your favorite rock genre is: " + genre.getName());
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

		public String getFullName()
		{
			return String.format("%02d - %s", this.id, this.name);
		}
	}
}
