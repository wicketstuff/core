package com.googlecode.wicket.jquery.ui.samples.pages.kendo.autocomplete;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.template.IJQueryTemplate;
import com.googlecode.wicket.jquery.core.utils.ListUtils;
import com.googlecode.wicket.jquery.ui.samples.data.bean.Genre;
import com.googlecode.wicket.jquery.ui.samples.data.dao.GenresDAO;
import com.googlecode.wicket.kendo.ui.form.autocomplete.AutoCompleteTextField;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class KendoTemplateAutoCompletePage extends AbstractAutoCompletePage
{
	private static final long serialVersionUID = 1L;

	public KendoTemplateAutoCompletePage()
	{
		// Model //
		final IModel<Genre> model = Model.of(GenresDAO.newGenre());

		// Form //
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback.setOutputMarkupId(true));

		// Auto-complete //
		final AutoCompleteTextField<Genre> autocomplete = new AutoCompleteTextField<Genre>("autocomplete", model) {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<Genre> getChoices(String input)
			{
				return ListUtils.contains(input, GenresDAO.all());
			}

			@Override
			protected void onSelected(AjaxRequestTarget target)
			{
				info("Your favorite rock genre is: " + this.getModelObject());
				target.add(feedback);
			}

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

		form.add(autocomplete.setListWidth(200));
	}
}
