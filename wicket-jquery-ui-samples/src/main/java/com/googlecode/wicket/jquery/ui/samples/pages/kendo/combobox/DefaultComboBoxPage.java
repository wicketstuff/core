package com.googlecode.wicket.jquery.ui.samples.pages.kendo.combobox;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.kendo.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.kendo.button.Button;
import com.googlecode.wicket.jquery.ui.kendo.combobox.ComboBox;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class DefaultComboBoxPage extends AbstractComboBoxPage
{
	private static final long serialVersionUID = 1L;
	private static final List<String> GENRES = Arrays.asList("Black Metal", "Death Metal", "Doom Metal", "Folk Metal", "Gothic Metal", "Heavy Metal", "Power Metal", "Symphonic Metal", "Trash Metal", "Vicking Metal");

	public DefaultComboBoxPage()
	{
		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedback");
		form.add(feedbackPanel.setOutputMarkupId(true));

		// ComboBox //
		final ComboBox<String> combobox = new ComboBox<String>("combobox", new Model<String>(), GENRES); // new WildcardListModel(GENRES) can be used (but not ListModel)
		form.add(combobox); //.setRequired(true)

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				DefaultComboBoxPage.this.info(combobox);
			}
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				DefaultComboBoxPage.this.info(combobox);
				target.add(feedbackPanel);
			}
		});
	}

	private void info(ComboBox<String> combobox)
	{
		String choice =  combobox.getModelObject();

		this.info(choice != null ? choice : "no choice");
	}
}
