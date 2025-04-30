/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.jqueryui.dropdown;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.wicketstuff.jquery.ui.form.dropdown.AjaxDropDownChoice;
import org.wicketstuff.jquery.ui.form.dropdown.DropDownChoice;
import org.wicketstuff.jquery.ui.panel.JQueryFeedbackPanel;

public class AjaxSelectMenuPage extends AbstractSelectMenuPage
{
	private static final long serialVersionUID = 1L;
	private static final List<String> GENRES = Arrays.asList("Black Metal", "Death Metal", "Doom Metal", "Folk Metal", "Gothic Metal", "Heavy Metal", "Power Metal", "Symphonic Metal", "Trash Metal", "Vicking Metal");

	public AjaxSelectMenuPage()
	{
		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final JQueryFeedbackPanel feedback = new JQueryFeedbackPanel("feedback");
		form.add(feedback);

		// SelectMenu //
		final DropDownChoice<String> dropdown = new AjaxDropDownChoice<String>("select", new Model<String>(), new ListModel<String>(GENRES)) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSelectionChanged(AjaxRequestTarget target)
			{
				String choice =  this.getModelObject();

				this.info(choice != null ? choice : "no choice");
				target.add(feedback);
			}
		};

		form.add(dropdown);
	}
}
