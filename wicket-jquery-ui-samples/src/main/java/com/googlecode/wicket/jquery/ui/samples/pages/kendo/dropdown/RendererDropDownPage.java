package com.googlecode.wicket.jquery.ui.samples.pages.kendo.dropdown;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.samples.data.bean.Genre;
import com.googlecode.wicket.jquery.ui.samples.data.dao.GenresDAO;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.form.button.Button;
import com.googlecode.wicket.kendo.ui.form.dropdown.lazy.DropDownList;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;
import com.googlecode.wicket.kendo.ui.renderer.ChoiceRenderer;

public class RendererDropDownPage extends AbstractDropDownPage
{
	private static final long serialVersionUID = 1L;

	public RendererDropDownPage()
	{
		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback);

		// ComboBox //
		final DropDownList<Genre> combobox = new DropDownList<Genre>("select", new Model<Genre>(), GenresDAO.all(), new ChoiceRenderer<Genre>("name", "id"));
		form.add(combobox.setListWidth(200));

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				RendererDropDownPage.this.info(combobox);
			}
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				RendererDropDownPage.this.info(combobox);
				target.add(feedback);
			}
		});
	}

	private void info(DropDownList<Genre> dropdown)
	{
		Genre choice =  dropdown.getModelObject();

		this.info(choice != null ? choice : "no choice");
	}
}
