package com.googlecode.wicket.jquery.ui.samples.pages.kendo.autocomplete;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;

import com.googlecode.wicket.jquery.core.renderer.TextRenderer;
import com.googlecode.wicket.jquery.core.utils.ListUtils;
import com.googlecode.wicket.jquery.ui.samples.data.bean.Genre;
import com.googlecode.wicket.jquery.ui.samples.data.dao.GenresDAO;
import com.googlecode.wicket.kendo.ui.form.autocomplete.AutoCompleteTextField;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class KendoCompoundAutoCompletePage extends AbstractAutoCompletePage
{
	private static final long serialVersionUID = 1L;

	private Genre genre = GenresDAO.get(0);

//	public Genre getGenre()
//	{
//		return genre;
//	}
//
//	public void setGenre(Genre genre)
//	{
//		this.genre = genre;
//	}

	public KendoCompoundAutoCompletePage()
	{
		// Model //
		//final IModel<Genre> model = Model.of(GenresDAO.newGenre());

		// Form //
		final Form<Object> form = new Form<Object>("form", new CompoundPropertyModel<Object>(this));
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback);

		// Auto-complete //
		final AutoCompleteTextField<Genre> autocomplete = new AutoCompleteTextField<Genre>("genre", new TextRenderer<Genre>("name")) {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<Genre> getChoices(String input)
			{
				return ListUtils.contains(input, GenresDAO.all());
			}

			@Override
			protected void onSelected(AjaxRequestTarget target)
			{
				this.info("genre: " + this.getModelObject());
				target.add(feedback);
			}
		};

		form.add(autocomplete);

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> unused)
			{
				target.add(feedback);
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> unused)
			{
				this.info(genre);
				target.add(feedback);
			}
		});
	}

//	private void info(Form<?> form)
//	{
//		Object choice =  form.getModelObject();
//
//		this.info(choice != null ? choice.toString() : "no choice");
//	}
}
