/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package com.googlecode.wicket.jquery.ui.samples.kendoui.console;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.kendo.ui.console.FeedbackConsole;
import com.googlecode.wicket.kendo.ui.form.TextField;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;

public class FeedbackConsolePage extends AbstractConsolePage
{
	private static final long serialVersionUID = 1L;

	public FeedbackConsolePage()
	{
		// FeedbackConsole //
		final FeedbackConsole console = new FeedbackConsole("console") {

			private static final long serialVersionUID = 1L;

			@Override
			protected String format(Serializable message, boolean error)
			{
				if (error)
				{
					return String.format("<i>%s</i>", super.format(message, error));
				}

				return super.format(message, error);
			}
		};

		this.add(console);

		// Form //
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// TextField //
		final TextField<String> textField = new TextField<String>("message", Model.of(""));
		form.add(textField.setRequired(true));

		// Buttons //
		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				this.info(textField.getModelObject());

				console.refresh(target);
			}

			@Override
			protected void onError(AjaxRequestTarget target)
			{
				console.refresh(target);
			}
		});
	}
}
