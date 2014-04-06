package com.googlecode.wicket.jquery.ui.samples.pages.kendo.multiselect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.util.ListModel;

import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.form.button.Button;
import com.googlecode.wicket.kendo.ui.form.multiselect.lazy.MultiSelect;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class LazyMultiSelectPage extends AbstractMultiSelectPage
{
	private static final long serialVersionUID = 1L;
	private static final List<String> GENRES = Arrays.asList("Black Metal", "Death Metal", "Doom Metal", "Folk Metal", "Gothic Metal", "Heavy Metal", "Power Metal", "Symphonic Metal", "Trash Metal", "Vicking Metal");

	public LazyMultiSelectPage()
	{
		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback);

		// MultiSelect //
		List<String> selected = new ArrayList<String>();
		selected.add("Heavy Metal");
		selected.add("Trash Metal");

		final MultiSelect<String> multiselect = new MultiSelect<String>("select", new ListModel<String>(selected)) {

			private static final long serialVersionUID = 1L;

			@Override
			public List<String> getChoices()
			{
				try
				{
					// simulate load duration //
					Thread.sleep(500);
				}
				catch (InterruptedException e)
				{
					this.error(e.getMessage());
				}

				return GENRES;
			}
		};

		form.add(multiselect.setOutputMarkupId(true));

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				LazyMultiSelectPage.this.info(multiselect);
			}
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				LazyMultiSelectPage.this.info(multiselect);
				target.add(feedback);
			}
		});
	}

	private void info(MultiSelect<String> multiselect)
	{
		Object choice =  multiselect.getModelObject();

		this.info(choice != null ? choice.toString() : "no choice");
	}
}
