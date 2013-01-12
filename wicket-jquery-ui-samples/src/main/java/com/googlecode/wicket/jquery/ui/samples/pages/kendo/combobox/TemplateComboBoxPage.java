package com.googlecode.wicket.jquery.ui.samples.pages.kendo.combobox;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.IClusterable;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.UrlUtils;
import org.apache.wicket.request.cycle.RequestCycle;

import com.googlecode.wicket.jquery.ui.kendo.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.kendo.button.Button;
import com.googlecode.wicket.jquery.ui.kendo.combobox.ComboBox;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.template.IJQueryTemplate;

public class TemplateComboBoxPage extends AbstractComboBoxPage
{
	private static final long serialVersionUID = 1L;

	public TemplateComboBoxPage()
	{
		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedback");
		form.add(feedbackPanel.setOutputMarkupId(true));

		// ComboBox //
		final ComboBox<Genre> combobox = new ComboBox<Genre>("combobox", new Model<String>(), GENRES) {

			private static final long serialVersionUID = 1L;

			@Override
			protected IJQueryTemplate newTemplate()
			{
				return new IJQueryTemplate() {

					private static final long serialVersionUID = 1L;

					@Override
					public String getText()
					{
						return  "<table style='width: 100%'>\n" +
							" <tr>\n" +
							"  <td>\n" +
							"   <img src='${ data.coverUrl }' width='50px' />\n" +
							"  </td>\n" +
							"  <td>\n" +
							"   ${ data.name }\n" +
							"  </td>\n" +
							" </tr>\n" +
							"</table>\n";
					}

					@Override
					public List<String> getTextProperties()
					{
						return Arrays.asList("name", "coverUrl");
					}

				};
			}
		};

		form.add(combobox);

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				TemplateComboBoxPage.this.info(combobox);
			}
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				TemplateComboBoxPage.this.info(combobox);
				target.add(feedbackPanel);
			}
		});
	}

	private void info(ComboBox<Genre> combobox)
	{
		String choice =  combobox.getModelObject();

		this.info(choice != null ? choice : "no choice");
	}


	// List of Genre(s) //
	private static final List<Genre> GENRES = Arrays.asList(
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

		public String getCoverUrl()
		{
			return UrlUtils.rewriteToContextRelative("images/" + this.cover, RequestCycle.get());
		}

		@Override
		public String toString()
		{
			return this.name;
		}
	}
}
