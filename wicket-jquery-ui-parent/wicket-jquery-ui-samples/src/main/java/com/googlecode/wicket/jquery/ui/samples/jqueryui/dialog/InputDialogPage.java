package com.googlecode.wicket.jquery.ui.samples.jqueryui.dialog;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.InputDialog;

public class InputDialogPage extends AbstractDialogPage
{
	private static final long serialVersionUID = 1L;

	public InputDialogPage()
	{
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		form.add(new JQueryFeedbackPanel("feedback"));

		// Dialog //
		final InputDialog<String> dialog = new InputDialog<String>("dialog", "Input", "Please provide a value:", Model.of("a sample value")) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit(AjaxRequestTarget target, DialogButton button)
			{
				this.info("The form has been submitted");
				this.info(String.format("The model object is: '%s'", this.getModelObject()));
			}

			@Override
			public void onClose(IPartialPageRequestHandler handler, DialogButton button)
			{
				this.info(button + " has been clicked");
				handler.add(form);
			}
		};

		this.add(dialog); //the dialog is not within the form

		// Buttons //
		form.add(new AjaxButton("open") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target)
			{
				dialog.open(target);
			}
		});
	}
}
