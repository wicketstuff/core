package com.googlecode.wicket.jquery.ui.samples.pages.kendo.console;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.ErrorLevelFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessagesModel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.kendo.ui.console.Console;
import com.googlecode.wicket.kendo.ui.form.button.AjaxButton;

public class DefaultConsolePage extends AbstractConsolePage
{
	private static final long serialVersionUID = 1L;

	private final FeedbackMessagesModel feedbackModel;

	public DefaultConsolePage()
	{
		this.feedbackModel = this.newFeedbackMessagesModel();

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
		final TextField<String> textField = new RequiredTextField<String>("message", Model.of(""));
		form.add(textField);

		// Buttons //
		form.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				console.info(textField.getModelObject(), target);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
				for (FeedbackMessage message : feedbackModel.getObject())
				{
					console.log(message.getMessage(), true, target);
					message.markRendered();
				}
			}
		});
	}

	protected FeedbackMessagesModel newFeedbackMessagesModel()
	{
		return new FeedbackMessagesModel(this, new ErrorLevelFeedbackMessageFilter(FeedbackMessage.ERROR));
	}
}
