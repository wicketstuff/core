package com.googlecode.wicket.jquery.ui.samples.pages.kendo.console;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.kendo.ui.console.FeedbackConsole;
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
		final TextField<String> textField = new RequiredTextField<String>("message", Model.of(""));
		form.add(textField);

		// Buttons //
		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				this.info(textField.getModelObject());

				console.refresh(target);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
				console.refresh(target);
			}
		});
	}
}
