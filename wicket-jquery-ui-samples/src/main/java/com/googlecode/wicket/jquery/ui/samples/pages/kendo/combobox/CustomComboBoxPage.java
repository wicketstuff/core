package com.googlecode.wicket.jquery.ui.samples.pages.kendo.combobox;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.IClusterable;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.string.UrlUtils;

import com.googlecode.wicket.jquery.ui.form.autocomplete.AutoCompleteUtils;
import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.form.button.Button;
import com.googlecode.wicket.jquery.ui.kendo.combobox.ComboBox;
import com.googlecode.wicket.jquery.ui.kendo.combobox.IComboBoxTemplate;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class CustomComboBoxPage extends AbstractComboBoxPage
{
	private static final long serialVersionUID = 1L;
	
	public CustomComboBoxPage()
	{
		Form<Void> form = new Form<Void>("form");
		this.add(form);
		
		// FeedbackPanel //
		final FeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedback");
		form.add(feedbackPanel.setOutputMarkupId(true));

		// ComboBox (Kendo-UI widget) //
		final ComboBox<Genre> dropdown = new ComboBox<Genre>("combobox", new Model<String>(), GENRES, this.newTemplate());
//		TODO: new ListModel<String> !!
		form.add(dropdown);
		
		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				CustomComboBoxPage.this.info(dropdown);
			}			
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				CustomComboBoxPage.this.info(dropdown);
				target.add(feedbackPanel);
			}
		});
	}

	private IComboBoxTemplate newTemplate()
	{
		return new IComboBoxTemplate() {
			
			@Override
			public String getHtml()
			{
				return  "<table style='width: 100%'>" +
						"<tr>" +
						"<td>" +
						"<img src='${ data.coverUrl }' width='50px' />" +
						"</td>" +
						"<td>" +
						"${ data.name }</span>" +
						"</td>" +
						"</tr>" +
						"</table>";
			}

			@Override
			public List<String> getProperties()
			{
				return Arrays.asList("name", "coverUrl");
			}

		};
	}

	private void info(ComboBox<Genre> dropdown)
	{
		String choice =  dropdown.getModelObject();
		
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
		
		public String getCoverUrl()
		{
			return UrlUtils.rewriteToContextRelative("images/" + this.cover, RequestCycle.get());
		}

		/**
		 * toString needs to be overridden: it is used by the suggestion display
		 * and by {@link AutoCompleteUtils#contains(List, String)} method
		 */
		@Override
		public String toString()
		{
			return this.name;
		}
	}
}
