package com.googlecode.wicket.jquery.ui.samples.pages.dropdown;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;

import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.form.button.Button;
import com.googlecode.wicket.jquery.ui.form.dropdown.DropDownChoice;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class DefaultSelectMenuPage extends AbstractSelectMenuPage
{
	private static final long serialVersionUID = 1L;
	private static final List<String> GENRES = Arrays.asList("Black Metal", "Death Metal", "Doom Metal", "Folk Metal", "Gothic Metal", "Heavy Metal", "Power Metal", "Symphonic Metal", "Trash Metal", "Vicking Metal");

	public DefaultSelectMenuPage()
	{
		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final JQueryFeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback);

		// SelectMenu //
		final DropDownChoice<String> dropdown = new DropDownChoice<String>("select", new Model<String>(), new ListModel<String>(GENRES));
		form.add(dropdown.setOutputMarkupId(true));

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				DefaultSelectMenuPage.this.info(dropdown);
			}
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				DefaultSelectMenuPage.this.info(dropdown);
				target.add(feedback);
			}
		});
	}

	private void info(DropDownChoice<String> dropdown)
	{
		String choice =  dropdown.getModelObject();

		this.info(choice != null ? choice : "no choice");
	}
}
