/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.kendoui.button;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.wicketstuff.kendo.ui.form.button.AjaxButton;
import org.wicketstuff.kendo.ui.form.button.Button;
import org.wicketstuff.kendo.ui.form.buttongroup.ButtonGroup;
import org.wicketstuff.kendo.ui.panel.KendoFeedbackPanel;

public class ButtonGroupPage extends AbstractButtonPage
{
	private static final long serialVersionUID = 1L;
	private static final List<Boolean> CHOICES = Arrays.asList(Boolean.FALSE, Boolean.TRUE);

	public ButtonGroupPage()
	{
		final Form<Boolean> form = new Form<Boolean>("form", Model.of(Boolean.TRUE));
		this.add(form);

		// FeedbackPanel //
		form.add(new KendoFeedbackPanel("feedback"));

		// ButtonGroup //
		form.add(new ButtonGroup<Boolean>("button-group", form.getModel(), Model.ofList(CHOICES)));

		// Buttons //
		form.add(new Button("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit()
			{
				ButtonGroupPage.this.info(form);
			}
		});

		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				ButtonGroupPage.this.info(form);
				target.add(form);
			}
		});
	}

	private final void info(Component component)
	{
		this.info(String.format("Submitted value: %s", component.getDefaultModelObjectAsString()));
	}
}
