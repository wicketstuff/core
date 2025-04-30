/*
Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0
 */
package org.wicketstuff.jquery.ui.samples.kendoui.console;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.ErrorLevelFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessagesModel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.wicketstuff.kendo.ui.console.Console;
import org.wicketstuff.kendo.ui.form.TextField;
import org.wicketstuff.kendo.ui.form.button.AjaxButton;

public class DefaultConsolePage extends AbstractConsolePage
{
	private static final long serialVersionUID = 1L;

	private final FeedbackMessagesModel errorFeedbackMessagesModel;

	public DefaultConsolePage()
	{
		this.errorFeedbackMessagesModel = newErrorFeedbackMessagesModel(this);

		this.initialize();
	}

	private void initialize()
	{
		// Console //
		final Console console = new Console("console");
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
				console.info(target, textField.getModelObject());
			}

			@Override
			protected void onError(AjaxRequestTarget target)
			{
				for (FeedbackMessage message : errorFeedbackMessagesModel.getObject())
				{
					console.error(target, message.getMessage());
					message.markRendered();
				}
			}
		});
	}

	protected static FeedbackMessagesModel newErrorFeedbackMessagesModel(Page page)
	{
		return new FeedbackMessagesModel(page, new ErrorLevelFeedbackMessageFilter(FeedbackMessage.ERROR));
	}
}
