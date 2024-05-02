package org.wicketstuff.jquery.ui.samples.kendoui.button;

import java.util.Arrays;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.kendo.ui.KendoIcon;
import org.wicketstuff.kendo.ui.form.buttongroup.AjaxButtonGroup;
import org.wicketstuff.kendo.ui.markup.html.IconLabel;
import org.wicketstuff.kendo.ui.panel.KendoFeedbackPanel;

public class AjaxButtonGroupPage extends AbstractButtonPage
{
	private static final long serialVersionUID = 1L;

	public AjaxButtonGroupPage()
	{
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		final KendoFeedbackPanel feedback = new KendoFeedbackPanel("feedback");
		form.add(feedback);

		// ButtonGroup //
		form.add(new AjaxButtonGroup<Boolean>("button-group", Model.of(Boolean.TRUE), Arrays.asList(Boolean.FALSE, Boolean.TRUE)) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSelect(AjaxRequestTarget target, Boolean value)
			{
				this.info(String.format("Selected value: %b", this.getModelObject()));
				target.add(feedback);
			}

			@Override
			protected Component newLabel(String id, IModel<Boolean> model)
			{
				return new IconLabel(id, Model.of(""), model.getObject() ? KendoIcon.CHECK : KendoIcon.CANCEL);
			}
		});
	}
}
