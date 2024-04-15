package com.googlecode.wicket.jquery.ui.samples.kendoui.dropdown;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.core.template.IJQueryTemplate;
import com.googlecode.wicket.jquery.ui.samples.data.bean.Genre;
import com.googlecode.wicket.jquery.ui.samples.data.dao.GenresDAO;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;
import com.googlecode.wicket.kendo.ui.form.button.Button;
import com.googlecode.wicket.kendo.ui.form.dropdown.lazy.DropDownList;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class TemplateDropDownPage extends AbstractDropDownPage
{
	private static final long serialVersionUID = 1L;

	public TemplateDropDownPage()
	{
		Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback);

		// DropDownList //
		final DropDownList<Genre> dropdown = new DropDownList<Genre>("select", new Model<Genre>(), GenresDAO.all()) {

			private static final long serialVersionUID = 1L;
			
			@Override
			protected void onInitialize()
			{
				super.onInitialize();
				
				this.setListWidth(220);
			}

			@Override
			protected IJQueryTemplate newTemplate()
			{
				return new IJQueryTemplate() {

					private static final long serialVersionUID = 1L;

					@Override
					public String getText()
					{
						return  "\n<table style='width: 100%'>" +
							"\n <tr>" +
							"\n  <td>" +
							"\n   <img src='#: data.coverUrl #' width='50px' />" +
							"\n  </td>" +
							"\n  <td>" +
							"\n   #: data.name #" +
							"\n  </td>" +
							"\n </tr>" +
							"\n</table>";
					}

					@Override
					public List<String> getTextProperties()
					{
						return Arrays.asList("name", "coverUrl");
					}
				};
			}
		};

		form.add(dropdown);

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				TemplateDropDownPage.this.info(dropdown);
			}
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				TemplateDropDownPage.this.info(dropdown);
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
