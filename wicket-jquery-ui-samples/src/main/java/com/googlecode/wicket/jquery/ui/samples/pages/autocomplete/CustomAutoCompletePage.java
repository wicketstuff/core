package com.googlecode.wicket.jquery.ui.samples.pages.autocomplete;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.io.IClusterable;

import com.googlecode.wicket.jquery.ui.form.autocomplete.AutoCompleteTextField;
import com.googlecode.wicket.jquery.ui.form.autocomplete.AutoCompleteUtils;

public class CustomAutoCompletePage extends AbstractAutoCompletePage
{
	private static final long serialVersionUID = 1L;

	public CustomAutoCompletePage()
	{
		this.init();
	}

	private void init()
	{
		// Model //
		final IModel<Genre> model = new Model<Genre>(Genre.emptyGenre());

		// Form //
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// Container for selected genre (name & cover) //
		final WebMarkupContainer container = new WebMarkupContainer("container");
		form.add(container.setOutputMarkupId(true));

		container.add(new ContextImage("cover", new PropertyModel<String>(model, "cover")));
		container.add(new Label("name", new PropertyModel<String>(model, "name")));

		// Auto-complete //
		form.add(new AutoCompleteTextField<Genre>("autocomplete", model) {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<Genre> getChoices(String input)
			{
				return AutoCompleteUtils.contains(input, GENRES);
			}

			@Override
			protected void onSelected(AjaxRequestTarget target)
			{
				target.add(container); //the model has already been updated
			}
		});
	}

	// List of Genre(s) //
	static final List<Genre> GENRES = Arrays.asList(
			new Genre("Black Metal", "cover-black-metal.png"),
			new Genre("Death Metal", "cover-death-metal.png"),
			new Genre("Doom Metal", "cover-doom-metal.png"),
			new Genre("Folk Metal", "cover-folk-metal.png"),
			new Genre("Gothic Metal", "cover-gothic-metal.png"),
			new Genre("Heavy Metal", "cover-heavy-metal.png"),
			new Genre("Power Metal", "cover-power-metal.png"),
			new Genre("Symphonic Metal", "cover-symphonic-metal.png"),
			new Genre("Trash Metal", "cover-trash-metal.png"),
			new Genre("Vicking Metal", "cover-vicking-metal.png"));


	// Bean //
	static class Genre implements IClusterable
	{
		private static final long serialVersionUID = 1L;

		public static Genre emptyGenre()
		{
			return new Genre("", "cover-empty.png");
		}

		private final String name;
		private final String cover;

		public Genre(final String name, final String cover)
		{
			this.name = name;
			this.cover = cover;
		}

		public String getName()
		{
			return this.name;
		}

		public String getCover()
		{
			return "images/" + this.cover;
		}

		/**
		 * #toString() needs to be overridden if no renderer is provided.
		 * #toString() is also used by {@link AutoCompleteUtils#contains(String, List)} method.
		 */
		@Override
		public String toString()
		{
			return this.name;
		}
	}
}
