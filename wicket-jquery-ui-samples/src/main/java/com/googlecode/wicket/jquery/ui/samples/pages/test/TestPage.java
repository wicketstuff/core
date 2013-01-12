package com.googlecode.wicket.jquery.ui.samples.pages.test;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;

import com.googlecode.wicket.jquery.ui.kendo.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.kendo.button.Button;
import com.googlecode.wicket.jquery.ui.kendo.dropdown.DropDownList;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.samples.pages.kendo.AbstractKendoPage;

public class TestPage extends AbstractKendoPage
{
	private static final long serialVersionUID = 1L;
	private static final List<String> GENRES = Arrays.asList("Black Metal", "Death Metal", "Doom Metal", "Folk Metal", "Gothic Metal", "Heavy Metal", "Power Metal", "Symphonic Metal", "Trash Metal", "Vicking Metal");

	public TestPage()
	{
		this.init();
	}

	private void init()
	{
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// DropDownList //
		final DropDownList<String> dropdown = new DropDownList<String>("select", new Model<String>(), new ListModel<String>(GENRES));
		form.add(dropdown.setVisible(false).setOutputMarkupPlaceholderTag(true));

		// Buttons //
		form.add(new Button("button") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				TestPage.this.info(dropdown);
			}
		});

		form.add(new AjaxButton("toggle") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				target.add(dropdown.setVisible(!dropdown.isVisible()));
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
				target.add(feedback);
			}
		});
	}

	private void info(DropDownList<String> dropdown)
	{
		String choice =  dropdown.getModelObject();

		this.info(choice != null ? choice : "no choice");
	}
}
