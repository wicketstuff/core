package com.googlecode.wicket.jquery.ui.samples.pages.kendo.autocomplete;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.renderer.TextRenderer;
import com.googlecode.wicket.jquery.core.utils.ListUtils;
import com.googlecode.wicket.jquery.ui.samples.data.bean.Genre;
import com.googlecode.wicket.jquery.ui.samples.data.dao.GenresDAO;
import com.googlecode.wicket.kendo.ui.form.autocomplete.AutoCompleteTextField;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class KendoRendererAutoCompletePage extends AbstractAutoCompletePage
{
	private static final long serialVersionUID = 1L;

	public KendoRendererAutoCompletePage()
	{
		// Model //
		final IModel<Genre> model = new Model<Genre>();

		// Form //
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Auto-complete //
		form.add(new AutoCompleteTextField<Genre>("autocomplete", model, new TextRenderer<Genre>("fullName")) {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<Genre> getChoices(String input)
			{
				return ListUtils.contains(input, GenresDAO.all());
			}

			@Override
			protected void onSelected(AjaxRequestTarget target)
			{
				Genre genre = this.getModelObject();

				info("Your favorite rock genre is: " + genre.getName());
				target.add(feedback);
			}
		});
	}
}
