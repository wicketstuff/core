package org.wicketstuff.jquery.ui.samples.kendoui.dropdown;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.wicketstuff.jquery.ui.samples.data.bean.Genre;
import org.wicketstuff.jquery.ui.samples.data.dao.GenresDAO;
import org.wicketstuff.kendo.ui.form.button.AjaxButton;
import org.wicketstuff.kendo.ui.form.button.Button;
import org.wicketstuff.kendo.ui.form.dropdown.lazy.DropDownList;
import org.wicketstuff.kendo.ui.panel.KendoFeedbackPanel;
import org.wicketstuff.kendo.ui.renderer.ChoiceRenderer;

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

		// DropDownList //
		final DropDownList<Genre> dropdown = new DropDownList<Genre>("select", new Model<Genre>(), GenresDAO.all(), new ChoiceRenderer<Genre>("name", "id"));
		form.add(dropdown.setListWidth(200));

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				RendererDropDownPage.this.info(dropdown);
			}
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				RendererDropDownPage.this.info(dropdown);
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
