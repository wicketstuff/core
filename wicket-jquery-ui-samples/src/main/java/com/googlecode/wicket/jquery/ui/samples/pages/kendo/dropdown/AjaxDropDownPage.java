package com.googlecode.wicket.jquery.ui.samples.pages.kendo.dropdown;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;

import com.googlecode.wicket.jquery.ui.kendo.dropdown.AjaxDropDownList;
import com.googlecode.wicket.jquery.ui.kendo.dropdown.DropDownList;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

public class AjaxDropDownPage extends AbstractDropDownPage
{
	private static final long serialVersionUID = 1L;
	private static final List<String> GENRES = Arrays.asList("Black Metal", "Death Metal", "Doom Metal", "Folk Metal", "Gothic Metal", "Heavy Metal", "Power Metal", "Symphonic Metal", "Trash Metal", "Vicking Metal");

	public AjaxDropDownPage()
	{
		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final FeedbackPanel feedbackPanel = new JQueryFeedbackPanel("feedback");
		form.add(feedbackPanel.setOutputMarkupId(true));

		// ComboBox //
		final DropDownList<String> dropdown = new AjaxDropDownList<String>("dropdown", new Model<String>(), new ListModel<String>(GENRES)) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSelectionChanged(AjaxRequestTarget target, Form<?> form)
			{
				String choice =  this.getModelObject();

				this.info(choice != null ? choice : "no choice");
				target.add(feedbackPanel);
			}
		};

		form.add(dropdown);
	}
}
