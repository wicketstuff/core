package com.googlecode.wicket.jquery.ui.samples.jqueryui.autocomplete;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.googlecode.wicket.jquery.core.utils.ListUtils;
import com.googlecode.wicket.jquery.ui.form.autocomplete.AutoCompleteTextField;
import com.googlecode.wicket.jquery.ui.samples.data.bean.Genre;
import com.googlecode.wicket.jquery.ui.samples.data.dao.GenresDAO;

public class CustomAutoCompletePage extends AbstractAutoCompletePage
{
	private static final long serialVersionUID = 1L;

	public CustomAutoCompletePage()
	{
		// Model //
		final IModel<Genre> model = Model.of(GenresDAO.newGenre());

		// Form //
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// Container for selected genre (name & cover) //
		final WebMarkupContainer container = new WebMarkupContainer("container");
		form.add(container.setOutputMarkupId(true));

		container.add(new ContextImage("cover", new PropertyModel<String>(model, "cover")));
		container.add(new Label("name", new PropertyModel<String>(model, "name")));

		// Auto-complete //
		form.add(new AutoCompleteTextField<Genre>("autocomplete", model) {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<Genre> getChoices(String input)
			{
				return ListUtils.contains(input, GenresDAO.all());
			}

			@Override
			protected void onSelected(AjaxRequestTarget target)
			{
				target.add(container); //the model has already been updated
			}
		});
	}
}
