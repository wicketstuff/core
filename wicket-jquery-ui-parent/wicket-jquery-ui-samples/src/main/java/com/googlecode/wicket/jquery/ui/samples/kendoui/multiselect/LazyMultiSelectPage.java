package com.googlecode.wicket.jquery.ui.samples.kendoui.multiselect;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Generics;

import com.googlecode.wicket.kendo.ui.KendoDataSource;
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
		List<String> selected = Generics.newArrayList();
		selected.add("Heavy Metal");
		selected.add("Trash Metal");

		final MultiSelect<String> multiselect = new MultiSelect<String>("select", Model.ofList(selected)) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onConfigure(KendoDataSource dataSource)
			{
				super.onConfigure(dataSource);
				
				dataSource.set("serverFiltering", false); // default is true
			}

			@Override
			protected List<String> getChoices(String input)
			{
				// note: 'input' is always empty when serverFiltering=false
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
			protected void onSubmit(AjaxRequestTarget target)
			{
				LazyMultiSelectPage.this.info(multiselect);
				target.add(feedback);
			}
		});
	}

	private void info(MultiSelect<String> multiselect)
	{
		Object choice = multiselect.getModelObject();

		this.info(choice != null ? choice.toString() : "no choice");
	}
}
