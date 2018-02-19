package com.googlecode.wicket.jquery.ui.samples.kendoui.multiselect;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Generics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicket.kendo.ui.form.multiselect.lazy.AjaxMultiSelect;
import com.googlecode.wicket.kendo.ui.form.multiselect.lazy.MultiSelect;
import com.googlecode.wicket.kendo.ui.panel.KendoFeedbackPanel;

public class AjaxMultiSelectPage extends AbstractMultiSelectPage
{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(AjaxMultiSelectPage.class);
	private static final List<String> GENRES = Arrays.asList("Black Metal", "Death Metal", "Doom Metal", "Folk Metal", "Gothic Metal", "Heavy Metal", "Power Metal", "Symphonic Metal", "Trash Metal", "Vicking Metal");

	public AjaxMultiSelectPage()
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

		final AjaxMultiSelect<String> multiselect = new AjaxMultiSelect<String>("select", Model.ofList(selected)) {

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
					if (LOG.isDebugEnabled())
					{
						LOG.debug(e.getMessage(), e);
					}
				}

				return GENRES;
			}
			
			@Override
			public void onSelectionChanged(AjaxRequestTarget target)
			{
				AjaxMultiSelectPage.this.info(this);
				target.add(feedback);
			}
		};

		form.add(multiselect);
	}

	private void info(MultiSelect<String> multiselect)
	{
		Object choice = multiselect.getModelObject();

		this.info(choice != null ? choice.toString() : "no choice");
	}
}
